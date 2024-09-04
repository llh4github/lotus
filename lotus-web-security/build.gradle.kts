plugins {
    `java-library`
    id("kotlin-conventions")
    id("maven-center-publish")
    alias(libs.plugins.ksp)
}
dependencies {
    implementation(project(":lotus-commons"))
    implementation(project(":lotus-web-core"))
    implementation(platform(libs.springboot.dependencies))

    compileOnly("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-security")
    compileOnly("org.springframework.boot:spring-boot-starter-data-redis")
    compileOnly("org.springframework.boot:spring-boot-starter-data-jdbc")
    testImplementation("org.springframework.security:spring-security-test")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation(libs.swagger.annotations)
    implementation(libs.jjwt.api)
    implementation(libs.jjwt.impl)
    implementation(libs.jjwt.jackson)
}