plugins {
    id("org.jetbrains.kotlin.js") version "1.4.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    js {
        browser {}
    }
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.js")
    }

    repositories {
        mavenCentral()
    }

    val implementation by configurations

    dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.3.9")
    }

    tasks.register<Copy>("copyDistributionToRoot") {
        group = "build"
        description = "Copies the distribution files to the root project distribution directory."

        from("$buildDir/distributions")
        into("${parent?.buildDir}/distributions")
    }

    tasks["build"].finalizedBy("copyDistributionToRoot")
}

tasks["run"].dependsOn(":serviceWorker:copyDevelopmentWebpackToClient")
