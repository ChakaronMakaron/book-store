plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.mockito:mockito-core:5.4.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.4.0")
    implementation("com.google.code.gson:gson:2.10.1")
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
    reports.html.required.set(true)
    testLogging {
        showStandardStreams = true
    }
}
