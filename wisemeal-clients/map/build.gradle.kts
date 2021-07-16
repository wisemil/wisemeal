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
dependencies {
    implementation("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("io.github.openfeign:feign-okhttp")

    implementation("javax.xml.bind:jaxb-api:2.1")


    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.0") {
        exclude("com.squareup.okhttp3", "okhttp")
    }
}
