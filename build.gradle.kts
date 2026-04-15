import org.gradle.internal.os.OperatingSystem

plugins {
    id("java")
    id("application")
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
    implementation("org.joml:joml:1.10.5")

    testImplementation(platform("org.junit:junit-bom:6.0.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

// Set the main class for the application plugin
application {
    mainClass.set("$group.Main")
}