plugins {
    id(Plugins.javaLibrary)
    id(Plugins.kotlinJvm)
    id(Plugins.kapt)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kapt {
    correctErrorTypes = true
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    // Coroutines
    implementation(Libraries.coroutinesCore)
    // Dagger
    implementation(Libraries.dagger)
    kapt(Libraries.daggerCompiler)
    // Junit 5
    testImplementation(TestLibraries.junitJupiterApi)
    testRuntimeOnly(TestLibraries.junitJupiterEngine)
    // Coroutines
    testImplementation(TestLibraries.coroutinesTest)
    // Turbine
    testImplementation(TestLibraries.turbine)
    // MockK
    testImplementation(TestLibraries.mockk)
}
