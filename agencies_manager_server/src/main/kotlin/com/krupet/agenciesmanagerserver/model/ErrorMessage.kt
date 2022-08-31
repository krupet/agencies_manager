package com.krupet.agenciesmanagerserver.model

data class ErrorMessage(
    val timestamp: Long,
    val status: Int,
    val error: String,
    val path: String,
    val message: String
)