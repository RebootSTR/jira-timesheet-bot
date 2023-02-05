import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://m2proxy.atlassian.com/repository/public") }
}

dependencies {
    implementation("com.atlassian.jira:jira-rest-java-client-api:5.2.2")
    implementation("com.atlassian.jira:jira-rest-java-client-core:5.2.2")
    implementation("io.atlassian.fugue:fugue:5.0.0")
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.0.7")

    implementation("org.jetbrains.exposed", "exposed-core", "0.40.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.40.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.40.1")
    implementation("org.xerial:sqlite-jdbc:3.30.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
