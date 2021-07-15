import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.getByName<BootJar>("bootJar") {
    enabled = true
//    mainClassName = "wisemil.wisemeal.api.main.WiseMealApiApplicationKt"
    archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")

    dependsOn(tasks.asciidoctor)
    from("build/asciidoc/html5") {
        into ("BOOT-INF/classes/static/docs")
    }
}

plugins {
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":wisemeal-core"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-configuration-processor")

}

val snippetsDir = file("build/generated-snippets")

tasks.test {
    outputs.dir(snippetsDir)
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
}
