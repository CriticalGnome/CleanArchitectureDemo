plugins {
    id(libs.plugins.java.library.get().pluginId)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    // Dagger
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    // Junit 5
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    // SLF4J
    implementation(libs.slf4j.api)
    implementation(libs.slf4j.simple)
    testImplementation(libs.slf4j.simple)
    // Coroutines
    testImplementation(libs.kotlinx.coroutines.test)
    // Turbine
    testImplementation(libs.turbine)
    // MockK
    testImplementation(libs.mockk)
}
