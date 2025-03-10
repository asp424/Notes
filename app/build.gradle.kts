plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    id("com.google.firebase.crashlytics")
}

android {
    namespace = appId
    compileSdk = 35

    defaultConfig {
        applicationId = appId
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = appVersion
        testInstrumentationRunner = testRunner
        vectorDrawables { useSupportLibrary = true }
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += Pair("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    lint { baseline = file("lint-baseline.xml") }

    buildTypes {

        debug{
           //isMinifyEnabled = true
          // isShrinkResources = true
            proguardFiles(getDefaultProguardFile(proGName), proGRules)
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile(proGName), proGRules)
        }
    }

    buildFeatures { compose = true; viewBinding = true }
    composeOptions {  kotlinCompilerExtensionVersion = composeCompilerVersion }
    compileOptions { sourceCompatibility = javaVersion; targetCompatibility = javaVersion }
    kotlinOptions { jvmTarget = jvm; freeCompilerArgs = argsList }
    packaging { resources { excludes += res } }
}

dependencies {

    //Dagger
    implementation("com.google.dagger:dagger:2.55")
    kapt ("com.google.dagger:dagger-compiler:2.55")

    //Base
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")

    //Compose
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.compose.animation:animation:$composeVersion")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.navigation:navigation-compose:2.8.8")
    implementation ("com.google.accompanist:accompanist-pager:0.27.1")

    //Room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")


    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
    implementation("com.google.firebase:firebase-database-ktx:21.0.0")
    implementation("com.google.firebase:firebase-auth-ktx:23.2.0")
    implementation("com.google.android.gms:play-services-auth:21.3.0")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")

    //Coil
    implementation("io.coil-kt:coil-compose:2.3.0")

    //ColorPicker
    implementation ("com.godaddy.android.colorpicker:compose-color-picker:0.5.0")

    //Keyboard listener
    implementation (
        "net.yslibrary" +
                ".keyboardvisibilityevent:keyboardvisibilityevent:3.0.0-RC3")

    //Leak Canary
   // debugImplementation ("com.squareup.leakcanary:leakcanary-android:2.9.1")
}







