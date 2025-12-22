plugins {
    id("net.fabricmc.fabric-loom-remap") version "1.14-SNAPSHOT"
    kotlin("jvm") version "2.3.0"
    id("com.diffplug.spotless") version "8.1.0"
    id("maven-publish")
}

val runNumber = if(System.getenv("GITHUB_RUN_NUMBER") == null) "9999" else System.getenv("GITHUB_RUN_NUMBER")
val isRelease = project.hasProperty("release")

val minecraft_version: String by project
val loader_version: String by project

val mod_id: String by project
val mod_name: String by project
val mod_version: String by project
val mod_description: String by project
val mod_license: String by project
val maven_group: String by project

version = if(isRelease) mod_version else "$mod_version-build.$runNumber"
group = maven_group

base.archivesName = mod_id

repositories {
    maven { url = uri("https://maven.parchmentmc.org") }
    maven { url = uri("https://maven.terraformersmc.com/") }
    mavenCentral()
}

dependencies {
    val fabric_version: String by project
    val fabric_kotlin_version: String by project

    val modmenu_version: String by project

    minecraft("com.mojang:minecraft:$minecraft_version")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-1.18.2:2022.11.06@zip")
    })

    testImplementation("net.fabricmc:fabric-loader-junit:$loader_version")

    modImplementation("net.fabricmc:fabric-loader:$loader_version")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabric_version")
    modImplementation("net.fabricmc:fabric-language-kotlin:$fabric_kotlin_version")

    modImplementation("com.terraformersmc:modmenu:$modmenu_version") {
        exclude(group = "net.fabricmc")
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<ProcessResources>().configureEach {
    val replaceProperties = hashMapOf(
        "loader_version"         to loader_version,
        "minecraft_version"      to minecraft_version,
        "mod_id"                 to mod_id,
        "mod_name"               to mod_name,
        "mod_license"            to mod_license,
        "mod_version"            to mod_version,
        "mod_description"        to mod_description
    )

    inputs.properties(replaceProperties)

    filesMatching(setOf("fabric.mod.json")) {
        expand(replaceProperties)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

kotlin {
    jvmToolchain(17)
}

tasks.named<Jar>("jar") {
    inputs.property("archivesName", project.base.archivesName)

    from("LICENSE") {
        rename { "${it}_${inputs.properties["archivesName"]}" }
    }
}

spotless {
    encoding("UTF-8")

    kotlin {
        ktfmt().kotlinlangStyle()
        endWithNewline()
        toggleOffOn()
    }

    java {
        importOrder()
        removeUnusedImports()
        palantirJavaFormat()
    }

    json {
        target("src/*/resources/**/*.json")
        targetExclude("src/generated/resources/**")

        biome("2.3.7").downloadDir(File(rootDir, ".gradle/biome").absolutePath).configPath(File(rootDir, "spotless/biome.json").absolutePath)

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
