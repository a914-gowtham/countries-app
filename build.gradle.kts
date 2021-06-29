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

/*./gradlew check*/
val detektAll by tasks.registering(Detekt::class) {
    description = "Runs over whole code base without the starting overhead for each module."
    parallel = true
    buildUponDefaultConfig = true
    setSource(files(projectDir))
    config.from(files(project.rootDir.resolve("config/detekt-config.yml")))
    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**")
    baseline.set(project.rootDir.resolve("config/detekt-baseline.xml"))
    reports {
        xml.enabled = false
        html {
            enabled = true
            destination = file("${rootProject.buildDir}/reports/detekt-report.html")
        }
        txt {
            enabled = true
            destination = file("${rootProject.buildDir}/reports/detekt-report.txt")
        }
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