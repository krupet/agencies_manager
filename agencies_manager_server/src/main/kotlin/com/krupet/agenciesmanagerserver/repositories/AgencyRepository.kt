package com.krupet.agenciesmanagerserver.repositories

import com.krupet.agenciesmanagerserver.model.Agency
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID

interface AgencyRepository : MongoRepository<Agency, UUID> {
    fun deleteByUuid(uuid: UUID)
}