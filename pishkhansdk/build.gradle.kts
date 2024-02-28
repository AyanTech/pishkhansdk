plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-kapt")
    id("kotlin-android")
    id("maven-publish")
}

android {
    namespace = "ir.ayantech.pishkhansdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testOptions.targetSdk = 34
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
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

    publishing {
        singleVariant("release")
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

afterEvaluate {
    publishing {
        publications {
            // Creates a Maven publication called "release".
            register("release", MavenPublication::class) {
                // Applies the component for the release build variant.
                // NOTE : Delete this line code if you publish Native Java / Kotlin Library
                from(components["release"])
                // Library Package Name (Example : "com.frogobox.androidfirstlib")
                // NOTE : Different GroupId For Each Library / Module, So That Each Library Is Not Overwritten
                groupId = "com.github.ayantech"

                // Library Name / Module Name (Example : "androidfirstlib")
                // NOTE : Different ArtifactId For Each Library / Module, So That Each Library Is Not Overwritten
                artifactId = "pishkhan-sdk"

                // Version Library Name (Example : "1.0.0")
                version = "0.0.8"
            }
        }
    }
}
