package com.pms.placemanagementsystemserverside.models.course

import com.pms.placemanagementsystemserverside.models.program.ProgramModel
import com.pms.placemanagementsystemserverside.models.user.address.AddressModel
import javax.persistence.*

@Entity(name = "course")
data class CourseModel(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        var name: String = "",

        var credit: Int = 0,

        var code: Int = 0,

        @OneToMany(targetEntity = ProgramModel::class)
        var programs: List<ProgramModel> = mutableListOf()
)