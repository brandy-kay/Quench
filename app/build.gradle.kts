plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version ("1.7.10-1.0.6")
}

android {
    compileSdk = 33

    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }

    defaultConfig {
        applicationId = ("com.brandyodhiambo.quench")
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = ("1.0")

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

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.compose.ui:ui:${Versions.compose_version}")
    implementation("androidx.compose.material:material:${Versions.compose_version}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Versions.compose_version}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.test:core-ktx:1.4.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Versions.compose_version}")
    debugImplementation("androidx.compose.ui:ui-tooling:${Versions.compose_version}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${Versions.compose_version}")

    // Room
    val room_version = ("2.4.3")
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // work-manager
    val work_version = ("2.7.1")
    implementation("androidx.work:work-runtime-ktx:$work_version")

    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.4.3")

    // Compose dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.navigation:navigation-compose:2.6.0-alpha04")
    implementation("com.google.accompanist:accompanist-flowlayout:0.17.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.40.5")
    kapt("com.google.dagger:hilt-android-compiler:2.40.5")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-work:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // RamCosta Navigation
    implementation("io.github.raamcosta.compose-destinations:core:1.5.20-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.5.15-beta")

    // Navigation animation
    implementation("com.google.accompanist:accompanist-navigation-animation:0.24.7-alpha")

    // Accompanist System UI controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.24.10-beta")

    // Livedata
    implementation("androidx.compose.runtime:runtime-livedata:${Versions.compose_version}")

    // Insets
    implementation("com.google.accompanist:accompanist-insets:0.17.0")

    // number picker
    implementation("com.chargemap.compose:numberpicker:1.0.3")

    // Accompanist
    implementation("com.google.accompanist:accompanist-pager:0.25.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.25.0")

    // lottie
    val lottie_version = ("5.2.0")
    implementation("com.airbnb.android:lottie-compose:$lottie_version")

    // charts
    implementation("com.github.MahmoudIbrahim3:android-compose-charts:1.2.2")
}
