plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")

}

android {
    namespace = "ir.ayantech.pishkhansdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        debug {
            isJniDebuggable = false
        }
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //-----------------------------------Ayan----------------------
    implementation("com.github.shadowalker77:whygoogle:0.8.2")
    implementation("com.github.shadowalker77:wp7progressbar:1.1.0")
    implementation("com.github.ayantech:versioncontrol:0.6.2")
    //-------------(""------------------Networking--------------------------------
    implementation("com.github.shadowalker77:networking:1.6.1")
    implementation("com.github.shadowalker77:generator:0.3.0")
    kapt("com.github.shadowalker77:generator:0.3.0")
}