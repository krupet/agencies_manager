package com.krupet.agenciesmanagerserver.services

import com.krupet.agenciesmanagerserver.model.Agency
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AgencyService {
    fun create(agency: Agency): Agency
    fun update(agency: Agency): Agency
    fun delete(agencyId: String): String
    fun fetch(pageable: Pageable): Page<Agency>
}