plugins {
   alias(libs.plugins.shadow)
}

dependencies {
   implementation(project(":concilium-core"))
   implementation(project(":concilium-orchestration"))
   implementation(project(":concilium-integrations"))
   implementation(project(":concilium-memory"))

   implementation(libs.qqq.middleware.javalin)
   implementation(libs.qqq.middleware.picocli)
   implementation(libs.qqq.middleware.health)
   implementation(libs.qqq.frontend.material.dashboard)
   implementation(libs.jackson.databind)
   implementation(libs.jackson.datatype.jsr310)
   implementation(libs.slf4j.simple)
   implementation(libs.log4j.api)
   implementation(libs.log4j.core)

   testImplementation(libs.h2)
}

tasks.shadowJar {
   archiveBaseName.set("concilium")
   archiveClassifier.set("")
   archiveVersion.set("")
   isZip64 = true
   manifest {
      attributes["Main-Class"] = "com.kof22.concilium.ConciliumServer"
   }
   mergeServiceFiles()
}
