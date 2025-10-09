plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.project.credmanager"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.project.credmanager"
        minSdk = 27
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
    ksp {
        arg("room:schemalocation", "$projectDir/schemas")
    }
}

dependencies {
    // Force all kotlin libs to match your plugin version (1.9.24)
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin") {
                useVersion("1.9.24")
            }
        }
    }

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // lottie
    implementation(libs.lottie)
    // room
    implementation(libs.androidx.room.runtime)
    androidTestImplementation(libs.androidx.core.testing)
    // ksp
    ksp("androidx.room:room-compiler:2.6.1")
    // viewModel
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // instantTaskExecutorRule
    testImplementation(libs.androidx.core.testing)
    // kotlinx-coroutines-test
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    // google truth for testing
    testImplementation(libs.truth)
    androidTestImplementation(libs.truth)
    // BCrypt hashing library
    implementation(libs.bcrypt)

}