plugins {
    id("com.android.application")
    kotlin("android")
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.daggerHilt)
    id(BuildPlugins.safeArgs)
    id(BuildPlugins.parcelize)
    id(BuildPlugins.serialization)
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
    implementations(Dependencies.appLibraries)
    implementation("io.reactivex.rxjava3:rxjava:3.1.2")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation("io.reactivex.rxjava3:rxkotlin:3.0.1")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    kapts(Dependencies.kaptLibraries)
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.2")
    testImplementations(Dependencies.testLibraries)
    androidTestImplementations(Dependencies.androidTestLibraries)
    kaptAndroidTests(Dependencies.kaptTestLibraries)
}
repositories {
    mavenCentral()
}