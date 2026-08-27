plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    `maven-publish`
}

android {
    namespace = "ir.ayantech.pishkhansdk"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
    buildFeatures {
        viewBinding = true
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(libs.bundles.androidx)

    //-----------------------------------Ayan----------------------
    implementation(libs.why.google)
    implementation(libs.wp7.progressbar)
    //------------- ''------------------Networking--------------------------------
    implementation(libs.networking)
//    implementation(libs.networkingV2)
    compileOnly(libs.generator)
    kapt(libs.generator)

    implementation(libs.persian.date)
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)

    implementation(libs.glide)
    kapt(libs.glide.compiler)

    //SMS Receiver
    implementation(libs.play.services.auth.api.phone)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.AyanTech"
            artifactId = "pishkhansdk"
            version = "1.2.12-alpha01"

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("Ayan Pishkhab SDK")
                description.set("This SDK provides developers with a solution for integrating and interacting with various service APIs and showing the result")
                url.set("https://github.com/AyanTech/pishkhansdk")
            }
        }
    }
}
