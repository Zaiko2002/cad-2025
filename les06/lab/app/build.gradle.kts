plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-context:6.2.2")
    implementation("org.springframework:spring-aspects:6.2.2")
    implementation("org.springframework:spring-jdbc:6.2.2")
    implementation("com.opencsv:opencsv:5.9")
    
    // H2 база данных (встраиваемая)
    implementation("com.h2database:h2:2.2.224")
    
    // Логирование
    implementation("ch.qos.logback:logback-classic:1.5.6")
    
    // Для @PostConstruct (если используешь)
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "ru.bsuedu.cad.lab.Main"
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}