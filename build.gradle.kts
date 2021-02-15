import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val springBootVersion = "2.4.2"
    val kotlinVersion = "1.4.21"
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    idea
}

idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}

group = "com.tamastudy"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}
allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

dependencies {
    // Spring boot
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-actuator")
//    implementation("org.springframework.boot:spring-boot-hateoas")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Database
    runtimeOnly("com.h2database:h2")
    runtimeOnly("mysql:mysql-connector-java")

    // Querydsl
    compileOnly("com.querydsl:querydsl-jpa")
    kapt("com.querydsl:querydsl-apt::jpa")

    // Jwt
    implementation("com.auth0:java-jwt:3.12.0")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")

    // Map-struct
    implementation("org.mapstruct:mapstruct:1.4.1.Final")
    kapt("org.mapstruct:mapstruct-processor:1.4.1.Final")
    kaptTest("org.mapstruct:mapstruct-processor:1.4.1.Final")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
