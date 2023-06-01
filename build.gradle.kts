import org.jetbrains.gradle.ext.packagePrefix
import org.jetbrains.gradle.ext.settings

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.21"
    id("org.jetbrains.intellij") version "1.13.3"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
}

group = "lb.datagrip.diagnostic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.1.1")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java")
    java.srcDir("src/main/kotlin")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java")
    java.srcDir("src/test/kotlin")
}

idea.module.settings {
    packagePrefix["src/main/kotlin"] = "lb.datagrip.diagnostic"
    packagePrefix["src/test/kotlin"] = "lb.datagrip.diagnostic"
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
	sourceCompatibility = "17"
	targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
	kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
	sinceBuild.set("231")
	untilBuild.set("233.*")
    }

    signPlugin {
	certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
	privateKey.set(System.getenv("PRIVATE_KEY"))
	password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
	token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
