Examples of using Datastore on Jetpack Compose (code mostly from codelabs but rewritten to Compose)

## 1. Preferences DataStore

#### libs.versions.toml:
[versions] 

    datastorePreferences = "1.1.2"

[libraries]

    androidx-datastore-preferences = { module = "androidx.datastore:datastore-preferences", version.ref = "datastorePreferences" }

#### build.gradle.kts:
    implementation(libs.androidx.datastore.preferences)

## 2. Proto DataStore using Serialization (without protobuf files)

#### libs.versions.toml:
[versions]

    serializationJson = "1.7.3"

[libraries]

    kotlinx-serialization-json = { module = "org.jetbrains.kotlinx:kotlinx-serialization-json", version.ref = "serializationJson" }

[plugins]

    kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }

#### build.greadle.kts(project):
    alias(libs.plugins.kotlin.serialization) apply false

#### build.gradle.kts(module):

plugins {

    alias(libs.plugins.kotlin.serialization)
}

dependencies {


    // Proto DataStore
    implementation(libs.androidx.datastore)

    // Serialization Json
    implementation(libs.kotlinx.serialization.json)

}
## 3. Proto DataStore using Protobuf

#### libs.versions.toml:
[versions]
    
    datastore = "1.1.2"
    protobufLite = "4.29.3"
    protobufPlugin = "0.9.4"

[libraries]
  
    androidx-datastore = { module = "androidx.datastore:datastore", version.ref = "datastore" }
    protobuf-javalite = { module = "com.google.protobuf:protobuf-javalite", version.ref = "protobufLite" }
    protobuf-kotlin-lite = { module = "com.google.protobuf:protobuf-kotlin-lite", version.ref = "protobufLite" }

[plugins]

    protobuf = { id = "com.google.protobuf", version.ref = "protobufPlugin" }

#### build.greadle.kts(project):

    alias(libs.plugins.protobuf) apply false

#### build.gradle.kts(module):

    import com.google.protobuf.gradle.*

plugins {
    
    alias(libs.plugins.protobuf)
}

android {

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
    
    // Proto DataStore
    implementation(libs.androidx.datastore)
    // Protocol Buffers (Java, Kotlin lite)
    implementation(libs.protobuf.javalite)
    implementation(libs.protobuf.kotlin.lite)
}

