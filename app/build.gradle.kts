import com.google.protobuf.gradle.*


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.bugbender.preferencesdatastore"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.bugbender.preferencesdatastore"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    protobuf {
        protoc {
            artifact = "com.google.protobuf:protoc:4.29.3"
        }
        // Generates the java Protobuf-lite code for the Protobufs in this project. See
        // https://github.com/google/protobuf-gradle-plugin#customizing-protobuf-compilation
        // for more information.
        generateProtoTasks {
            // see https://github.com/google/protobuf-gradle-plugin/issues/518
            // see https://github.com/google/protobuf-gradle-plugin/issues/491
            // all() here because of android multi-variant
            all().forEach { task ->
                // this only works on version 3.8+ that has buildins for javalite / kotlin lite
                // with previous version the java build in is to be removed and a new plugin
                // need to be declared
                task.builtins {
                    id("java") { // id is imported above
                        option("lite")
                    }
                    id("kotlin") {
                        option("lite")
                    }
                }
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ViewModel compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // collectAsStateWithLifecycle
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Proto DataStore
    implementation(libs.androidx.datastore)
    // Protocol Buffers (Java, Kotlin lite)
    implementation(libs.protobuf.javalite)
    implementation(libs.protobuf.kotlin.lite)
}