import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.getByName<BootJar>("bootJar") {
    enabled = true
    mainClassName = "wisemil.wisemeal.admin.main.WiseMealAdminApplicationKt"
    archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
}

plugins {
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("plugin.noarg")
    kotlin("plugin.allopen")
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":wisemeal-core"))
    implementation(project(":wisemeal-application"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}
