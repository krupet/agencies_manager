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

    override fun update(agency: Agency): Agency =
        agencyRepository.save(agency)

    override fun delete(agencyId: UUID): UUID {
        agencyRepository.deleteByUuid(agencyId)
        return agencyId
    }

    override fun fetch(pageable: Pageable): Page<Agency> =
        agencyRepository.findAll(pageable)
}