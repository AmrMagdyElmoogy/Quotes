@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.quotes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.quotes"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {
    implementation(libs.androidx.navigation.fragment)
    val lifecycle_version = "2.6.2"
    val room_version = "2.6.1"
    val nav_version = "2.7.5"
    val lottieVersion = "3.4.0"
    val paging_version = "3.2.1"
    implementation("org.jsoup:jsoup:1.14.3")
    implementation("com.google.dagger:hilt-android:2.44")
    ksp("com.google.dagger:hilt-android-compiler:2.44")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    implementation("androidx.paging:paging-runtime:$paging_version")
    implementation("io.coil-kt:coil:2.5.0")
    implementation("com.airbnb.android:lottie:$lottieVersion")
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}