import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.getByName<Jar>("jar") {
    enabled = true
}
tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

plugins {
    kotlin("plugin.spring")
    kotlin("plugin.allopen")
    kotlin("plugin.noarg")
    kotlin("kapt")
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":wisemeal-external:map:port"))

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
}
