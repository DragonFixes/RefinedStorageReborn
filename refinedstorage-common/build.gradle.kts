plugins {
    id("com.refinedmods.refinedarchitect.common")
}

repositories {
    maven {
        name = "JEI"
        url = uri("https://maven.blamejared.com/")
        content {
            includeGroup("mezz.jei")
        }
    }
}

refinedarchitect {
    common()
    testing()
    javadoc()
    publishing {
        maven = true
    }
}

base {
    archivesName.set("refinedstorage-common")
}

dependencies {
    api(project(":refinedstorage-common-api"))
    api(project(":refinedstorage-core-api"))
    api(project(":refinedstorage-resource-api"))
    api(project(":refinedstorage-storage-api"))
    api(project(":refinedstorage-network-api"))
    api(project(":refinedstorage-network"))
    api(project(":refinedstorage-query-parser"))
    compileOnly(libs.jei.common.api)
    testImplementation(libs.junit.api)
    testImplementation(libs.junit.params)
    testImplementation(libs.assertj)
    testImplementation(libs.equalsverifier)
    testRuntimeOnly(libs.junit.engine)
}
