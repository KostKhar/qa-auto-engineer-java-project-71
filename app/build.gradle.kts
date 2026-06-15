plugins {
    id("java")
    application
    checkstyle
    id("org.sonarqube") version "7.3.1.8318"
    jacoco
}

application {
 mainClass = "hexlet.code.App"
}


group = "hexlet.code"
version = "1.0-SNAPSHOT"

val picocliVersion = "4.7.7"
val jacksonVersion = "2.20.2"
val junitVersion = "5.10.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("info.picocli:picocli:$picocliVersion")
    implementation(platform("com.fasterxml.jackson:jackson-bom:$jacksonVersion"))
        implementation("com.fasterxml.jackson.core:jackson-databind")
        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    annotationProcessor("info.picocli:picocli-codegen:$picocliVersion")

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}



checkstyle {
    configProperties["org.checkstyle.google.suppressionfilter.config"] =
        "${project.rootDir}/config/checkstyle/checkstyle-suppressions.xml"
}

jacoco {
    toolVersion = "0.8.14"
}


tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
        html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
    }
}

sonar {
    properties {
        property("sonar.projectKey", "KostKhar_qa-auto-engineer-java-project-71")
        property("sonar.organization", "kostkhar")
         }
}