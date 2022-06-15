plugins {
    // kotlin("multiplatform") version "1.7.0"
    kotlin("multiplatform") version "1.7.20-dev-2125"
}

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    jvm()

    js(IR) {
        nodejs()
        binaries.executable()
    }

    sourceSets {
        val nativeMain by getting
        val nativeTest by getting
    }
}


task("runJvm", JavaExec::class) {
    dependsOn("jvmJar")
    mainClass.set("MainKt")
    val compilation = kotlin.jvm().compilations["main"]
    classpath =
        compilation.output.allOutputs +
                compilation.runtimeDependencyFiles
}
