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
"wisemeal-admin:wisemeal-admin-server",
"wisemeal-admin:wisemeal-admin-front",
"wisemeal-application",
"wisemeal-common",
//clients
"wisemeal-clients:map",
"wisemeal-clients:jwt"
)
