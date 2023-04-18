@file:Suppress("UnstableApiUsage")

plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kapt)
    id(Plugins.hiltAndroid)
    id(Plugins.androidJunit5)
}

android {
    namespace = Application.appName
    compileSdk = Application.currentSdk

    defaultConfig {
        applicationId = "com.criticalgnome.cleanarchitecturedemo"
        minSdk = Application.minSdk
        targetSdk = Application.currentSdk
        versionCode = Application.versionCode
        versionName = Application.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] = "de.mannodermaus.junit5.AndroidJUnit5Builder"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    // Modules
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":data")))
    // Core
    implementation(Libraries.androidxCore)
    implementation(Libraries.androidxAppcompat)
    implementation(Libraries.anddroidxConstraint)
    implementation(Libraries.androidxFragment)
    implementation(Libraries.material)
    // Dagger
    implementation(Libraries.dagger)
    kapt(Libraries.daggerCompiler)
    // Hilt
    implementation(Libraries.hilt)
    kapt(Libraries.hiltCompiler)
    // Junit 5
    testImplementation(TestLibraries.junitJupiterApi)
    testImplementation(TestLibraries.junitJupiterParams)
    testRuntimeOnly(TestLibraries.junitJupiterEngine)
    // Coroutines
    testImplementation(TestLibraries.coroutinesTest)
    // Turbine
    testImplementation(TestLibraries.turbine)
    // MockK
    testImplementation(TestLibraries.mockk)
    // Core Android
    androidTestImplementation(TestLibraries.androidxTestRunner)
    // Junit 5 Android
    androidTestImplementation(TestLibraries.junitJupiterApi)
    androidTestImplementation(TestLibraries.junitAndroidCore)
    androidTestRuntimeOnly(TestLibraries.junitAndroidTestRunner)
    // MockK Android
    androidTestImplementation(TestLibraries.mockkAndroid)
}
