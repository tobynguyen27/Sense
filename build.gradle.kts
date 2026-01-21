import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.loom)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.spotless)
    idea
    `maven-publish`
}

val runNumber: String =
    if (System.getenv("GITHUB_RUN_NUMBER") == null) "9999" else System.getenv("GITHUB_RUN_NUMBER")
val isRelease = project.hasProperty("release")

val minecraft_version: String by project
val loader_version: String by project

val mod_id: String by project
val mod_name: String by project
val mod_version: String by project
val mod_description: String by project
val mod_license: String by project
val maven_group: String by project

version = if (isRelease) mod_version else "$mod_version-build.$runNumber"

group = maven_group

base.archivesName = mod_id

repositories {
    val urls = setOf("https://maven.parchmentmc.org", "https://maven.terraformersmc.com")

    urls.forEach { maven { url = uri(it) } }

    mavenCentral()
}

sourceSets {
    register("testmod") {
        val main = sourceSets.getByName<SourceSet>("main")
        compileClasspath += main.compileClasspath
        runtimeClasspath += main.runtimeClasspath
    }
}

loom {
    runs {
        val testmod = sourceSets.getByName("testmod")

        register("testmodClient") {
            client()
            name = "Testmod Client"
            source(testmod)
        }

        register("testmodServer") {
            client()
            name = "Testmod Server"
            source(testmod)
        }
    }
}

dependencies {
    minecraft(libs.minecraft)
    mappings(
        loom.layered {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-1.18.2:2022.11.06@zip")
        }
    )

    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    modImplementation(libs.fabric.kotlin)
    modImplementation(libs.energyapi)
    include(libs.energyapi)

    testImplementation(libs.fabric.junit)

    modLocalRuntime(libs.modmenu) { exclude(group = "net.fabricmc") }

    "testmodImplementation"(sourceSets.main.get().output)
}

tasks.named<Test>("test") { useJUnitPlatform() }

tasks.withType<ProcessResources>().configureEach {
    val replaceProperties =
        hashMapOf(
            "loader_version" to loader_version,
            "minecraft_version" to minecraft_version,
            "mod_id" to mod_id,
            "mod_name" to mod_name,
            "mod_license" to mod_license,
            "mod_version" to mod_version,
            "mod_description" to mod_description,
        )

    inputs.properties(replaceProperties)

    filesMatching(setOf("fabric.mod.json")) { expand(replaceProperties) }
}

tasks.withType<JavaCompile>().configureEach { options.encoding = "UTF-8" }

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
        vendor = JvmVendorSpec.GRAAL_VM
    }

    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    withSourcesJar()
}

kotlin { compilerOptions { jvmTarget = JvmTarget.JVM_17 } }

tasks.named<Jar>("jar") {
    inputs.property("archivesName", project.base.archivesName)

    from("LICENSE") { rename { "${it}_${inputs.properties["archivesName"]}" } }
}

spotless {
    encoding("UTF-8")

    kotlin {
        ktfmt().kotlinlangStyle().configure {
            it.setMaxWidth(100)
            it.setBlockIndent(4)
            it.setContinuationIndent(4)
            it.setRemoveUnusedImports(true)
        }
        endWithNewline()
        toggleOffOn()
    }

    kotlinGradle {
        target("*.gradle.kts")
        ktfmt().kotlinlangStyle()
    }

    java {
        importOrder()
        removeUnusedImports()
        palantirJavaFormat()
    }

    json {
        target("src/*/resources/**/*.json")
        targetExclude("src/generated/resources/**")

        biome("2.3.11")
            .downloadDir(File(rootDir, ".gradle/biome").absolutePath)
            .configPath(File(rootDir, "spotless/biome.json").absolutePath)

        endWithNewline()
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = mod_id
            version = project.version.toString()

            from(components["java"])
        }
    }

    repositories {
        maven {
            val targetRepo = if (isRelease) "releases" else "snapshots"
            url = uri("https://maven.tobynguyen.dev/$targetRepo")

            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}
