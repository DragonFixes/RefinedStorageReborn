dependencyResolutionManagement {
    repositories {
        maven {
            name = "Refined Architect"
            url = uri("https://maven.creeperhost.net")
            content {
                includeGroupAndSubgroups("com.refinedmods.refinedarchitect")
            }
        }
    }
    versionCatalogs {
        create("libs") {
            val refinedarchitectVersion: String by settings
            from("com.refinedmods.refinedarchitect:refinedarchitect-versioning:${refinedarchitectVersion}")
            // Fabric libraries built for the Minecraft version targeted in build-logic/src/main/kotlin/versions.kt.
            version("cloth-config-fabric", "26.3.158")
            version("modmenu", "21.0.1")
            // See: https://maven.blamejared.com/mezz/jei/
            version("jei", "31.18.0.118")
            library("jei-common-api", "mezz.jei", "jei-26.3-common-api").versionRef("jei")
            library("jei-neoforge-api", "mezz.jei", "jei-26.3-neoforge-api").versionRef("jei")
            library("jei-neoforge", "mezz.jei", "jei-26.3-neoforge").versionRef("jei")
            library("jei-fabric-api", "mezz.jei", "jei-26.3-fabric-api").versionRef("jei")
            library("jei-fabric", "mezz.jei", "jei-26.3-fabric").versionRef("jei")
        }
    }
}

pluginManagement {
    includeBuild("build-logic")
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
    }
}

rootProject.name = "refinedstorage"
include("refinedstorage-core-api")
include("refinedstorage-resource-api")
include("refinedstorage-storage-api")
include("refinedstorage-query-parser")
include("refinedstorage-autocrafting-api")
include("refinedstorage-network-api")
include("refinedstorage-network")
include("refinedstorage-common-api")
include("refinedstorage-common")
include("refinedstorage-fabric")
include("refinedstorage-fabric-api")
include("refinedstorage-neoforge")
include("refinedstorage-neoforge-api")
include("refinedstorage-network-test")
