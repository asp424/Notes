
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics.ktx)
}

android {
    namespace = appId
    compileSdk = 35

    defaultConfig {
        applicationId = appId
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = appVersion
        vectorDrawables { useSupportLibrary = true }
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += Pair("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    lint { baseline = file("lint-baseline.xml") }

    buildTypes {

        debug {
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
    composeOptions { kotlinCompilerExtensionVersion = composeCompilerVersion }
    compileOptions { sourceCompatibility = javaVersion; targetCompatibility = javaVersion }
    kotlinOptions { jvmTarget = jvm; freeCompilerArgs = argsList }
    packaging { resources { excludes += res } }
}

dependencies {

    //Dagger
    implementation(libs.dagger.v255)
    ksp(libs.dagger.compiler.v255)

    //Base
    implementation(libs.androidx.core.ktx.v1150)
    implementation(libs.kotlinx.coroutines.core.v173)
    implementation(libs.androidx.lifecycle.runtime.ktx.v287)
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v287)

    //Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material)
    implementation(libs.androidx.lifecycle.viewmodel.compose.v287)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.animation)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.compose.v1101)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.pager)

    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    //Auth with google
    implementation(libs.play.services.auth.v2130)
    // implementation(libs.androidx.credentials)
    // implementation(libs.androidx.credentials.play.services.auth)
    //implementation(libs.googleid)

    //Coil
    implementation(libs.coil.compose)

    //ColorPicker
    implementation(libs.compose.color.picker)

    //Keyboard listener
    implementation(libs.keyboardvisibilityevent)

    //UiController
    implementation(libs.accompanist.systemuicontroller)
}







