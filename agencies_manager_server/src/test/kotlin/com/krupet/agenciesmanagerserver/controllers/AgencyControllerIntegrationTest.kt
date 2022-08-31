package com.krupet.agenciesmanagerserver.controllers

import com.krupet.agenciesmanagerserver.DATABASE_NAME
import com.krupet.agenciesmanagerserver.DATABASE_TEST_USER
import com.krupet.agenciesmanagerserver.MONGODB_PORT
import com.krupet.agenciesmanagerserver.model.Agency
import com.krupet.agenciesmanagerserver.repositories.AgencyRepository
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import java.util.UUID
import org.apache.http.HttpStatus
import org.hamcrest.Matchers
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

    private val id = UUID.randomUUID()

    @Test
    internal fun `get all records`() {
        saveAgencies()

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
        .`when`()
            .get("/agencies")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("content.size()", Matchers.greaterThanOrEqualTo(1))
    }

    fun saveAgencies() {
        agencyRepository.saveAll(
            listOf(
                Agency(
                    uuid = id,
                    name = "Le Chamois",
                    country = "France",
                    countryCode = "FRA",
                    city = "Paris",
                    street = "Rue Bonaparte 7",
                    settlementCurrency = "EUR",
                    contactPerson = "Madame Beaufort"
                )
            )
        )
    }
}