buildscript {
    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.google.services)
        classpath(libs.firebase.crashlytics.gradle)
    }

    repositories.repository
}

allprojects { repositories.repository }

clearProject(rootProject.buildFile)

