import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.googleAndroidLibrariesMapsplatformSecretsGradlePlugin)
}

android {
    namespace = "iestrassierra.jlcamunas.trasslado"
    compileSdk = 34

    defaultConfig {
        applicationId = "iestrassierra.jlcamunas.trasslado"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Cargar la API key de Google Maps desde local.properties
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(localPropertiesFile.inputStream())
        }
//        val googleMapsKey = localProperties.getProperty("google_maps_key") ?: ""
//        resValue("string", "google_maps_key", googleMapsKey)
        val mapboxAccessToken = localProperties.getProperty("mapbox_access_token") ?: ""
        resValue("string", "mapbox_access_token", mapboxAccessToken)
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true //Habilitamos la configuración personalizada de este archivo
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Dependencias de Google Maps
    implementation (libs.google.maps.services)
    implementation (libs.android.maps.utils)
    implementation(libs.okhttp)
    // Dependencias de Mapbox
    implementation(libs.mapbox.sdk.geojson)
    implementation(libs.mapbox.sdk.services)
    implementation(libs.mapbox.sdk.turf)
    implementation(libs.mapbox.sdk.core)
    implementation(libs.android)
    implementation(libs.core)
    implementation(libs.navigation.android)
}
