enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "TodoApp"

pluginManagement {
    includeBuild("build-logic")

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
    }
}

include(":app")
include(":source:core:api")
include(":source:core:designsystem")
include(":source:core:utils")
include(":source:data:network")
include(":source:feature:list")
include(":source:feature:task")
include(":source:core:domain")
include(":source:feature:auth")
include(":source:data:local")
include(":source:feature:settings")
include(":source:data:database")
include(":source:data:repository")
include(":source:feature:about")
