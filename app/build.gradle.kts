plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id ("com.google.devtools.ksp") version "1.6.21-1.0.5"
    id("com.google.gms.google-services")
}

val composeVersion = "1.2.0-rc02"
val roomVersion = "2.4.2"

android {
    namespace = appId
    compileSdk = 32

    defaultConfig {
        applicationId = appId
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = appVersion
        testInstrumentationRunner = testRunner
        vectorDrawables { useSupportLibrary = true }
    }
        buildTypes {
            release {
                isMinifyEnabled = true
                proguardFiles(getDefaultProguardFile(proGName), proGRules)
            }
        }

        composeOptions { kotlinCompilerExtensionVersion = composeVersion }
        compileOptions { sourceCompatibility = javaVersion; targetCompatibility = javaVersion }
        kotlinOptions {
            jvmTarget = jvm; freeCompilerArgs = argsList
        }
        buildFeatures { compose = true }
        packagingOptions { resources { excludes += res } }
    }

dependencies {

    //Dagger
    implementation("com.google.dagger:dagger:2.42")
    kapt("com.google.dagger:dagger-compiler:2.42")

    //Base
    implementation("androidx.core:core-ktx:1.9.0-alpha05")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0-rc02")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0-rc02")

    //Compose
    implementation("androidx.compose.ui:ui:1.2.0-rc02")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.compiler:compiler:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.compose.animation:animation:$composeVersion")
    implementation("androidx.compose.material3:material3:1.0.0-alpha13")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1")


    //Room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    //Tests
    implementation("androidx.benchmark:benchmark-junit4:1.1.0")

    //Firebase
    implementation ("com.google.firebase:firebase-bom:30.2.0")
    implementation ("com.google.firebase:firebase-database-ktx:20.0.5")
    implementation("com.google.firebase:firebase-auth-ktx:21.0.6")
    implementation ("com.google.android.gms:play-services-auth:20.2.0")

    //Koil
    implementation("io.coil-kt:coil-compose:2.1.0")
}





