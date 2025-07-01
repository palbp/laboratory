plugins {
    kotlin("jvm") version "2.1.10"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

group = "me.palbp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("org.slf4j:slf4j-simple:1.7.36")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(23)
}
