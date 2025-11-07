import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.teatrope_kotlin_app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.teatrope_kotlin_app"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "API_BASE_URL",
            "\"${project.findProperty("API_BASE_URL") ?: "http://10.0.2.2:8080/"}\""
        )
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions { jvmTarget = JvmTarget.JVM_11 }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // --- Compose (usa BOM del catálogo, no fijes versiones a mano) ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.foundation)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Core / lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Navigation Compose
    implementation(libs.androidx.navigation.compose)
    // Material Icons (opcional)
    implementation(libs.androidx.material.icons.extended)

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Splashscreen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // --- Networking ---
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // --- Coil 2.x (¡sin coil-network-okhttp!) ---
    implementation(libs.coil.compose)
    // Si quieres el core (no estrictamente necesario con compose):
    // implementation(libs.coil)
    // Formatos extra si los usas:
    // implementation(libs.coil.gif)
    // implementation(libs.coil.svg)

    // --- Room ---
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // --- Hilt ---
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation("androidx.core:core-ktx:1.13.1")            // ya entra por catálogo
    implementation("androidx.compose.material3:material3:1.2.1") // usa el del BOM
    implementation("io.coil-kt:coil-compose:2.6.0")             // duplicado del catálogo
    //implementation(libs.coil.network.okhttp)                     // NO existe en Coil 2.x
    implementation("com.google.android.material:material:1.12.0")// solo si usas vistas XML
}
