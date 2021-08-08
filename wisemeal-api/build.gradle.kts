import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.getByName<BootJar>("bootJar") {
    enabled = true
    mainClassName = "wisemil.wisemeal.api.main.WiseMealApiApplicationKt"
    archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")

    dependsOn(tasks.asciidoctor)
    from("build/asciidoc/html5") {
        into ("static/docs")
    }
}

plugins {
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":wisemeal-common"))
    implementation(project(":wisemeal-core"))
    implementation(project(":wisemeal-application"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    testImplementation("org.springframework.security:spring-security-test")
}


tasks.processResources {
    copy {
        description = "secret 모듈의 기밀 파일을 운영 모듈로 옮긴다."
        from("../wisemeal-secret/api")
        into("src/main/resources")
    }
}

tasks {
    description = "운영 모듈에서 작성한 기밀파일을 secret 모듈로 옮긴다."
    val backUpApiSecret = "backUpApiSecret"

    val secretContents = copySpec {
        from("src/main/resources")
        include("application-auth.yml")
    }

    register(backUpApiSecret, Copy::class) {
        into("../wisemeal-secret/api")
        includeEmptyDirs = false
        with(secretContents)
    }
}

val snippetsDir = file("build/generated-snippets")

tasks.test {
    outputs.dir(snippetsDir)
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
}
