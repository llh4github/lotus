plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "lotus"
include("lotus-commons")
include("lotus-web-core")
include("lotus-web-security")

// example subproject
include("example:example-web")
