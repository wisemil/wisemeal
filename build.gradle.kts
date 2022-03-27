import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version Dependencies.Versions.springBoot apply false
    id("io.spring.dependency-management") version Dependencies.Versions.springDependencyManagement

    kotlin("jvm") version Dependencies.Versions.kotlin
    kotlin("plugin.spring") version Dependencies.Versions.kotlin apply false
    kotlin("plugin.jpa") version Dependencies.Versions.kotlin apply false
    kotlin("kapt") version Dependencies.Versions.kotlin apply false
    kotlin("plugin.allopen") version Dependencies.Versions.kotlin apply false
    kotlin("plugin.noarg") version Dependencies.Versions.kotlin apply false
}

allprojects {
    apply {
        plugin("idea")
    }

    repositories {
        mavenCentral()
    }

    group = "${property("projectGroup")}"
    version = "${property("projectVersion")}"
}

val kotlinProjects = listOf(
    project(":wisemeal-external:map:port"),
    project(":wisemeal-persistence:port"),
    project(":wisemeal-core")
)
configure(kotlinProjects) {
    apply {
        plugin<JavaLibraryPlugin>()
        plugin<KotlinPlatformJvmPlugin>()
    }

    dependencies {
        testImplementation("io.kotest:kotest-runner-junit5-jvm:${Dependencies.Versions.kotest}")
        testImplementation("io.kotest:kotest-assertions-core-jvm:${Dependencies.Versions.kotest}")
        testImplementation("io.kotest:kotest-property-jvm:${Dependencies.Versions.kotest}")

        testImplementation("org.junit.jupiter", "junit-jupiter", "5.8.2")
        testImplementation("org.assertj", "assertj-core", "3.22.0")
    }

    tasks.withType<KotlinCompile> {
        sourceCompatibility = "11"

        kotlinOptions {
            freeCompilerArgs.plus("-Xjsr305=strict")
            freeCompilerArgs.plus("-Xjvm-default=enable")
            freeCompilerArgs.plus("-progressive")
            freeCompilerArgs.plus("-XXLanguage:+InlineClasses")

            jvmTarget = "11"
        }

        dependsOn("processResources")
    }

}

val kotlinSpringBootProjects = listOf(
    project(":wisemeal-api"),
    project(":wisemeal-persistence:jpa-adapter"),
    project(":wisemeal-external:map:kakao-adapter")
)
configure(kotlinSpringBootProjects) {
    apply {
        plugin<JavaLibraryPlugin>()
        plugin<KotlinPlatformJvmPlugin>()
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${Dependencies.Versions.springCloud}")
            mavenBom("org.springframework.boot:spring-boot-dependencies:${Dependencies.Versions.springBoot}")
        }

        dependencies {
            dependency("com.querydsl:querydsl-jpa:${Dependencies.Versions.querydsl}")
            dependencySet("io.kotest:${Dependencies.Versions.kotest}") {
                entry("kotest-runner-junit5-jvm")
                entry("kotest-assertions-core-jvm")
                entry("kotest-property-jvm")
                entry("kotest-extensions-spring-jvm")
            }
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-validation")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

        testImplementation("org.springframework.boot:spring-boot-starter-test")

        testImplementation("io.kotest:kotest-runner-junit5-jvm")
        testImplementation("io.kotest:kotest-assertions-core-jvm")
        testImplementation("io.kotest:kotest-property-jvm")
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    tasks.withType<KotlinCompile> {
        sourceCompatibility = "11"

        kotlinOptions {
            freeCompilerArgs.plus("-Xjsr305=strict")
            freeCompilerArgs.plus("-Xjvm-default=enable")
            freeCompilerArgs.plus("-progressive")
            freeCompilerArgs.plus("-XXLanguage:+InlineClasses")

            jvmTarget = "11"
        }

        dependsOn("processResources")
    }

    tasks.withType<Test> {
        useJUnitPlatform {
            excludeTags.add("integration")
        }
    }

    tasks.register("integrationTest", Test::class) {
        useJUnitPlatform {
            includeTags.add("integration")
        }
    }

}
