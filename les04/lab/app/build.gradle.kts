plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-context:6.2.2")
    implementation("org.springframework:spring-aspects:6.2.2")  // для АОП
    implementation("com.opencsv:opencsv:5.9")
	
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