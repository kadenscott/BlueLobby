import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import net.kyori.indra.IndraPlugin

plugins {
    id("net.kyori.indra") version "2.0.5"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "dev.kscott.bluelobby"
version = "1.0.0"

subprojects {
    apply {
        plugin<ShadowPlugin>()
        plugin<IndraPlugin>()
    }

    repositories {
        mavenCentral()

        // Paper repo
        maven("https://papermc.io/repo/repository/maven-public/")

        // PLaceholderAPI
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

        // TAB
        maven("https://repo.codemc.io/repository/maven-public/")

        // Citizens
        maven("https://repo.citizensnpcs.co/")

        // Interfaces, sometimes
        mavenLocal()

        // Corn
        maven("https://repo.broccol.ai/snapshots")

        // WorldGuard
        maven("https://maven.enginehub.org/repo/")

    }

    tasks {

        indra {
            gpl3OnlyLicense()

            javaVersions {
                target(16)
            }
        }

        processResources {
            expand("version" to rootProject.version)
        }

    }
}

