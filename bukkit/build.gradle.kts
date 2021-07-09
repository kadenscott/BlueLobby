plugins {
    id("xyz.jpenilla.run-paper") version "1.0.2"
}

dependencies {
    compileOnly(libs.paper)

    api(libs.minimessage)
    api(libs.cloud.paper)
    api(libs.interfaces.core)
    api(libs.interfaces.paper)
    api(libs.bundles.guice)
    api(libs.bundles.corn)
    api(libs.adventurebungee)
    api(libs.ifgui)

    compileOnly(libs.citizens)
    compileOnly(libs.holodisplays)
    compileOnly(libs.placeholderapi)
    compileOnly(libs.luckperms)
    compileOnly(libs.worldguard)
    compileOnly(libs.tab)
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

    runServer {
        minecraftVersion("1.16.5")
    }
}