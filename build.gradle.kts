plugins {
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.noarg)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.spring)
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(libs.flyway)
    implementation(libs.flyway.postgres)
    implementation(libs.postgres)

    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.data.jdbc)
    implementation(libs.spring.boot.starter.sftp)

    testImplementation(testLibs.io.kotest.kotestAllure)
    testImplementation(testLibs.io.kotest.kotestRunnerJunitJvm)
    testImplementation(testLibs.io.kotest.kotestSpring)
    testImplementation(testLibs.io.kotest.kotestTestcontainers)

    testImplementation(testLibs.org.testcontainers.cockroachdb)
    testImplementation(testLibs.org.testcontainers.junit.jupiter)
    testImplementation(testLibs.org.testcontainers.testcontainers)

    testImplementation(testLibs.spring.boot.starter.test)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
