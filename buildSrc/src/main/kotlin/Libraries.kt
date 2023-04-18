object Libraries {
    // Core
    val androidxCore by lazy { "androidx.core:core-ktx:${Versions.androidCore}" }
    val androidxAppcompat by lazy { "androidx.appcompat:appcompat:${Versions.appcompat}" }
    val anddroidxConstraint by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
    val androidxFragment by lazy { "androidx.fragment:fragment-ktx:${Versions.androidFragment}" }
    val material by lazy { "com.google.android.material:material:${Versions.material}" }
    val kotlinSerialization by lazy { "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinSerialization}" }
    // Coroutines
    val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}" }
    // Dagger
    val dagger by lazy { "com.google.dagger:dagger:${Versions.dagger}" }
    val daggerCompiler by lazy { "com.google.dagger:dagger-compiler:${Versions.dagger}" }
    // Hilt
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.dagger}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-compiler:${Versions.dagger}" }
    // Retrofit
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val moshiConverter by lazy { "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}" }
    val kotlinSerializationConverter by lazy { "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.kotlinSerializationConverter}" }
    // OkHttp
    val loggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}" }
    // Room
    val room by lazy { "androidx.room:room-runtime:${Versions.room}" }
    val roomKtx by lazy { "androidx.room:room-ktx:${Versions.room}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.room}" }
}
