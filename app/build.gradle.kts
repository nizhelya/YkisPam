plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.hilt)
    alias(libs.plugins.perf)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ykis.mob"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ykis.ykispam"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        multiDexEnabled = true
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("long", "VERSION_CODE", "${defaultConfig.versionCode}")
            buildConfigField("String","VERSION_NAME","\"${defaultConfig.versionName}\"")
            signingConfig = signingConfigs.getByName("debug")
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
        buildConfig = true
//        dataBinding = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/DEPENDENCIES"
            excludes += "/META-INF/INDEX.LIST"
        }
    }

}

dependencies {

    //implementation(libs.androidx.material3.adaptive)
    implementation(libs.androidx.material3.adaptive.navigation.suite)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.firebase.functions)
//    implementation(libs.firebase.functions.ktx)
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.materialWindow)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.accompanist.adaptive)

    androidTestImplementation(composeBom)
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.core.ktx)
    implementation(libs.constraintlayout)

    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

//    firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.perf)
    implementation(libs.firebase.config)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.firebase.inappmessaging.display)
    //implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.play.services.auth)
    implementation(libs.kotlinx.coroutines.play.services)


//    hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.hilt.android)
    ksp(libs.androidx.hilt.compiler)
    ksp(libs.hilt.compiler)
// Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.legacy.support.v4)
    ksp(libs.androidx.room.compiler)

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

    implementation(libs.androidx.splashscreen)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    val cameraxVersion = "1.3.4"
    implementation ("androidx.camera:camera-core:${cameraxVersion}")
    implementation ("androidx.camera:camera-camera2:${cameraxVersion}")
    implementation ("androidx.camera:camera-view:${cameraxVersion}")
    implementation ("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation ("com.google.auth:google-auth-library-oauth2-http:1.7.0")
//    implementation ("com.google.auth:google-auth-library-oauth2-http:1.25.0")
//    implementation (libs.google.auth.library.oauth2.http)
//    implementation("com.google.firebase:firebase-storage:21.0.0")
//    implementation("com.google.firebase:firebase-storage-ktx:21.0.0")
}
