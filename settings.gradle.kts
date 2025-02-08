rootProject.name = "vacancy_backend"

buildCache {
    local {
        directory = File("tmp", "build-cache")
        removeUnusedEntriesAfterDays = 30
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    val kotlinVersion: String by settings

    plugins {
        // other plugins: spring boot, graalvm, etc...
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
        kotlin("kapt") version kotlinVersion
    }
}