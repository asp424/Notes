plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

@Suppress("UnstableApiUsage")
android {
    namespace = appId
    compileSdk = 33

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
    packagingOptions { resources { excludes += res } }
}

dependencies {

    //Dagger
    implementation("com.google.dagger:dagger:2.44.2")
    kapt("com.google.dagger:dagger-compiler:2.44.2")

    //Base
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0-alpha03")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0-alpha03")

    //Compose
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.compose.animation:animation:$composeVersion")
    implementation("androidx.compose.material3:material3:1.1.0-alpha02")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.24.1-alpha")
    implementation ("com.google.accompanist:accompanist-pager:0.27.1")

    //Room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    //Tests
    implementation("androidx.benchmark:benchmark-junit4:1.1.1")

    //Firebase
    implementation("com.google.firebase:firebase-bom:31.1.0")
    implementation("com.google.firebase:firebase-database-ktx:20.1.0")
    implementation("com.google.firebase:firebase-auth-ktx:21.1.0")
    implementation("com.google.android.gms:play-services-auth:20.4.0")

    //Coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    //ColorPicker
    implementation ("com.godaddy.android.colorpicker:compose-color-picker:0.5.0")

    //Keyboard listener
    implementation (
        "net.yslibrary" +
                ".keyboardvisibilityevent:keyboardvisibilityevent:3.0.0-RC3")

    //Leak Canary
   // debugImplementation ("com.squareup.leakcanary:leakcanary-android:2.9.1")
}







