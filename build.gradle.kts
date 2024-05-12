import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.sftp)

    testImplementation(testLibs.io.kotest.kotestAllure)
    testImplementation(testLibs.io.kotest.kotestRunnerJunitJvm)
    testImplementation(testLibs.io.kotest.kotestTestcontainers)

    testImplementation(testLibs.org.testcontainers.cockroachdb)
    testImplementation(testLibs.org.testcontainers.junit.jupiter)
    testImplementation(testLibs.org.testcontainers.testcontainers)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
