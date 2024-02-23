plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.junit5)
}

android {
    namespace = "com.criticalgnome.cleanarchitecturedemo"
    compileSdk = libs.versions.target.sdk.get().toInt()

    defaultConfig {
        applicationId = "com.criticalgnome.cleanarchitecturedemo"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = libs.versions.version.code.get().toInt()
        versionName = libs.versions.version.name.get()

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

dependencies {
    // Modules
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":data")))
    // Core
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.fragment.ktx)
    implementation(libs.material)
    // Dagger
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    // Hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
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
    // Core Android
    androidTestImplementation(libs.runner)
    // Junit 5 Android
    androidTestImplementation(libs.junit.jupiter.api)
    androidTestImplementation(libs.android.test.core)
    androidTestRuntimeOnly(libs.android.test.runner)
    // MockK Android
    androidTestImplementation(libs.mockk.android)
}
