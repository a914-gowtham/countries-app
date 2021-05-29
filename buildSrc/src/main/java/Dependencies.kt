import org.gradle.api.artifacts.dsl.DependencyHandler

object Versions {

    /* kotlin */
    const val kotlin = "1.5.10"
    const val kotlinPlugin = "1.5.10"

    /* plugins */
    const val androidPlugin = "4.2.1"
    const val benManes = "0.39.0"

    /* libraries */
    const val appCompat = "1.3.0"
    const val coreKtx = "1.5.0"
    const val constraintLayout = "2.0.4"
    const val material = "1.3.0"

    /* test libraries */
    const val jUnit = "4.13.2"
    const val extJUnit = "1.1.2"
    const val espressoCore = "3.2.0"

}

object Dependencies {

    /* kotlin */
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinPlugin}"
    const val coil = "io.coil-kt:coil:1.2.1"
    const val ktStdLib= "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val ktSerializer = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1"
    const val ktReflect = "org.jetbrains.kotlin:kotlin-reflect:1.4.21"

    /* plugin */
    const val androidPlugin = "com.android.tools.build:gradle:${Versions.androidPlugin}"
    const val benManes = "com.github.ben-manes.versions"

    /* libraries */
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val mutliDex = "androidx.multidex:multidex:2.0.1"
    const val actKtx = "androidx.activity:activity-ktx:1.2.3"
    const val lottie = "com.airbnb.android:lottie:3.7.0"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:1.5.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:2.6.2"
    const val logging = "com.squareup.okhttp3:logging-interceptor:4.5.0"

    /* jetpack libraries */
    const val hilt = "com.google.dagger:hilt-android:2.36"
    const val recyclerView = "androidx.recyclerview:recyclerview:1.2.0"
    const val lifeCycleExt = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:2.3.5" //Android Navigation Architecture
    const val navigationUi =
        "androidx.navigation:navigation-ui-ktx:2.3.5"  //Android Navigation Architecture
    const val roomRunTime = "androidx.room:room-runtime:2.4.0-alpha02" //Room
    const val roomExt = "androidx.room:room-ktx:2.4.0-alpha02"  //Room

    /* test libraries */
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val extJUnit = "androidx.test.ext:junit:${Versions.extJUnit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"

    /* kapt */
    const val dataBinding = "com.android.databinding:compiler:3.1.4" //Databinding compiler
    const val hiltKapt = "com.google.dagger:hilt-android-compiler:2.36"  //Hilt
    const val roomKapt = "androidx.room:room-compiler:2.4.0-alpha02" //Room

    val appLibraries = arrayListOf<String>().apply {
        add(ktStdLib)
        add(appCompat)
        add(coreKtx)
        add(material)
        add(constraintLayout)
        add(coil)
        add(ktSerializer)
        add(mutliDex)
        add(actKtx)
        add(lottie)
        add(moshiKotlin)
        add(retrofit)
        add(moshiConverter)
        add(logging)
        add(ktReflect)

        add(hilt)
        add(recyclerView)
        add(lifeCycleExt)
        add(navigationFragment)
        add(navigationUi)
        add(roomRunTime)
        add(roomExt)
    }

    val kaptLibraries = arrayListOf<String>().apply {
        add(dataBinding)
        add(hiltKapt)
        add(roomKapt)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(jUnit)
    }
}


//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapts(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementations(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementations(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementations(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}