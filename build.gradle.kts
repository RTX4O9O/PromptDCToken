plugins {
    id("java")
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}
application {
    mainClass.set("me.rtx4090.Main")
}


group = "me.rtx4090"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.seleniumhq.selenium:selenium-java:4.21.0")

}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.example.MainClass"
    }
    from({
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    })
}

tasks {
    shadowJar {
        archiveBaseName.set("TokenLogin")
        archiveVersion.set("1.0-SNAPSHOT")
        archiveClassifier.set("")
    }
}