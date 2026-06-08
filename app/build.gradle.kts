plugins {
    id("java")
    application
    checkstyle
    id ("org.sonarqube") version ("7.3.0.8198")
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
}


sonar {
  properties {
    property("sonar.projectKey", "cicdpiplinetohell_qa-auto-engineer-java-project-71")
    property("sonar.organization", "cicdpiplinetohell")
  }
}

jacoco {
    toolVersion = "0.8.14"
    reportsDirectory = layout.buildDirectory.dir("customJacocoReportDir")
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}
tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

tasks.jacocoTestReport {
    reports {
        xml.required = false
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
    }
}


tasks.test {
    useJUnitPlatform()
}