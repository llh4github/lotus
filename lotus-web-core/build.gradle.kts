plugins {
    `java-library`
    id("kotlin-conventions")
    id("maven-center-publish")
    alias(libs.plugins.ksp)
}
dependencies {
    implementation(project(":lotus-commons"))
    implementation(platform(libs.springboot.dependencies))


    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-starter-jdbc")
    ksp(libs.jimmer.ksp)
    compileOnly(libs.jimmer.core)
    compileOnly(libs.jimmer.sql.kotlin)

    compileOnly(libs.swagger.models)
    compileOnly(libs.swagger.annotations)
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}
