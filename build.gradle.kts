plugins {
    id("java")
}

val projectVersion: String by project

group = "delta.cion.restcontroller"
version = projectVersion

repositories {
	mavenLocal()
    mavenCentral()
}

dependencies {
    compileOnly("delta.cion.cherry.api:cherry_api:v2.1.0-predemo")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}
