import org.gradle.internal.impldep.org.jsoup.safety.Safelist.basic

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create<BasicAuthentication>("basic")
            }
            credentials {
                // No cambies el usuario de la siguiente línea. Debe ser siempre "mapbox" (no tu usuario).
                username = "mapbox"
                // Usa el token secreto almacenado en gradle.properties como la contraseña
                password = providers.gradleProperty("MAPBOX_DOWNLOADS_TOKEN").get()
                // Usa el token secreto almacenado en gradle.properties como la contraseña
            }
        }
    }
}

rootProject.name = "Trasslado"
include(":app")
