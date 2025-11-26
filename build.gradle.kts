import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        maven { url = uri("https://repo1.maven.org/maven2") }
        maven { url = uri("https://repo.spring.io/release") }
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:3.3.4")
        classpath("io.spring.gradle:dependency-management-plugin:1.1.6")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.25")
    }
}

apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")
apply(plugin = "org.jetbrains.kotlin.jvm")
apply(plugin = "org.jetbrains.kotlin.plugin.spring")

group = "com.example"
version = "0.0.1-SNAPSHOT"

description = "Simple WebSocket chat built with Spring Boot and Kotlin"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    maven { url = uri("https://repo1.maven.org/maven2") }
    maven { url = uri("https://repo.spring.io/release") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
