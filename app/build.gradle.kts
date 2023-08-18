//@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.hilt)
//    alias(libs.plugins.ksp)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.ykis.ykispam"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.ykis.ykispam"
        minSdk = 22
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.constraintlayout)

    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
//    implementation("androidx.compose.runtime:runtime-livedata")
//    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
//    implementation("androidx.compose.ui:ui:1.5.0")

//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.0")


//    firebase
    implementation(platform(libs.firebase.bom))
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-perf-ktx")
    implementation("com.google.firebase:firebase-config-ktx")
    implementation(libs.play.services.auth)

//    compose
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
//    implementation(libs.ui.android)

    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.material)

    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.foundation:foundation-layout")
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")
    implementation(libs.androidx.runtime.livedata)


//    hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout.compose)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.android)
// Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.legacy.support.v4)

    kapt(libs.androidx.room.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.scalars)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.gson)

    //okHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)


    //coil
    implementation(libs.coil.compose)
    //Xpay
//    implementation(libs.pustovitandriy.xpay.android.sdk)
//    implementation("com.github.pustovitandriy:XPAY_Android_SDK:1.0.0")


//    implementation(libs.play.services)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}
kapt {
    correctErrorTypes = true
    useBuildCache = false
    showProcessorStats = true


}