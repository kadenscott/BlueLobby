import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import net.kyori.indra.IndraPlugin
import net.kyori.indra.IndraPublishingPlugin

plugins {
    id("net.kyori.indra") version "1.3.1"
    id("net.kyori.indra.publishing") version "1.3.1"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "dev.kscott.bluelobby"
version = "1.0.0"

apply {
    plugin<ShadowPlugin>()
    plugin<IndraPlugin>()
    plugin<IndraPublishingPlugin>()
}

repositories {
    mavenCentral()

    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.broccol.ai")
}

tasks {

    indra {
        gpl3OnlyLicense()

        javaVersions {
            target.set(16)
        }
    }

    processResources {
        expand("version" to rootProject.version)
    }

}

dependencies {
    compileOnly(libs.paper)

    api(libs.minimessage)
    api(libs.cloud.paper)
    api(libs.bundles.guice)
    api(libs.bundles.corn)
}

tasks {

    build {
        dependsOn(named("shadowJar"))
    }

    shadowJar {
        fun relocates(vararg dependencies: String) {
            dependencies.forEach {
                val split = it.split(".")
                val name = split.last()
                relocate(it, "${rootProject.group}.dependencies.$name")
            }
        }

        dependencies {
            exclude(dependency("com.google.guava:"))
            exclude(dependency("com.google.errorprone:"))
            exclude(dependency("org.checkerframework:"))
        }

        relocates(
                "cloud.commandframework",
                "com.google.inject",
                "broccolai.corn"
        )

        archiveClassifier.set(null as String?)
        archiveFileName.set(project.name + ".jar")
        destinationDirectory.set(rootProject.tasks.shadowJar.get().destinationDirectory.get())
    }
}