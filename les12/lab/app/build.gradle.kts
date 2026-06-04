plugins {
    java
    war
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-context:6.1.14")
    implementation("org.springframework:spring-orm:6.1.14")
    implementation("org.springframework.data:spring-data-jpa:3.2.5")
    implementation("org.springframework:spring-webmvc:6.1.14")
    implementation("org.thymeleaf:thymeleaf-spring6:3.1.2.RELEASE")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.h2database:h2:2.2.224")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.0")
    implementation("org.slf4j:slf4j-simple:2.0.13")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.war {
    archiveFileName.set("zoomagazin.war")
}