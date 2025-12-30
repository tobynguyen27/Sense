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
    maven { url = uri("https://maven.parchmentmc.org") }
    maven { url = uri("https://maven.terraformersmc.com/") }
    mavenCentral()
}

sourceSets {
    val testmod by creating {
        val main = sourceSets.getByName<SourceSet>("main")
        compileClasspath += main.compileClasspath
        runtimeClasspath += main.runtimeClasspath
    }
}

loom {
    runs {
        val testmod = sourceSets.getByName("testmod")

        val testmodClient by creating {
            client()
            name = "Testmod Client"
            source(testmod)
        }

        val testmodServer by creating {
            server()
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

kotlin { jvmToolchain(17) }

tasks.named<Jar>("jar") {
    inputs.property("archivesName", project.base.archivesName)

    from("LICENSE") { rename { "${it}_${inputs.properties["archivesName"]}" } }
}

spotless {
    encoding("UTF-8")

    kotlin {
        ktfmt().kotlinlangStyle()
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

        biome("2.3.7")
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
        val mavenUsername = System.getenv("MAVEN_USERNAME")
        val mavenPassword = System.getenv("MAVEN_PASSWORD")

        if (isRelease) {
            maven {
                url = uri("https://maven.tobynguyen.dev/releases")
                credentials {
                    username = mavenUsername
                    password = mavenPassword
                }
            }
        } else {
            maven {
                url = uri("https://maven.tobynguyen.dev/snapshots")
                credentials {
                    username = mavenUsername
                    password = mavenPassword
                }
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
