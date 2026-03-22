plugins {
   `java-library`
}

dependencies {
   api(platform(libs.qqq.bom))
   api(libs.qqq.backend.core)
   api(libs.qqq.backend.module.rdbms)
   api(libs.qqq.backend.module.postgres)
   api(libs.qqq.backend.module.filesystem)

   implementation(libs.jakarta.persistence.api)
   implementation(libs.hibernate.core)
   implementation(libs.postgres.driver)
   implementation(libs.liquibase.core)
   implementation(libs.log4j.api)
   implementation(libs.log4j.core)
   implementation(libs.jackson.databind)
   implementation(libs.jackson.datatype.jsr310)

   testImplementation(libs.h2)
}
