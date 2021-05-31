// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id(BuildPlugins.benManes) version Versions.benManes
    id(BuildPlugins.detekt) version Versions.detekt
}

buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(BuildPlugins.androidPlugin)
        classpath(BuildPlugins.kotlinPlugin)
        classpath(BuildPlugins.safeArgsPlugIn)
        classpath(BuildPlugins.daggerHiltPlugin)
        classpath(BuildPlugins.ktSerializerPlugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

/* ./gradlew dependencyUpdates */
tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
    checkForGradleUpdate = true
    outputDir = "${rootProject.buildDir}/dependencyUpdates"
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
    config = files("$projectDir/config/detekt-config.yml") // point to your custom config defining rules to run, overwriting default behavior
    baseline = file("$projectDir/config/detekt-baseline.xml") // a way of suppressing issues before introducing detekt

    reports {
        html.enabled = true // observe findings in your browser with structure and code snippets
        xml.enabled = true // checkstyle like format mainly for integrations like Jenkins
        txt.enabled = true // similar to the console output, contains issue signature to manually edit baseline files
        sarif.enabled = true // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with Github Code Scanning
    }
}

// Kotlin DSL
tasks.withType<Detekt>().configureEach {
    // Target version of the generated JVM bytecode. It is used for type resolution.
    jvmTarget = "1.8"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}