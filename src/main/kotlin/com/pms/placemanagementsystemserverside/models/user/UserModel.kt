package com.pms.placemanagementsystemserverside.models.user

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.pms.placemanagementsystemserverside.models.enums.ActivationModelStatusEnum
import com.pms.placemanagementsystemserverside.models.enums.UserTypeEnum
import javax.persistence.*

@Entity(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
open class UserModel(

        @Id
        @SequenceGenerator(name = "id", sequenceName = "user_id_seq", allocationSize = 1)
        @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
        @JsonIgnoreProperties(allowGetters = true)
        open var id: Long? = null,

        open var name: String? = null,

        open var email: String? = null,

        open var number: Int? = null,

        open var street: String? = null,

        open var city: String? = null,

        open var state: Int? = null,

        open var status: ActivationModelStatusEnum? = null, //TODO criar get set para parsear o valor

        //ADMINISTRATOR: "Admin", PROFESSOR: "Professor", TI_SUPPORT: "TI",
        // ASSISTANT: "Auxiliar", STUDENT: "Aluno", MANAGER: "Gerenciador"
        // TODO acertar enum e criar get set pro parser posso tb criar um serializer para esses campos separados
        open var type: UserTypeEnum? = null,

        open var telephones: String? = null

) {
    override fun toString(): String {
        return "UserModel(" +
                "id=$id, name=$name, email=$email, number=$number, street='$street', city='$city', state=$state, " +
                "statusActivation=$status, type=$type, telephones=$telephones" +
                ")"
    }
}