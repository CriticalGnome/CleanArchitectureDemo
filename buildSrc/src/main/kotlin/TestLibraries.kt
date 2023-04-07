object TestLibraries {
    // Core
    val androidxTestRunner by lazy { "androidx.test:runner:${Versions.androidTestRunner}" }
    // Junit 5
    val junitJupiterApi by lazy { "org.junit.jupiter:junit-jupiter-api:${Versions.junit5}" }
    val junitJupiterParams by lazy { "org.junit.jupiter:junit-jupiter-params:${Versions.junit5}" }
    val junitJupiterEngine by lazy { "org.junit.jupiter:junit-jupiter-engine:${Versions.junit5}" }
    val junitAndroidCore by lazy { "de.mannodermaus.junit5:android-test-core:${Versions.junit5Android}" }
    val junitAndroidTestRunner by lazy { "de.mannodermaus.junit5:android-test-runner:${Versions.junit5Android}" }
    // Coroutines
    val coroutinesTest by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}" }
    // Turbine
    val turbine by lazy { "app.cash.turbine:turbine:${Versions.turbine}" }
    // MockK
    val mockk by lazy { "io.mockk:mockk:${Versions.mockk}" }
    val mockkAndroid by lazy { "io.mockk:mockk-android:${Versions.mockk}" }
}
