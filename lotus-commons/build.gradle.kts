plugins {
    `java-library`
    id("kotlin-conventions")
    id("maven-center-publish")
}

dependencies {
    implementation(libs.yitter.idgenerator)
    implementation(libs.swagger.annotations)

    compileOnly(libs.slf4j.api)
    compileOnly(libs.logback.core)
    compileOnly(libs.logback.classic)
}
