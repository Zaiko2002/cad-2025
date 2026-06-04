plugins {
    java
    war
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Core & Data
    implementation("org.springframework:spring-context:6.1.14")
    implementation("org.springframework:spring-orm:6.1.14")
    implementation("org.springframework.data:spring-data-jpa:3.2.5")
    implementation("org.springframework:spring-webmvc:6.1.14")   
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.h2database:h2:2.2.224")
    implementation("org.slf4j:slf4j-simple:2.0.13")

    // Servlet API (provided by Tomcat)
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
    
    // Jackson для JSON
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.war {
    from(sourceSets.main.get().output)
    archiveFileName.set("zoomagazin.war")
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java")
        }
        resources {
            srcDirs("src/main/resources")
        }
    }
}