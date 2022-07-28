buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("com.google.gms:google-services:4.3.13")
    }
    repositories.repository
}

allprojects { repositories.repository }

clearProject(rootProject.buildDir)


