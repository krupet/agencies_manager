package com.krupet.agenciesmanagerserver.controllers

import com.krupet.agenciesmanagerserver.DATABASE_NAME
import com.krupet.agenciesmanagerserver.DATABASE_TEST_USER
import com.krupet.agenciesmanagerserver.MONGODB_PORT
import com.krupet.agenciesmanagerserver.model.Agency
import com.krupet.agenciesmanagerserver.repositories.AgencyRepository
import com.krupet.agenciesmanagerserver.testAgencies
import com.krupet.agenciesmanagerserver.testAgency
import com.krupet.agenciesmanagerserver.testAgencyId
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.filter.log.LogDetail
import org.apache.http.HttpStatus
import org.hamcrest.Matchers
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.GenericContainer

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AgencyControllerIntegrationTest {
    @LocalServerPort
    protected var port = 0

    @Autowired
    protected lateinit var agencyRepository: AgencyRepository

    @BeforeEach
    fun setUp() {
        RestAssured.port = port
    }

    @AfterEach
    internal fun tearDown() {
        deleteAllAgencies()
    }

    companion object {
        private val mongoDbContainer = GenericContainer("mongo:4.0.10")
            .apply {
                this.withExposedPorts(MONGODB_PORT)
                this.withEnv("MONGO_INITDB_DATABASE", DATABASE_NAME)
                this.withEnv("MONGO_INITDB_ROOT_USERNAME", DATABASE_TEST_USER)
                this.withEnv("MONGO_INITDB_ROOT_PASSWORD", DATABASE_TEST_USER)
            }

        init {
            mongoDbContainer.start()
            mongoDbContainer.logs
        }


        @JvmStatic
        @DynamicPropertySource
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri") {
                "mongodb://$DATABASE_TEST_USER:$DATABASE_TEST_USER@localhost:${
                    mongoDbContainer.getMappedPort(
                        MONGODB_PORT
                    )
                }"
            }
            registry.add("spring.data.mongodb.database") { DATABASE_NAME }
            registry.add("spring.data.mongodb.user") { DATABASE_TEST_USER }
            registry.add("spring.data.mongodb.password") { DATABASE_TEST_USER }
        }
    }

    @Test
    internal fun `add new agency`() {
        assertEquals(0L, countsAgencies())

        val payload = """
            {
              "name": "Le Chamois",
              "country": "France",
              "countryCode": "FRA",
              "city": "Paris",
              "street": "Rue Bonaparte 7",
              "settlementCurrency": "EUR",
              "contactPerson": "Madame Beaufort"
            }
        """.trimIndent()

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(payload)
        .`when`()
            .post("/agencies")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("uuid", Matchers.notNullValue())
            .body("name", Matchers.equalTo("Le Chamois"))
            .body("country", Matchers.equalTo("France"))
            .body("countryCode", Matchers.equalTo("FRA"))
            .body("city", Matchers.equalTo("Paris"))
            .body("street", Matchers.equalTo("Rue Bonaparte 7"))
            .body("settlementCurrency", Matchers.equalTo("EUR"))
            .body("contactPerson", Matchers.equalTo("Madame Beaufort"))

        assertEquals(1L, countsAgencies())
    }

    @Test
    internal fun `update existing agency`() {
        saveAgency(testAgency)
        assertEquals(1L, countsAgencies())

        val newName = "Le New Chamois"

        val payload = """
            {
              "name": "$newName",
              "country": "France",
              "countryCode": "FRA",
              "city": "Paris",
              "street": "Rue Bonaparte 7",
              "settlementCurrency": "EUR",
              "contactPerson": "Madame Beaufort"
            }
        """.trimIndent()

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(payload)
        .`when`()
            .put("/agencies/{id}", testAgencyId)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("uuid", Matchers.equalTo(testAgencyId.toString()))
            .body("name", Matchers.equalTo(newName))
            .body("country", Matchers.equalTo("France"))
            .body("countryCode", Matchers.equalTo("FRA"))
            .body("city", Matchers.equalTo("Paris"))
            .body("street", Matchers.equalTo("Rue Bonaparte 7"))
            .body("settlementCurrency", Matchers.equalTo("EUR"))
            .body("contactPerson", Matchers.equalTo("Madame Beaufort"))

        assertEquals(1L, countsAgencies())
    }

    @Test
    internal fun `delete agency by id`() {
        saveAgency(testAgency)
        assertEquals(1L, countsAgencies())

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
        .`when`()
            .delete("/agencies/{id}", testAgencyId)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("deletedId", Matchers.equalTo(testAgencyId.toString()))

        assertEquals(0, countsAgencies())
    }

    @Test
    internal fun `get all records`() {
        saveAgencies()
        assertEquals(testAgencies.size.toLong(), countsAgencies())

        val pagination = mapOf("page" to "0", "size" to "20")

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParams(pagination)
        .`when`()
            .get("/agencies")
        .then()
            .log().ifValidationFails(LogDetail.BODY)
            .statusCode(HttpStatus.SC_OK)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
//                little hack with spread operator as hamcrest miss-match on list type
            .body("content.name", Matchers.hasItems(*testAgencies.map { it.name }.toTypedArray()))
    }

    fun saveAgency(agency: Agency) {
        agencyRepository.insert(agency)
    }

    fun saveAgencies() {
        agencyRepository.saveAll(testAgencies)
    }

    fun deleteAllAgencies() {
        agencyRepository.deleteAll()
    }

    fun countsAgencies(): Long = agencyRepository.count()
}