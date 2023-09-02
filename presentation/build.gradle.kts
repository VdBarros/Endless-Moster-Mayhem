plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinKapt)
}

android {
    namespace = "com.vinibarros.mvvmcomposetemplate.presentation"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vinibarros.mvvmcomposetemplate.presentation"
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
        dataBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // layouts
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.material)
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // ViewModel and LiveData
    implementation(libs.androidx.lifecycle.extensions)

    // Dagger 2
    api(libs.dagger)
    kapt(libs.dagger.compiler)

    // dagger android
    api(libs.dagger.android)
    api(libs.dagger.android.support)
    kapt(libs.dagger.android.processor)

    //rx
    implementation(libs.rxkotlin)
    implementation(libs.rxandroid)
}