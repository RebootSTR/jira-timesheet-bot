import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "1.7.10"
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
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}