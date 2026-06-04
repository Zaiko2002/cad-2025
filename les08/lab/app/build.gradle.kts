plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring
    implementation("org.springframework:spring-context:6.1.14")
    implementation("org.springframework:spring-orm:6.1.14")
    implementation("org.springframework.data:spring-data-jpa:3.2.5")
    
    // JPA и Hibernate
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")
    
    // HikariCP и H2
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.h2database:h2:2.2.224")
    
    // Логирование (простое)
    implementation("org.slf4j:slf4j-simple:2.0.13")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "ru.bsuedu.cad.lab.app.Main"
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}