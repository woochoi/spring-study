plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //implementation("org.springframework.boot:spring-boot-starter-actuator")
    //implementation("org.springframework.boot:spring-boot-starter-webflux")
    //implementation("org.springframework.boot:spring-boot-starter-mustache")
    //implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

    // reactor
    implementation("io.projectreactor:reactor-core:_")

    // rxjava
    implementation("io.reactivex.rxjava3:rxjava:_")

    // mutiny
    implementation("io.smallrye.reactive:mutiny:2.1.0")

    // reactor tool
    implementation("io.projectreactor:reactor-tools")

    // lombok
    annotationProcessor("org.projectlombok:lombok:_")
    compileOnly("org.projectlombok:lombok:_")


    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")

    testImplementation("org.junit.jupiter:junit-jupiter-api:_")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:_")

    implementation("org.slf4j:slf4j-api:_")
    implementation("ch.qos.logback:logback-classic:_")
    implementation("ch.qos.logback:logback-core:_")

}

tasks.test {
    useJUnitPlatform()
}