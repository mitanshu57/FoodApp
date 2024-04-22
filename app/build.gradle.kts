plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.foodapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.foodapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures{
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))

    implementation("com.google.firebase:firebase-auth")

    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)

   // implementation("com.google.android.libraries.ads.mobile.sdk:ads-mobile-sdk:0.3.0-alpha01")


    implementation("com.google.firebase:firebase-firestore")
    implementation ("com.google.android.gms:play-services-ads:23.0.0")


    implementation(libs.constraintlayout)
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation(libs.firebase.database)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.inappmessaging.display)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
