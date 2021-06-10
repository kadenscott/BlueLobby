import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import net.kyori.indra.IndraPlugin

plugins {
    id("net.kyori.indra") version "1.3.1"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "dev.kscott.bonk"
version = "1.0.0"

subprojects {
    apply {
        plugin<ShadowPlugin>()
        plugin<IndraPlugin>()
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
}

