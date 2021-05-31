import org.gradle.api.artifacts.dsl.DependencyHandler

object Versions {

    /* kotlin */
    const val kotlin = "1.5.10"
    const val ktSerilization = "1.2.1"
    const val ktReflect = "1.4.21"

    /* plugins */
    const val androidPlugin = "4.2.1"
    const val kotlinPlugin = "1.5.10"
    const val benManes = "0.39.0"
    const val detekt = "1.16.0"
    const val safeArgsPlugIn = "2.3.5"
    const val daggerHiltPlugin = "2.36"
    const val ktSerializerPlugin = "1.5.10"

    /* libraries */
    const val appCompat = "1.3.0"
    const val coreKtx = "1.5.0"
    const val legacySupport = "1.0.0"
    const val retrofitConverter = "2.3.0"
    const val coilSvg = "1.2.1"
    const val flexBox = "3.0.0"
    const val constraintLayout = "2.0.4"
    const val material = "1.3.0"
    const val multiDex = "2.0.1"
    const val actKtx = "1.2.3"
    const val lottie = "3.7.0"
    const val retrofit = "2.9.0"
    const val logging = "4.5.0"
    const val hilt = "2.36"
    const val recyclerView = "1.2.0"
    const val lifeCycleExt = "2.2.0"
    const val navigation = "2.3.5"
    const val room = "2.4.0-alpha02"
    const val location = "18.0.0"
    const val eventBus = "3.2.0"
    const val easyPermission = "1.0.0"

    /* test libraries */
    const val jUnit = "4.13.2"
    const val extJUnit = "1.1.2"
    const val espressoCore = "3.2.0"

}

object Dependencies {

    /* kotlin */
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private const val coil = "io.coil-kt:coil:${Versions.coilSvg}"
    private const val coilSvg = "io.coil-kt:coil-svg:${Versions.coilSvg}"
    private const val ktStdLib= "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    private const val ktSerializer = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.ktSerilization}"
    private const val ktReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.ktReflect}"



    /* libraries */
    private const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    private const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private const val material = "com.google.android.material:material:${Versions.material}"
    private const val multiDex = "androidx.multidex:multidex:${Versions.multiDex}"
    private const val actKtx = "androidx.activity:activity-ktx:${Versions.actKtx}"
    private const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private const val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.logging}"
    private const val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.legacySupport}"
    private const val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofitConverter}"
    private const val flexBox = "com.google.android.flexbox:flexbox:${Versions.flexBox}"

    private const val location = "com.google.android.gms:play-services-location:${Versions.location}"
    private const val eventBus = "org.greenrobot:eventbus:${Versions.eventBus}"
    private const val easyPermission = "com.vmadalin:easypermissions-ktx:${Versions.easyPermission}"

    /* jetpack libraries */
    private const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    private const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    private const val lifeCycleExt = "androidx.lifecycle:lifecycle-extensions:${Versions.lifeCycleExt}"
    private const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}" //Android Navigation Architecture
    private const val navigationUi =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"  //Android Navigation Architecture
    private const val roomRunTime = "androidx.room:room-runtime:${Versions.room}" //Room
    private const val roomExt = "androidx.room:room-ktx:${Versions.room}"  //Room

    /* test libraries */
    private const val jUnit = "junit:junit:${Versions.jUnit}"
    private const val extJUnit = "androidx.test.ext:junit:${Versions.extJUnit}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"

    /* kapt */
    private const val hiltKapt = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"  //Hilt
    private const val roomKapt = "androidx.room:room-compiler:${Versions.room}" //Room

    val appLibraries = arrayListOf<String>().apply {
        add(ktStdLib)
        add(appCompat)
        add(coreKtx)
        add(material)
        add(constraintLayout)
        add(coil)
        add(coilSvg)
        add(ktSerializer)
        add(multiDex)
        add(actKtx)
        add(lottie)
        add(retrofit)
        add(logging)

        add(legacySupport)
        add(retrofitConverter)
        add(flexBox)
        add(ktReflect)

        add(location)
        add(eventBus)
        add(easyPermission)

        add(hilt)
        add(recyclerView)
        add(lifeCycleExt)
        add(navigationFragment)
        add(navigationUi)
        add(roomRunTime)
        add(roomExt)
    }

    val kaptLibraries = arrayListOf<String>().apply {
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