package com.krupet.agenciesmanagerserver.services

import com.krupet.agenciesmanagerserver.model.Agency
import com.krupet.agenciesmanagerserver.repositories.AgencyRepository
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class AgencyServiceImpl(private val agencyRepository: AgencyRepository) : AgencyService {
    override fun create(agency: Agency): Agency =
        agencyRepository.insert(agency)

    override fun update(agency: Agency): Agency {
        return agencyRepository.findById(agency.uuid)
            .map { it.copy(agency) }
            .map { agencyRepository.save(it) }
            .orElseThrow { RuntimeException("Cant find agency by id: {${agency.uuid}}") }
    }

    override fun delete(agencyId: UUID): UUID {
        agencyRepository.deleteById(agencyId)
        return agencyId
    }

    override fun fetch(pageable: Pageable): Page<Agency> =
        agencyRepository.findAll(pageable)
}

fun Agency.copy(other: Agency): Agency =
    this.copy(
        uuid = other.uuid,
        name = other.name,
        country = other.country,
        countryCode = other.countryCode,
        city = other.city,
        street = other.street,
        settlementCurrency = other.settlementCurrency,
        contactPerson = other.contactPerson,
    )