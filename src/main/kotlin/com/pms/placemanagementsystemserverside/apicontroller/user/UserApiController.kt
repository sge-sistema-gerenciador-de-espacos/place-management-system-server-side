package com.pms.placemanagementsystemserverside.apicontroller.user

import com.pms.placemanagementsystemserverside.apicontroller.contract.ApiController
import com.pms.placemanagementsystemserverside.dto.ProfessorLackPostRequest
import com.pms.placemanagementsystemserverside.dto.StudentEvasionResponse
import com.pms.placemanagementsystemserverside.models.api.authenticator.AuthenticatorRequestModel
import com.pms.placemanagementsystemserverside.models.api.authenticator.AuthenticatorResponseModel
import com.pms.placemanagementsystemserverside.models.api.response.ApiResponseModel
import com.pms.placemanagementsystemserverside.models.api.response.KeyResponseModel
import com.pms.placemanagementsystemserverside.models.api.response.StatusResponseModel
import com.pms.placemanagementsystemserverside.models.enums.StatusResponseTypeEnum
import com.pms.placemanagementsystemserverside.models.enums.UserTypeEnum
import com.pms.placemanagementsystemserverside.models.user.UserModel
import com.pms.placemanagementsystemserverside.service.user.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/pms-api/user"])
class UserApiController : ApiController<UserModel> {

    private val logger = LoggerFactory.getLogger(UserApiController::class.java)

    @Autowired
    private lateinit var userService: UserService

    override fun create(item: UserModel): ApiResponseModel {
        return try {
            logger.info("create::item: $item")
            val itemUpdated = userService.create(item)
            val apiResponseModel = ApiResponseModel(20000, KeyResponseModel(itemUpdated.id))
            logger.info("create::response::success: $apiResponseModel")
            apiResponseModel
        } catch (e: Exception) {
            logger.info("create::catch: ${e.message}")
            ApiResponseModel(HttpStatus.CONFLICT.value(), null)
        }
    }

    override fun readByFilter(item: UserModel): ApiResponseModel {
        var filteredUsers: List<UserModel>? = null

        return try {
            logger.info("readByFilter::item: $item")
            filteredUsers = userService.read()
            logger.info("readByFilter::filteredUsers: $filteredUsers")
            ApiResponseModel(20000, filteredUsers)
        } catch (e: Exception) {
            ApiResponseModel(HttpStatus.NOT_FOUND.value(), filteredUsers)
        }
    }

    override fun read(): ApiResponseModel {
        val userModelList = userService.read()
        logger.info("read::userModelList: $userModelList")
        return ApiResponseModel(20000, userModelList)
    }

    @GetMapping(value = ["/master"])
    fun readMaster(): ApiResponseModel {
        val userModelList = readActiveByType(UserTypeEnum.MANAGER)//TODO adc ADMINISTRATOR TB
        logger.info("read::userModelList: $userModelList")
        return ApiResponseModel(20000, userModelList)
    }

    @GetMapping(value = ["/professor"])
    fun readActiveProfessor(): ApiResponseModel {
        val userModelList = readActiveByType(UserTypeEnum.PROFESSOR)
        logger.info("readActivatedProfessor::userModelList: $userModelList")
        return ApiResponseModel(20000, userModelList)
    }

    @GetMapping(value = ["/scheduler"])
    fun readActiveScheduler(): ApiResponseModel {
        val userModelList = readActiveByType(UserTypeEnum.MANAGER)//TODO mudar
        logger.info("read::userModelList: $userModelList")
        return ApiResponseModel(20000, userModelList)
    }

    private fun readActiveByType(userTypeEnum: UserTypeEnum): List<UserModel> {
        //TODO fazer um arg de usuario agendadorF
        return userService.readActiveByType(userTypeEnum)
        //TODO fazer um overload no service para ter 2 metodos, um aceita array, outro aceita um enum
    }

    //TODO quando mudar pra inativo, registrar evasao bo campo
    override fun update(item: UserModel, id: Long): ApiResponseModel {
        return try {
            item.id = id
            logger.info("update::item: $item")
            userService.update(item)

            ApiResponseModel(
                    20000,
                    StatusResponseModel(StatusResponseTypeEnum.SUCCESS.status)
            )

        } catch (e: Exception) {
            ApiResponseModel(
                    HttpStatus.NO_CONTENT.value(),
                    StatusResponseModel(StatusResponseTypeEnum.SUCCESS.status)
            )
        }
    }

    override fun delete(id: Long): ApiResponseModel {
        return try {
            logger.info("delete::id: $id")
            userService.delete(id)
            ApiResponseModel(
                    20000,
                    StatusResponseModel(StatusResponseTypeEnum.SUCCESS.status)
            )

        } catch (e: Exception) {
            ApiResponseModel(
                    HttpStatus.NO_CONTENT.value(),
                    StatusResponseModel(StatusResponseTypeEnum.ERROR.status)
            )
        }
    }

    //TODO rever
    @PostMapping(value = ["/login"])
    fun login(@RequestBody authenticatorRequestModel: AuthenticatorRequestModel):
            ApiResponseModel {
        logger.info("login::authenticatorRequestModel: $authenticatorRequestModel")
        return ApiResponseModel(
                20000,
                AuthenticatorResponseModel()
        )
    }

    //TODO rever
    @PostMapping(value = ["/logout"])
    fun logout(): ApiResponseModel {
        logger.info("logout")
        return ApiResponseModel(20000, "success")
    }

    //TODO rever / acertar o response
    @GetMapping(value = ["/info"])
    fun getInfo(@RequestParam token: String): String {
        logger.info("info")
        return "{\"code\":20000,\"data\":{\"roles\":[\"admin\"],\"introduction\":\"I am a super administrator\",\"avatar\":\"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif\",\"name\":\"Super Admin\"}}"
    }

    @GetMapping(value = ["/report/evasion"])
    fun getEvasionDate(): ApiResponseModel {
        val list = userService.read()
        val studentEvasionList: MutableList<StudentEvasionResponse.StudentEvasion> = mutableListOf()
        list.forEach {
            studentEvasionList.add(StudentEvasionResponse.StudentEvasion(
                    it.name, it.evasionDate
            ))
        }

        return ApiResponseModel(20000, StudentEvasionResponse(studentEvasionList.size, studentEvasionList))
    }

    @PostMapping(value = ["/lack"])
    fun professorLack(@RequestBody professorLackPostRequest: ProfessorLackPostRequest): ApiResponseModel {
        userService.professorLack(professorLackPostRequest)
        return ApiResponseModel(
                20000,
                StatusResponseModel(StatusResponseTypeEnum.SUCCESS.status)
        )
    }

}