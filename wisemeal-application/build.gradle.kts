import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.getByName<Jar>("jar") {
    enabled = true
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

plugins {
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":wisemeal-core"))

    runtimeOnly("com.h2database:h2")
    api("org.mariadb.jdbc:mariadb-java-client")

    implementation("org.springframework.boot:spring-boot-starter-validation")
    runtimeOnly("net.logstash.logback:logstash-logback-encoder:6.2")
}

tasks.processResources {
    copy {
        description = "secret 모듈의 기밀 파일을 운영 모듈로 옮긴다."
        from("../wisemeal-secret/application")
        into("src/main/resources")
    }
}

tasks {
    description = "운영 모듈에서 작성한 기밀파일을 secret 모듈로 옮긴다."
    val backUpApplicationSecret = "backUpApplicationSecret"

    val secretContents = copySpec {
        from("src/main/resources")
        include("application-db-prod.yml")
    }

    register(backUpApplicationSecret, Copy::class) {
        into("../wisemeal-secret/application")
        includeEmptyDirs = false
        with(secretContents)
    }
}
