import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.4" apply false
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"

    kotlin("kapt") version "1.6.21"
}



java.sourceCompatibility = JavaVersion.VERSION_17

allprojects {
    group = "com.example"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-kapt")
    dependencies {
        // JWT 인증
        implementation("com.auth0:java-jwt:3.19.2")

        // Kotlin 로깅
        implementation("io.github.microutils:kotlin-logging:1.12.5")

        // Kotlin
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//        implementation("org.springframework.security.oauth.boot:spring-security-oauth2-autoconfigure:2.1.7.RELEASE")

        // H2DB
        runtimeOnly("com.h2database:h2")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    // 이 설정이 있어야 멀티 모듈에서도 제대로 의존성 주입 가능
    dependencyManagement {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }


    tasks.withType<Test> {
        useJUnitPlatform()
    }
}