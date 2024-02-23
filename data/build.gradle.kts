plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.junit5)
}

android {
    namespace = "com.criticalgnome.data"
    compileSdk = libs.versions.target.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

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

dependencies {
    // Modules
    implementation(project(mapOf("path" to ":domain")))
    // Core
    implementation(libs.core.ktx)
    implementation(libs.kotlinx.serialization.json)
    // Dagger
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    // Hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    // OkHttp
    implementation(libs.logging.interceptor)
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Junit 5
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    // Coroutines
    testImplementation(libs.kotlinx.coroutines.test)
    // Turbine
    testImplementation(libs.turbine)
    // MockK
    testImplementation(libs.mockk)
    // Room
    testImplementation(libs.room.testing)
    // Core Android
    androidTestImplementation(libs.runner)
    // Junit 5 Android
    androidTestImplementation(libs.junit.jupiter.api)
    androidTestImplementation(libs.android.test.core)
    androidTestRuntimeOnly(libs.android.test.runner)
    // MockK Android
    androidTestImplementation(libs.mockk.android)
}
