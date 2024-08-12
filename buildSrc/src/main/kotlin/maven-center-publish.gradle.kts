plugins {
    id("kotlin")
    id("maven-publish")
}
val jarSources by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.map { it.allSource })
}

val jarJavadoc by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}


publishing {
    repositories {
        maven {
            name = "central"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
            credentials {
                username = project.findProperty("OSSRH_USERNAME") as String? ?: System.getenv("OSSRH_USERNAME")
                password = project.findProperty("OSSRH_PASSWORD") as String? ?: System.getenv("OSSRH_PASSWORD")
            }
        }

        maven {
            name = "snapshot"
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
            credentials {
                username = project.findProperty("OSSRH_USERNAME") as String? ?: System.getenv("OSSRH_USERNAME")
                password = project.findProperty("OSSRH_PASSWORD") as String? ?: System.getenv("OSSRH_PASSWORD")
            }
        }
    }
    publications {

        create<MavenPublication>("dist") {
            from(components["java"])
            artifact(jarSources)
            artifact(jarJavadoc)

            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            pom{
                description.set("我的web开发工具集。My web development toolset.")
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("llh4github")
                        name.set("llh")
                        email.set("lilinhong_coding@foxmail.com")
                    }
                }
            }


        }
    }
}
