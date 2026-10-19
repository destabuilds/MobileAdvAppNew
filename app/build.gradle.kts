// Blok plugins: Mendaftarkan plugin utama Android Application dan Kotlin Android untuk proses kompilasi
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
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

    implementation("androidx.navigation:navigation-fragment-ktx:2.8.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Testing Libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}