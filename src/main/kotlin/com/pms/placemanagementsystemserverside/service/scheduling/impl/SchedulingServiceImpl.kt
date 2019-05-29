package com.pms.placemanagementsystemserverside.service.scheduling.impl

import com.pms.placemanagementsystemserverside.domain.scheduling.SchedulingDomain
import com.pms.placemanagementsystemserverside.models.scheduling.SchedulingModel
import com.pms.placemanagementsystemserverside.repository.scheduling.SchedulingRepository
import com.pms.placemanagementsystemserverside.service.scheduling.SchedulingService
import com.pms.placemanagementsystemserverside.service.space.SpaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SchedulingServiceImpl : SchedulingService {

    @Autowired
    private lateinit var schedulingRepository: SchedulingRepository

    @Autowired
    private lateinit var schedulingDomain: SchedulingDomain

    @Autowired
    private lateinit var spaceService: SpaceService


    override fun create(schedulingModel: SchedulingModel): SchedulingModel {
        val filteredSpaces = spaceService.filterSpaceBySpaceIntention(schedulingModel.space)
        val spaceModel = schedulingDomain.findSpaceToThisScheduling(schedulingModel, filteredSpaces)
        schedulingModel.space = spaceModel
        return schedulingRepository.create(schedulingModel)
    }

    override fun read(): List<SchedulingModel> {
        return schedulingRepository.read()
    }

    override fun update(schedulingModel: SchedulingModel) {
        schedulingRepository.update(schedulingModel)
    }

    override fun delete(id: Long) {
        schedulingRepository.delete(id)
    }

}