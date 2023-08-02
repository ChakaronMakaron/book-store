plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.andersen.EntryPoint"
    }
}

application {
    mainClass.set("com.andersen.EntryPoint")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    reports.html.required.set(false)
    testLogging {
        setShowStandardStreams(true)
    }
}
