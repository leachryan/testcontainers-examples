pluginManagement {

    repositories {
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "testcontainers-examples"

dependencyResolutionManagement {

    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    versionCatalogs {
        create("testLibs") {
            from(files("./gradle/testLibs.versions.toml"))
        }
    }
}