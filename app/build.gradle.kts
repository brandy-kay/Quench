plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version ("1.9.23-1.0.19")
}

apply {
    from("$rootDir/shared_dependencies.gradle")
}

android {
    compileSdk = AndroidConfig.compileSdk

    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }

    defaultConfig {
        applicationId = AndroidConfig.applicationId
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName

        testInstrumentationRunner = ("androidx.test.runner.AndroidJUnitRunner")
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_version
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(Module.designsystem))
    implementation(project(Module.common))

    // features
    implementation(project(Module.home))
    implementation(project(Module.statistics))
    implementation(project(Module.settings))

    // RamCosta Navigation
    implementation("io.github.raamcosta.compose-destinations:core:1.7.32-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.5.15-beta")

    // Navigation animation
    implementation("com.google.accompanist:accompanist-navigation-animation:0.24.7-alpha")

    // Splash Api dependency
    implementation("androidx.core:core-splashscreen:1.0.1")
}
