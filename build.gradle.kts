import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

group = "ru.perm.v"
// change version on publishing
version = "0.24.0706.1"
description = "Vacancy backend"

java.sourceCompatibility = JavaVersion.VERSION_11
var querydslVersion = "5.0.0"
var springFoxVersion = "3.0.0"
var springBootVersion = "2.5.6"
var springDependencyManagement = "1.0.3.RELEASE"
var mockitoKotlinVersion = "4.0.0"

configurations.create("querydsl")

buildscript {
    var kotlinVersion: String? by extra; kotlinVersion = "1.3.3"

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }

}

repositories {
    mavenCentral()
    mavenLocal()
    maven {

        url = uri("http://v.perm.ru:8082/repository/ru.perm.v") //OK
        isAllowInsecureProtocol = true
        credentials {
//            username = "admin"
//            password = "pass"

// export NEXUS_CI_USER=admin
// echo $NEXUS_CI_USER
            username = System.getenv("NEXUS_CRED_USR") ?: extra.properties["nexus-ci-username"] as String?
// export NEXUS_CI_PASS=pass
// echo $NEXUS_CI_PASS
            password = System.getenv("NEXUS_CRED_PASS") ?: extra.properties["nexus-ci-password"] as String?
        }
    }
}

plugins {
    val kotlinVersion = "1.8.21"
    id("org.springframework.boot") version "2.5.6"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("maven-publish")
    id("io.qameta.allure") version "2.8.1"
    id("jacoco")
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("kapt") version "1.7.0"
    kotlin("plugin.serialization") version "2.0.0" //JSON serialization, deserialization
    java
    idea
    application
//    kotlin("plugin.allopen")
//    kotlin("plugin.noarg")
//	id 'io.qameta.allure' version '2.11.2' // version 2.10.0 WORK! NO WARNINGS for generate allure report
    id("org.flywaydb.flyway") version "9.21.1"
}

//Note that this is a BootJar plugin used in org.springframework.boot
apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")
apply(plugin = "kotlin-kapt")


repositories {
    mavenCentral()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
    gradlePluginPortal()
}

java.sourceSets["main"].java {
    srcDir("build/classes/java/main")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("org.springframework.boot:spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-jetty") // jetty uses less memory

// recomendation from https://habr.com/ru/companies/domclick/articles/505860/
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springframework.boot:spring-boot-starter-cache")
// https://mvnrepository.com/artifact/io.springfox/springfox-boot-starter
    implementation("io.springfox:springfox-boot-starter:$springFoxVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
//for postgresql
    implementation("org.postgresql:postgresql:42.7.2")
// form h2 base    implementation("com.h2database:h2:2.2.220")
// validator
    implementation("org.hibernate.validator:hibernate-validator")

// https://mvnrepository.com/artifact/com.querydsl/querydsl-apt
    implementation("com.querydsl:querydsl-core:$querydslVersion")
    implementation("com.querydsl:querydsl-jpa:$querydslVersion")

// spring-boot-starter-actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")
// prometheus - metrics
    implementation("io.micrometer:micrometer-registry-prometheus")

    api("com.querydsl:querydsl-apt:$querydslVersion:jpa")
    kapt("jakarta.annotation:jakarta.annotation-api")

    testImplementation ("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.mockito", "mockito-core")
    }
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")

    compileOnly("org.springframework.boot:spring-boot-starter-actuator")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

kapt {
    javacOptions {
        option("querydsl.entityAccessors", true)
    }
    arguments {
        arg("plugin", "com.querydsl.apt.jpa.JPAAnnotationProcessor")
    }
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    // Show test log
    testLogging {
//        events("standardOut", "started", "passed", "skipped", "failed")
        events("passed", "skipped", "failed")
    }
//    if (project.hasProperty('excludeTests')) {
//        exclude project.property('excludeTests')
//    }
    filter {
        exclude("*IntegrationTest*")
    }

}

//// remove suffix 'plain' in sonar repository
tasks.jar {
    enabled = true
    // Remove `plain` postfix from jar file name
    archiveClassifier.set("")
}

// Configure Spring Boot plugin task for running the application.
val bootJar by tasks.getting(BootJar::class) {
    enabled = true
}

val sourcesJar by tasks.registering(Jar::class)  {
   archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    repositories {
        maven {
            url = uri("http://v.perm.ru:8082/repository/ru.perm.v/")
            isAllowInsecureProtocol = true
            //  publish в nexus "./gradlew publish" из ноута и Jenkins проходит
            // export NEXUS_CRED_USR=admin
            // echo $NEXUS_CRED_USR
            credentials {
                username = System.getenv("NEXUS_CRED_USR")
                password = System.getenv("NEXUS_CRED_PSW")
            }
        }
    }
    publications {
        create<MavenPublication>("maven"){
            artifact(tasks["bootJar"])
            artifact(tasks["sourcesJar"])
        }
    }
}

// use ./gradlew bootRun
springBoot {
    mainClass.set("ru.perm.v.vacancy.VacancyKotlinApplication")
}


// DEMO TASKS
// use ./gradlew myTask1
tasks.register("myTask1") {
    println("echo from myTask1.\nFor run use: ./gradlew myTask1")
}

// use ./gradlew myTask2
tasks.register("myTask2") {
    println("echo from myTask2.\nFor run use: ./gradlew myTask2")
}

// use ./gradlew helloUserCmd
tasks.register("helloUserCmd") {
    println("echo from helloUserCmd.\nFor run use: ./gradlew helloUserCmd")
    val user: String? = System.getenv("USER")
    project.exec {
        commandLine("echo", "Hello,", "$user!")
    }
}

tasks.register("exampleTask") {
    println("exampleTask")
    enabled = false
}

tasks.withType<org.flywaydb.gradle.task.FlywayMigrateTask> {
//    url = System.getenv ("PG_VACANCY")
//    user = System.getenv ("PG_LOGIN")
//    password = System.getenv ("PG_PASSWORD")
    url = "jdbc:postgresql://192.168.1.20:5432/vacancy"
    user = "vasi"
    password = "pass"
    baselineOnMigrate = true
}
//tasks.named<BootJar>("bootJar") {
//    archiveClassifier.set("")
//}

//tasks.named<Jar>("jar") {
//    archiveClassifier.set("")
//}
