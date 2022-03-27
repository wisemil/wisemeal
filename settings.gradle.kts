/**
 * build.gradle.kts plugins{} 에서 호출될 플러그인용 repository 지정
 */
pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "wisemeal"

include(
"wisemeal-core",
"wisemeal-api",
"wisemeal-external:map:port",
"wisemeal-external:map:kakao-adapter",
"wisemeal-persistence:port",
"wisemeal-persistence:jpa-adapter"
)
