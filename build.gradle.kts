import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.spring.dependency-management") version Dependencies.Versions.springDependencyManagement
    id("org.springframework.boot") version Dependencies.Versions.springBoot apply false
    id("org.asciidoctor.convert") version Dependencies.Versions.asciidoctorConvert apply false

    kotlin("jvm") version Dependencies.Versions.kotlin
    kotlin("plugin.spring") version Dependencies.Versions.kotlin apply false
    kotlin("plugin.jpa") version Dependencies.Versions.kotlin apply false
    kotlin("kapt") version Dependencies.Versions.kotlin apply false
    kotlin("plugin.allopen") version Dependencies.Versions.kotlin
    kotlin("plugin.noarg") version Dependencies.Versions.kotlin
}

allprojects {
    apply {
        plugin("idea")
    }

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }

    group = "${property("projectGroup")}"
    version = "${property("projectVersion")}"
}

val kotlinProjects = listOf(
    project(":wisemeal-core"),
    project(":wisemeal-api"),
    project(":wisemeal-application"),
    project(":wisemeal-admin:wisemeal-admin-server"),
    project(":wisemeal-clients:map")
)
configure(kotlinProjects) {
    apply {
        plugin<JavaLibraryPlugin>()
        plugin<KotlinPlatformJvmPlugin>()
        plugin("io.spring.dependency-management")
        plugin("org.springframework.boot")
        plugin("jacoco")
    }

    dependencyManagement {
        imports {
            mavenBom("org.jetbrains.kotlin:kotlin-bom:${Dependencies.Versions.kotlin}")
            mavenBom("org.springframework.boot:spring-boot-dependencies:${Dependencies.Versions.springBoot}")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${Dependencies.Versions.springCloud}")
            mavenBom("com.amazonaws:aws-java-sdk-bom:${Dependencies.Versions.awsSdk}")
        }

        dependencies {
            dependency("com.querydsl:querydsl-jpa:${Dependencies.Versions.querydsl}")
            dependencySet("io.kotest:${Dependencies.Versions.kotest}") {
                entry("kotest-runner-junit5-jvm")
                entry("kotest-assertions-core-jvm")
                entry("kotest-property-jvm")
                entry("kotest-extensions-spring-jvm")
            }
            dependency("io.mockk:mockk:${Dependencies.Versions.mockk}")
            dependencySet("io.github.microutils:${Dependencies.Versions.kotlinLogging}") {
                entry("kotlin-logging-jvm")
                entry("kotlin-logging-common")
            }
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("io.github.microutils:kotlin-logging-jvm")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

        testImplementation("org.springframework.boot:spring-boot-starter-test")

        /** @see <a href="https://kotest.io/">kotest</a>*/
        testImplementation("io.kotest:kotest-runner-junit5-jvm")
        testImplementation("io.kotest:kotest-assertions-core-jvm")
        testImplementation("io.kotest:kotest-property-jvm")

        /** @see <a href="https://github.com/mockk/mockk">Mock K<a/>*/
        testImplementation("io.mockk:mockk")
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    configurations.all {
        resolutionStrategy.eachDependency {
            when (requested.group) {
                "com.squareup.okhttp3" -> useVersion("4.9.0")
            }
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

    configure<JacocoPluginExtension> {
        toolVersion = Dependencies.Versions.jacoco
    }

    tasks.withType<JacocoReport> {
        executionData.setFrom(fileTree(buildDir).include("jacoco/jacoco*.exec"))
        reports {
            html.isEnabled = false
            xml.isEnabled = true
            csv.isEnabled = false
        }
    }

    tasks.withType<JacocoCoverageVerification> {
        violationRules {
            rule {
                element = "BUNDLE"

                limit {
                    counter = "BRANCH"
                    value = "COVEREDRATIO"
                    minimum = "0.5".toBigDecimal()
                }
            }
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform {
            excludeTags.add("integration")
        }
        extensions.configure(JacocoTaskExtension::class) {
            setDestinationFile(file("${buildDir}/jacoco/jacoco.exec"))
        }
        finalizedBy("jacocoTestReport")
    }

    tasks.register("integrationTest", Test::class) {
        useJUnitPlatform {
            includeTags.add("integration")
        }
        extensions.configure(JacocoTaskExtension::class) {
            setDestinationFile(file("${buildDir}/jacoco/jacocoIntegration.exec"))
        }
        finalizedBy("jacocoTestReport")
    }

}

val requireRestDocProjects = listOf(
    project(":wisemeal-api")
)

configure(requireRestDocProjects) {
    apply {
        plugin("io.spring.dependency-management")
        plugin("org.asciidoctor.convert")
    }

    extra["snippetsDir"] = file("build/generated-snippets")

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    }
}

tasks.register("buildAll", GradleBuild::class) {
    tasks = listOf(
        "clean",
        "build",
        "integrationTest"
    )
}
