plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven {
        name = "NeoForge"
        url = uri("https://maven.neoforged.net/releases")
    }
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }
}

dependencies {
    // See: https://projects.neoforged.net/neoforged/ModDevGradle
    implementation("net.neoforged:moddev-gradle:2.0.148")
    // See: https://maven.fabricmc.net/net/fabricmc/fabric-loom/
    implementation("net.fabricmc.fabric-loom:net.fabricmc.fabric-loom.gradle.plugin:1.19.4")
    // See: https://github.com/neoforged/ModDevGradle/blob/main/build.gradle
    implementation("gradle.plugin.org.jetbrains.gradle.plugin.idea-ext:gradle-idea-ext:1.2")
    // https://mvnrepository.com/artifact/info.solidsoft.gradle.pitest/gradle-pitest-plugin/versions?repo=gradle-plugins
    implementation("info.solidsoft.gradle.pitest:gradle-pitest-plugin:1.19.0-rc.2")
    // https://mvnrepository.com/artifact/org.sonarqube/org.sonarqube.gradle.plugin
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:7.2.2.6593")
    // See: https://modmuss50.github.io/mod-publish-plugin/getting_started/
    implementation("me.modmuss50:mod-publish-plugin:2.1.1")
}
