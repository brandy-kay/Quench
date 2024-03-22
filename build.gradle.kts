buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51")
        classpath("com.diffplug.spotless:spotless-plugin-gradle:6.25.0")
    }
} // Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version ("7.2.1") apply false
    id("com.android.library") version ("7.2.1") apply false
    id("org.jetbrains.kotlin.android") version ("1.8.10") apply false

    id("org.jlleitschuh.gradle.ktlint") version "11.3.1" apply false
    id("com.diffplug.spotless") version "6.25.0" apply false
}

subprojects {
    afterEvaluate {
        project.apply("$rootDir/spotless.gradle")
        project.apply("$rootDir/ktlint.gradle")
    }
}
