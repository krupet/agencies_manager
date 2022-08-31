package com.krupet.agenciesmanagerserver.controllers

import com.krupet.agenciesmanagerserver.model.Agency
import com.krupet.agenciesmanagerserver.model.AgencyRequest
import com.krupet.agenciesmanagerserver.model.toEntity
import com.krupet.agenciesmanagerserver.services.AgencyService
import java.util.UUID
import javax.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/agencies", produces = [MediaType.APPLICATION_JSON_VALUE])
class AgencyController(private val agencyService: AgencyService) {

    @PostMapping
    fun create(@Valid @RequestBody agencyRequest: AgencyRequest): Agency =
        agencyService.create(agencyRequest.toEntity())

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") agencyId: String,
        @Valid @RequestBody agencyRequest: AgencyRequest
    ): Agency = agencyService.update(
        agencyRequest.toEntity(agencyId)
    )

    @DeleteMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun delete(@PathVariable("id") agencyId: String): String =
        // TODO: fix me
        """{ "deletedId" : "${agencyService.delete(agencyId)}" }"""

    @GetMapping
    fun fetch(@PageableDefault(sort = ["name"]) pageable: Pageable): Page<Agency> =
        agencyService.fetch(pageable)
}
