package com.pms.placemanagementsystemserverside.service.software.impl

import com.pms.placemanagementsystemserverside.models.enums.ActivationModelStatusEnum
import com.pms.placemanagementsystemserverside.models.space.software.SoftwareModel
import com.pms.placemanagementsystemserverside.repository.software.SoftwareRepository
import com.pms.placemanagementsystemserverside.repository.space.SpaceRepository
import com.pms.placemanagementsystemserverside.service.software.SoftwareService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SoftwareServiceImpl : SoftwareService {

    @Autowired
    private lateinit var softwareRepository: SoftwareRepository

    @Autowired
    private lateinit var spaceRepository: SpaceRepository

    override fun create(softwareModel: SoftwareModel): SoftwareModel {
        return softwareRepository.create(softwareModel)
    }

    override fun read(): List<SoftwareModel> {
        return softwareRepository.read()
    }

    override fun readActive(): List<SoftwareModel> {
        return read().filter { it.status == ActivationModelStatusEnum.ACTIVE }
    }

    override fun readBySpace(labId: Long): List<SoftwareModel> {
        val computerLabModel = spaceRepository.read().find { it.id == labId }
        return computerLabModel?.softwares?.toList() ?: mutableListOf()
    }

    override fun update(softwareModel: SoftwareModel) {
        softwareRepository.update(softwareModel)
    }

    override fun delete(id: Long) {
        softwareRepository.delete(id)
    }

}