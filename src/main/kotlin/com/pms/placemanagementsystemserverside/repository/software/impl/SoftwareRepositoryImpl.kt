package com.pms.placemanagementsystemserverside.repository.software.impl

import com.pms.placemanagementsystemserverside.models.space.software.SoftwareModel
import com.pms.placemanagementsystemserverside.repository.software.SoftwareJpaRepository
import com.pms.placemanagementsystemserverside.repository.software.SoftwareRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository


@Repository
class SoftwareRepositoryImpl : SoftwareRepository {

    @Autowired
    private lateinit var softwareJpaRepository: SoftwareJpaRepository

    override fun create(software: SoftwareModel): SoftwareModel {
        return softwareJpaRepository.saveAndFlush(software)
    }

    override fun read(): List<SoftwareModel> {
        return softwareJpaRepository.findAll()
    }

    override fun update(software: SoftwareModel): SoftwareModel {
        return if (softwareJpaRepository.findById(software.id).isPresent) {
            softwareJpaRepository.saveAndFlush(software)
        } else {
            SoftwareModel()
        }
    }

    override fun delete(id: Long) {
        val existingSoftware = softwareJpaRepository.findById(id)
        softwareJpaRepository.delete(existingSoftware.get())
    }
}