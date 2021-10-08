import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.getByName<Jar>("jar") {
    enabled = true
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

plugins {
    kotlin("plugin.spring")
    id("org.springframework.boot")
}

tasks.processResources {
    copy {
        description = "secret 모듈의 기밀 파일을 운영 모듈로 옮긴다."
        from("../../wisemeal-secret/clients/jwt")
        into("src/main/resources")
    }
}

tasks {
    description = "운영 모듈에서 작성한 기밀파일을 secret 모듈로 옮긴다."
    val backUpApiSecret = "backUpApiSecret"

    val secretContents = copySpec {
        from("src/main/resources")
        include("application-jwt.yml")
    }

    register(backUpApiSecret, Copy::class) {
        into("../../wisemeal-secret/clients/jwt")
        includeEmptyDirs = false
        with(secretContents)
    }
}

dependencies {
    implementation(project(":wisemeal-common"))

    implementation("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-configuration-processor")

    implementation("com.auth0:java-jwt:3.18.2")
}
