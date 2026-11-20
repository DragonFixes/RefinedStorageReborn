import org.sonarqube.gradle.SonarExtension

plugins {
    id("com.refinedmods.refinedarchitect.root")
    id("com.refinedmods.refinedarchitect.base")
}

refinedarchitect {
    sonarQube("refinedmods_refinedstorage2", "refinedmods")
}

// Every jar a subproject builds (mod jars, sources and javadoc jars) is also copied to <root>/deploy.
val deployDir = layout.projectDirectory.dir("deploy")

subprojects {
    group = "com.refinedmods.refinedstorage"

    val deployJars = tasks.register<Copy>("deployJars") {
        group = "build"
        description = "Copies the jars of this project to the root deploy folder."
        from(tasks.withType<Jar>())
        into(deployDir)
    }
    tasks.matching { it.name == "build" }.configureEach {
        finalizedBy(deployJars)
    }
}

tasks.matching { it.name == "clean" && it is Delete }.configureEach {
    (this as Delete).delete(deployDir)
}

project.extensions.getByType<SonarExtension>().apply {
    properties {
        property(
            "sonar.coverage.exclusions",
            "refinedstorage-neoforge-api/**/*,refinedstorage-neoforge/**/*,refinedstorage-fabric-api/**/*,refinedstorage-fabric/**/*,refinedstorage-common/**/*,refinedstorage-common-api/**/*"
        )
        property(
            "sonar.exclusions",
            "refinedstorage-common/src/main/java/com/refinedmods/refinedstorage/common/repackage/**"
        )
    }
}
