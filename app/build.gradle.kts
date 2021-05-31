plugins {
    id("com.android.application")
    kotlin("android")
    id(Dependencies.kotlinKapt)
    id(Dependencies.daggerHilt)
    id(Dependencies.safeArgs)
    id(Dependencies.parcelize)
    id(Dependencies.serialization)
}

android {
    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig {
        applicationId = "com.gowtham.template"
        minSdkVersion(AppConfig.minSdk)
        targetSdkVersion(AppConfig.targetSdk)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        multiDexEnabled = true
        testInstrumentationRunner = AppConfig.androidTestInstrumentation
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

    viewBinding {
        android.buildFeatures.dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation("com.google.android.gms:play-services-location:18.0.0")
    implementation("com.vmadalin:easypermissions-ktx:1.0.0")
    implementation("org.greenrobot:eventbus:3.2.0")
    implementations(Dependencies.appLibraries)
    kapts(Dependencies.kaptLibraries)
    testImplementations(Dependencies.testLibraries)
    androidTestImplementations(Dependencies.androidTestLibraries)
}