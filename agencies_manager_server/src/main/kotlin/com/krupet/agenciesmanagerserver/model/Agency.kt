package com.krupet.agenciesmanagerserver.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Agency(
    @Id
    val id: String? = null,
    val name: String,
    val country: String,
    val countryCode: String,
    val city: String,
    val street: String,
    val settlementCurrency: String,
    val contactPerson: String
)
