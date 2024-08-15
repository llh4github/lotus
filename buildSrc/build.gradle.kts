plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.gradle)
    implementation(libs.springboot.gradle)
    implementation(libs.kotlin.allopen)
    implementation(libs.kotlin.noarg)
    implementation(libs.graalvm.native)
    implementation(libs.oshai.kotlinLogging)
    implementation(libs.slf4j.api)
}
