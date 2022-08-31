package com.krupet.agenciesmanagerserver

import com.krupet.agenciesmanagerserver.model.Agency
import com.krupet.agenciesmanagerserver.model.AgencyRequest
import com.krupet.agenciesmanagerserver.model.CITY_NOT_BLANK_VIOLATION_MESSAGE
import com.krupet.agenciesmanagerserver.model.CONTACT_PERSON_NOT_BLANK_VIOLATION_MESSAGE
import com.krupet.agenciesmanagerserver.model.COUNTRY_CODE_LENGTH_VIOLATION_MESSAGE
import com.krupet.agenciesmanagerserver.model.COUNTRY_NOT_BLANK_VIOLATION_MESSAGE
import com.krupet.agenciesmanagerserver.model.NAME_NOT_BLANK_VIOLATION_MESSAGE
import com.krupet.agenciesmanagerserver.model.SETTLEMENT_CURRENCY_NOT_BLANK_VIOLATION_MESSAGE
import com.krupet.agenciesmanagerserver.model.STREET_NOT_BLANK_VIOLATION_MESSAGE
import javax.validation.Validation
import javax.validation.Validator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ValidationChecksTest {
    private val factory = Validation.buildDefaultValidatorFactory()
    private val validator: Validator = factory.validator

    @Test
    internal fun `validate agency request and expected no violations`() {
        val violations = validator.validate<AgencyRequest>(testAgency.toRequest())
        assertTrue(violations.isEmpty())
    }

    @ParameterizedTest(name = "[{index}]: {0}")
    @MethodSource("agencyRequestsProvider")
    internal fun `validate agency request and fail with violation`(
        message: String,
        agencyRequest: AgencyRequest,
        expectedMessage: String
    ) {
        val violations = validator.validate<AgencyRequest>(agencyRequest)

        assertEquals(1, violations.size)
        assertEquals(expectedMessage, violations.map { it.message }.first())
    }

    companion object {
        @JvmStatic
        fun agencyRequestsProvider() = listOf(
            Arguments.of("Fail on country code to long", testAgency.toRequest().copy(countryCode = "FRAAAAA!"), COUNTRY_CODE_LENGTH_VIOLATION_MESSAGE),
            Arguments.of("Fail on blank name", testAgency.toRequest().copy(name = ""), NAME_NOT_BLANK_VIOLATION_MESSAGE),
            Arguments.of("Fail on blank country", testAgency.toRequest().copy(country = ""), COUNTRY_NOT_BLANK_VIOLATION_MESSAGE),
            Arguments.of("Fail on blank city", testAgency.toRequest().copy(city = ""), CITY_NOT_BLANK_VIOLATION_MESSAGE),
            Arguments.of("Fail on blank street", testAgency.toRequest().copy(street = ""), STREET_NOT_BLANK_VIOLATION_MESSAGE),
            Arguments.of("Fail on blank settlementCurrency", testAgency.toRequest().copy(settlementCurrency = ""), SETTLEMENT_CURRENCY_NOT_BLANK_VIOLATION_MESSAGE),
            Arguments.of("Fail on blank contactPerson", testAgency.toRequest().copy(contactPerson = ""), CONTACT_PERSON_NOT_BLANK_VIOLATION_MESSAGE)
        )
    }
}

fun Agency.toRequest(): AgencyRequest =
    AgencyRequest(
        id = this.uuid,
        name = this.name,
        country = this.country,
        countryCode = this.countryCode,
        city = this.city,
        street = this.street,
        settlementCurrency = this.settlementCurrency,
        contactPerson = this.contactPerson
    )