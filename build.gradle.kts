// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    allprojects {
        repositories {
//            maven { url = uri("https://www.jitpack.io") }
        }
    }
}
plugins {
    id ("com.android.application") version "8.12.0" apply false
    id ("com.android.library") version "8.12.0" apply false
    id ("org.jetbrains.kotlin.android") version "2.2.0" apply false
    id ("org.jetbrains.kotlin.jvm") version "2.2.0" apply false
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
}
