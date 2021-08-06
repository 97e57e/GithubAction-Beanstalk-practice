plugins {
    java
    id("org.springframework.boot") version "2.5.3"
    id("com.google.cloud.tools.jib") version "3.1.2"
    id("org.ajoberstar.grgit") version "1.7.2"
}

java.sourceCompatibility = JavaVersion.VERSION_11


apply(plugin = "io.spring.dependency-management")
apply(plugin = "java")
apply(plugin = "org.ajoberstar.grgit")

val grgit: org.ajoberstar.grgit.Grgit by extra
val buildVersion by extra {grgit.head().abbreviatedId}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks {
    test {
        useJUnitPlatform()
    }

    jib {

        from {
            image = "openjdk:11-jre-slim"
        }

        to {
            image = "gardenlee/cicd-practice:${buildVersion}"
            System.getenv("DOCKER_USERNAME")?.let { envUsername ->
                System.getenv("DOCKER_PASSWORD")?.let { envPassword ->
                    auth {
                        username = envUsername
                        password = envPassword
                    }
                }
            }
        }

        container {
            mainClass = "lee.garden.cicdtest.CicdTestApplication"
            ports = listOf("8080")
            volumes = listOf("/tmp")
        }
    }
}