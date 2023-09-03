plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLib) apply false
    alias(libs.plugins.kotlinKSP) apply false
    alias(libs.plugins.kotlinKapt) apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
}