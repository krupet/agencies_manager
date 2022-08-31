package com.krupet.agenciesmanagerserver.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Agency(
    val uuid: UUID,
    val name: String,
    val country: String,
    val countryCode: String,
    val city: String,
    val street: String,
    val settlementCurrency: String,
    val contactPerson: String
)
