import org.gradle.internal.os.OperatingSystem

plugins {
    id("java")
    application
    id("com.gradleup.shadow") version "9.4.1"
}

val lwjglVersion = "3.4.1"
val lwjglNatives = when (OperatingSystem.current()) {
    OperatingSystem.LINUX   -> "natives-linux"
    OperatingSystem.MAC_OS  -> "natives-macos"
    else                    -> "natives-windows"
}

group = "net.paulem.argus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Core LWJGL
    implementation("org.lwjgl:lwjgl:$lwjglVersion")
    runtimeOnly("org.lwjgl:lwjgl:$lwjglVersion:$lwjglNatives")

    // GLFW (Gestion des fenêtres et entrées)
    implementation("org.lwjgl:lwjgl-glfw:$lwjglVersion")
    runtimeOnly("org.lwjgl:lwjgl-glfw:$lwjglVersion:$lwjglNatives")

    // OpenGL (Le moteur de rendu)
    implementation("org.lwjgl:lwjgl-opengl:$lwjglVersion")
    runtimeOnly("org.lwjgl:lwjgl-opengl:$lwjglVersion:$lwjglNatives")

    // JOML (Mathématiques 3D)
    implementation("org.joml:joml:1.10.8")

    // Lombok
    val lombokVersion = "1.18.44"
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")

    // Logger
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("ch.qos.logback:logback-classic:1.5.32")
}

// Set the main class for the application plugin
val entrypoint = "net.paulem.entrypoint.EntryPoint"

application {
    mainClass.set(entrypoint)
}

// Add to manifest
tasks.jar {
    manifest {
        attributes["Main-Class"] = entrypoint
    }
}