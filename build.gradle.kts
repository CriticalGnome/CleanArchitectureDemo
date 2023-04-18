// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.androidApplication) version Versions.android apply false
    id(Plugins.androidLibrary) version Versions.android apply false
    id(Plugins.kotlinJvm) version Versions.kotlin apply false
    id(Plugins.kotlinAndroid) version Versions.kotlin apply false
    id(Plugins.kotlinSerialization) version Versions.kotlin apply false
    id(Plugins.hiltAndroid) version Versions.dagger apply false
    id(Plugins.androidJunit5) version Versions.junit5Plugin apply false
}
