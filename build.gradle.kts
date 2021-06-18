import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import net.kyori.indra.IndraPlugin

plugins {
    id("net.kyori.indra") version "1.3.1"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "dev.kscott.bluelobby"
version = "1.0.0"

subprojects {
    apply {
        plugin<ShadowPlugin>()
        plugin<IndraPlugin>()
    }

    repositories {
        mavenLocal()
        mavenCentral()

        // Paper repo
        maven("https://papermc.io/repo/repository/maven-public/")

        // Corn
        maven("https://repo.broccol.ai")

        // PLaceholderAPI
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

        // TAB
        maven("https://repo.codemc.io/repository/maven-public/")
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

