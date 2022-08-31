package com.krupet.agenciesmanagerserver.model

import java.util.UUID
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

const val COUNTRY_CODE_LENGTH_VIOLATION_MESSAGE = "Country code should be from 2 to 3 characters"
const val NAME_NOT_BLANK_VIOLATION_MESSAGE = "Name cannot be blank"
const val COUNTRY_NOT_BLANK_VIOLATION_MESSAGE = "Country cannot be blank"
const val CITY_NOT_BLANK_VIOLATION_MESSAGE = "City cannot be blank"
const val STREET_NOT_BLANK_VIOLATION_MESSAGE = "Street cannot be blank"
const val SETTLEMENT_CURRENCY_NOT_BLANK_VIOLATION_MESSAGE = "Settlement currency cannot be blank"
const val CONTACT_PERSON_NOT_BLANK_VIOLATION_MESSAGE = "Contact person cannot be blank"

data class AgencyRequest(
    val id: UUID?,
    @field:NotBlank(message = NAME_NOT_BLANK_VIOLATION_MESSAGE)
    val name: String,
    @field:NotBlank(message = COUNTRY_NOT_BLANK_VIOLATION_MESSAGE)
    val country: String,
    @field:Size(min=2, max = 3, message = COUNTRY_CODE_LENGTH_VIOLATION_MESSAGE)
    val countryCode: String,
    @field:NotBlank(message = CITY_NOT_BLANK_VIOLATION_MESSAGE)
    val city: String,
    @field:NotBlank(message = STREET_NOT_BLANK_VIOLATION_MESSAGE)
    val street: String,
    @field:NotBlank(message = SETTLEMENT_CURRENCY_NOT_BLANK_VIOLATION_MESSAGE)
    val settlementCurrency: String,
    @field:NotBlank(message = CONTACT_PERSON_NOT_BLANK_VIOLATION_MESSAGE)
    val contactPerson: String
)

fun AgencyRequest.toEntity(id: UUID = UUID.randomUUID()): Agency = Agency(
    id,
    this.name,
    this.country,
    this.countryCode,
    this.city,
    this.street,
    this.settlementCurrency,
    this.contactPerson
)