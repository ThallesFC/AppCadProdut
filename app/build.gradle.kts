plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.appcadprodut"
    compileSdk = 34

    defaultConfig {
        applicationId= "com.example.appcadprodut"
        minSdk = 19
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
    compileOptions {sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    configurations.all {
        resolutionStrategy {
            force("androidx.annotation:annotation:1.9.1")
        }
    }
    implementation(libs.appcompat) {
        exclude(group = "androidx.annotation", module = "annotation")
    }
    implementation(libs.material) {
        exclude(group = "androidx.annotation", module = "annotation")
    }
    implementation(libs.activity) {
        exclude(group = "androidx.annotation", module = "annotation")
    }
    implementation(libs.constraintlayout) {
        exclude(group = "androidx.annotation", module = "annotation")
    }
    implementation(libs.navigation.fragment) {
        exclude(group = "androidx.annotation", module = "annotation")
    }
    implementation(libs.navigation.ui) {
        exclude(group = "androidx.annotation", module = "annotation")
    }
    implementation(libs.annotation.jvm) // Mantém a dependência libs.annotation.jvm
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}