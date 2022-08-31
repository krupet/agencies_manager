package com.krupet.agenciesmanagerserver.repositories

import com.krupet.agenciesmanagerserver.model.Agency
import org.springframework.data.mongodb.repository.MongoRepository

interface AgencyRepository : MongoRepository<Agency, String> {
}