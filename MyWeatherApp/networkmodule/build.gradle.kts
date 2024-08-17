plugins {
    alias(libs.plugins.androidLibrary)
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
//    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltPlugin)
    alias(libs.plugins.secretsMapsplatform)

}

android {
    namespace = "com.example.networkmodule"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    secrets {
        propertiesFileName = "secrets.properties"
        defaultPropertiesFileName = "local.defaults.properties"

    }
    kapt {
        correctErrorTypes = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.dagger)
//    ksp "com.google.dagger:dagger-compiler:2.48"; // Dagger compiler
//    ksp "com.google.dagger:hilt-compiler:2.48";  // Hilt compiler

//    implementation(libs.hilt.android.compiler)
    
    implementation(libs.material)
    implementation(libs.adapter.guava)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)
    implementation(libs.gson)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    /**
     * Coroutines implementation
     */
    implementation(libs.kotlin.coroutine)
    implementation(libs.kotlin.coroutine.android)


//    implementation(libs.kotlin.coil)
//    /**
//     * Navigation components
//     */
//    def navigationVersion = "2.7.6"
//    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
//    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

//    /**
//     * Dagger-Hilt component
//     */
//    def daggerHiltVersion = "2.50"
//    implementation("com.google.dagger:hilt-android:$daggerHiltVersion")
//    kapt "com.google.dagger:hilt-compiler:$daggerHiltVersion")

//    /**
//     * Lifecycle components
//     */
//    def lifecycleVersion = "2.6.2"
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

//    /**
//     * Encryption/Decryption components
//     */
//    def cryptoVersion = "1.0.0"
//    implementation("androidx.security:security-crypto:$cryptoVersion")

//    /**
//     * Logging interceptor components
//     */
//    def okHttpVersion = "4.11.0"
//    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
//    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")
//
//    /**
//     * Encrypted room component
//     */
//    def cipherVersion = "4.5.3"
//    implementation("net.zetetic:android-database-sqlcipher:$cipherVersion")

    /**
     * Compose related component
     */
    //implementation"androidx.activity:activity-compose:1.8.2"
    //implementation"androidx.compose.ui:ui:1.6.1"
    //implementation"androidx.compose.material3:material3:1.2.0"
    //implementation("androidx.compose.ui:ui-tooling-preview"
    //implementation"androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0"
    //debugimplementation("androidx.compose.ui:ui-tooling"

    /**
     * Circle image view component
     */
    //def circleImageView = "3.1.0"
    //implementation"de.hdodenhof:circleimageview:$circleImageView"

//    /**
//     * Glide component
//     */
//    def glide = "4.16.0"
//    implementation("com.github.bumptech.glide:glide:$glide")
//
//    /**
//     * Coil component
//     */
//    //def coilVersion = "2.6.0"
//    //implementation"io.coil-kt:coil-compose:$coilVersion"
//
//    testIementation("junit:junit:4.13.2")
//    androidTestimplementation("androidx.test.ext:junit:1.1.5")
//    androidTestimplementation("androidx.test.espresso:espresso-core:3.5.1")
//
}