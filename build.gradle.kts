plugins {
   java
   jacoco
}

allprojects {
   repositories {
      mavenLocal()
      mavenCentral()
   }
}

subprojects {
   apply(plugin = "java")
   apply(plugin = "jacoco")

   java {
      toolchain {
         languageVersion.set(JavaLanguageVersion.of(21))
      }
   }

   tasks.withType<JavaCompile> {
      options.encoding = "UTF-8"
      options.compilerArgs.addAll(listOf("-Xlint:deprecation", "-Xlint:unchecked"))
   }

   tasks.withType<Test> {
      useJUnitPlatform()
   }

   tasks.jacocoTestReport {
      reports {
         xml.required.set(true)
         html.required.set(true)
      }
   }

   val libs = rootProject.the<VersionCatalogsExtension>().named("libs")

   dependencies {
      testImplementation(libs.findLibrary("junit-jupiter").orElseThrow())
      testImplementation(libs.findLibrary("assertj-core").orElseThrow())
   }
}
