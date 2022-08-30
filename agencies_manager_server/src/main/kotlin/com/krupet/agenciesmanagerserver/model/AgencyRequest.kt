package com.krupet.agenciesmanagerserver.model

import java.util.UUID

data class AgencyRequest(
    val id: UUID?,
    val name: String,
    val country: String,
    val countryCode: String,
    val city: String,
    val street: String,
    val settlementCurrency: String,
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