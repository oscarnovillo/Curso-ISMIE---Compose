import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    alias(libs.plugins.navigation.safeargs.kotlin)
}


android {
    namespace = "com.example.primerxmlmvvm"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.primerxmlmvvm"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        //load the values from .properties file
//        val keystoreFile = project.rootProject.file("apikey.properties")
//        val properties = Properties()
//        properties.load(keystoreFile.inputStream())
//
//        //return empty key in case something goes wrong
//        val apiKey = properties.getProperty("API_KEY") ?: ""
//        buildConfigField("String", "API_KEY", "\"$apiKey\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions += "env"
    productFlavors {
        create("development") {
            dimension = "env"
            buildConfigField("String", "API_URL", "\"https://jsonplaceholder.typicode.com\"")
        }
        create("production") {
            dimension = "env"
            buildConfigField("String", "API_URL", "\"https://jsonplaceholder.typicode.com\"")
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true

    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    // para el decortator al hacer swipe
    implementation(libs.recyclerview.swipedecorator)


    // Fragments
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.bundles.viewmodel)



    implementation(libs.timber)

    //retrofit

    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.converter.scalars)
    implementation (libs.logging.interceptor)

    implementation(libs.coil)


    // Hilt
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    //Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    // To use Kotlin Symbol Processing (KSP)
    ksp(libs.androidx.room.compiler)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

    // testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}