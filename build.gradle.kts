import java.util.concurrent.CyclicBarrier

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.41"
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    repositories {
        jcenter()
    }

    dependencies {
        testImplementation(platform("org.jetbrains.kotlin:kotlin-bom"))
        testImplementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        testImplementation("org.jetbrains.kotlin:kotlin-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    }
}

val barrier = CyclicBarrier(2)
rootProject.ext["barrier"] = barrier
tasks.test {
    doFirst {
        mkdir(buildDir.resolve("ids"))
        barrier.await()
    }
    maxParallelForks = 6
}

val checkTestIds by tasks.registering {
    dependsOn(tasks.test, ":submodule:test")
    doLast {
        val rootModuloIds = buildDir.resolve("ids").listFiles().map { it.name.toInt().rem(6) }
        val submoduleModuloIds = project(":submodule").buildDir.resolve("ids").listFiles().map { it.name.toInt().rem(6) }
        if (rootModuloIds.toList() != rootModuloIds.toSet().toList() || submoduleModuloIds.toList() != submoduleModuloIds.toSet().toList()) {
            throw GradleException("TestWorker ids mod maxParallelForks are not unique. \n Root project modulos: $rootModuloIds \n Submodule modulos: $submoduleModuloIds")
        }
    }
}