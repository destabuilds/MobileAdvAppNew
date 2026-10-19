// Blok plugins: Mendaftarkan plugin utama Android Application dan Kotlin Android untuk proses kompilasi
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

// Blok android: Pengaturan utama Android SDK, identitas aplikasi, serta opsi kompilasi
android {
    namespace = "com.example.mobileadvappnew"
    compileSdk = 36

    // Blok defaultConfig: Menentukan identitas unik aplikasi (applicationId), SDK minimal, SDK target, serta versi rilis
    defaultConfig {
        applicationId = "com.example.mobileadvappnew"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    // Blok buildFeatures: Mengaktifkan ViewBinding untuk akses komponen UI secara type-safe tanpa findViewById
    buildFeatures {
        viewBinding = true
    }
}

// Blok dependencies: Mendaftarkan pustaka/library eksternal yang dibutuhkan untuk runtime maupun testing
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.appcompat)

    // Testing Libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}