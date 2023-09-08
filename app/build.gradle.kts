plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("androidx.navigation.safeargs.kotlin")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.example.cgtaska"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.cgtaska"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "BASE_URL", "\"https://reqres.in/api/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        dataBinding = true
        buildConfig = true
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

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.13.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.9.0")
    implementation ("jp.wasabeef:glide-transformations:4.3.0")

    // Recyclerview
    implementation ("androidx.recyclerview:recyclerview:1.3.1")

    // Cardview
    implementation ("androidx.cardview:cardview:1.0.0")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Retrofit and Okhttp
    implementation ("com.google.code.gson:gson:2.9.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.11.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0")

    //Rx Java
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation ("io.reactivex.rxjava2:rxjava:2.2.7")

    // Lifecycle components
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    annotationProcessor ("androidx.lifecycle:lifecycle-compiler:2.7.0-alpha01")
    kapt ("androidx.lifecycle:lifecycle-common-java8:2.1.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0-alpha02")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0-alpha02")

    // Navigation component
    implementation ("androidx.navigation:navigation-fragment-ktx:2.6.0-alpha04")
    implementation ("androidx.navigation:navigation-ui-ktx:2.6.0-alpha04")
    implementation ("android.arch.navigation:navigation-fragment-ktx:1.0.0-alpha11")

    // Dagger
    implementation ("com.google.dagger:dagger:2.44.2")
    implementation ("com.google.dagger:dagger-android:2.40.5")
    implementation ("com.google.dagger:dagger-android-support:2.23.2")
    kapt ("com.google.dagger:dagger-compiler:2.44.2")
    kapt ("com.google.dagger:dagger-android-processor:2.23.2")

    // API Log Tools
    implementation ("com.facebook.stetho:stetho:1.5.1")
    implementation ("com.facebook.stetho:stetho-okhttp3:1.5.1")

    // Room DB
    implementation ("androidx.room:room-runtime:2.5.2")
    implementation ("androidx.room:room-ktx:2.5.2")
    implementation ("androidx.room:room-paging:2.5.2")
    kapt ("androidx.room:room-compiler:2.5.2")

    // Swipe refresh Layout
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //Paging
    implementation("androidx.paging:paging-runtime-ktx:3.2.0")

    //SQL Cipher
    implementation ("net.zetetic:android-database-sqlcipher:4.5.0")

    // Android Security Crypto
    implementation ("androidx.security:security-crypto:1.0.0")
}