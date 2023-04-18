@file:Suppress("UnstableApiUsage")

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinSerialization)
    id(Plugins.kapt)
    id(Plugins.hiltAndroid)
    id(Plugins.androidJunit5)
}

android {
    namespace = Application.dataName
    compileSdk = Application.currentSdk

    defaultConfig {
        minSdk = Application.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] = "de.mannodermaus.junit5.AndroidJUnit5Builder"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        buildFeatures {
            buildConfig = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    // Modules
    implementation(project(mapOf("path" to ":domain")))
    // Core
    implementation(Libraries.androidxCore)
    implementation(Libraries.androidxAppcompat)
    implementation(Libraries.material)
    implementation(Libraries.kotlinSerialization)
    // Dagger
    implementation(Libraries.dagger)
    kapt(Libraries.daggerCompiler)
    // Hilt
    implementation(Libraries.hilt)
    kapt(Libraries.hiltCompiler)
    // Retrofit
    implementation(Libraries.retrofit)
    implementation(Libraries.moshiConverter)
    implementation(Libraries.kotlinSerializationConverter)
    // OkHttp
    implementation(Libraries.loggingInterceptor)
    // Room
    implementation(Libraries.room)
    implementation(Libraries.roomKtx)
    kapt(Libraries.roomCompiler)

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
    // Room
    testImplementation(TestLibraries.roomTesting)
    // Core Android
    androidTestImplementation(TestLibraries.androidxTestRunner)
    // Junit 5 Android
    androidTestImplementation(TestLibraries.junitJupiterApi)
    androidTestImplementation(TestLibraries.junitAndroidCore)
    androidTestRuntimeOnly(TestLibraries.junitAndroidTestRunner)
    // MockK Android
    androidTestImplementation(TestLibraries.mockkAndroid)
}
