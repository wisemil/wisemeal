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
//clients
    "wisemeal-clients:map"
)
include("wisemeal-api")
include("wisemeal-application")
include("wisemeal-admin")
include("wisemeal-admin:wisemeal-admin-front")
findProject(":wisemeal-admin:wisemeal-admin-front")?.name = "wisemeal-admin-front"
