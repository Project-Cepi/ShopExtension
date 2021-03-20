import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    kotlin("jvm") version "1.4.30"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("org.jetbrains.dokka") version "1.4.20"
    kotlin("plugin.serialization") version "1.4.21"
    `maven-publish`
    maven

    // Apply the application plugin to add support for building a jar
    java
}

repositories {
    // Use jcenter for resolving dependencies.
    jcenter()
    mavenCentral()
    // Use mavenCentral
    maven(url = "https://repo1.maven.org/maven2/")
    maven(url = "https://repo.spongepowered.org/maven")
    maven(url = "https://libraries.minecraft.net")
    maven(url = "https://jitpack.io")
    maven(url = "https://jcenter.bintray.com/")
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    // Align versions of all Kotlin components
    compileOnly(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    compileOnly(kotlin("stdlib"))

    // Use the Kotlin reflect library.
    compileOnly(kotlin("reflect"))

    // Compile Minestom into project
    compileOnly("com.github.Minestom:Minestom:949794cbf5")

    // Get KStom
    compileOnly("com.github.Project-Cepi:KStom:cbcf67f09c")

    // Items
    compileOnly("com.github.Project-Cepi:ItemExtension:0c931a7440")

    // import kotlinx serialization
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")

    // Canvas
    compileOnly("com.github.mworzala:canvas:407c072b23")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

configurations {
    testImplementation {
        extendsFrom(configurations.compileOnly.get())
    }
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("shop")
        mergeServiceFiles()
        minimize()

    }

    test { useJUnitPlatform() }

    build { dependsOn(shadowJar) }

}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "11" }
val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks

compileKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-Xinline-classes")
}