plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.google.ksp)
    kotlin("kapt")
}

android {
    namespace = "dev.balinapp.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", project.properties["BASE_URL"].toString())
            buildConfigField("String", "DATASTORE_FILE", project.properties["DATASTORE_FILE"].toString())
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.squareup.converter.kotlinx.serialization)

    api(libs.retrofit)
    implementation(libs.retrofit.adapters.result)

    implementation(libs.okhttp)

    api(libs.dagger)
    kapt(libs.dagger.compiler)

    api(libs.androidx.datastore.preferences)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    api(libs.androidx.paging.runtime)
    api(libs.androidx.room.paging)
}