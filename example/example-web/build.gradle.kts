plugins {
    id("kotlin-conventions")
    id("spring-conventions")
    alias(libs.plugins.ksp)
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation(project(":lotus-web-core"))
    ksp(libs.jimmer.ksp)
    implementation(libs.jimmer.starter)
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    runtimeOnly("com.mysql:mysql-connector-j")
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}
