package com.pms.placemanagementsystemserverside.apicontroller.user

import com.pms.placemanagementsystemserverside.apicontroller.contract.ApiController
import com.pms.placemanagementsystemserverside.models.api.authenticator.AuthenticatorRequestModel
import com.pms.placemanagementsystemserverside.models.api.authenticator.AuthenticatorResponseModel
import com.pms.placemanagementsystemserverside.models.user.UserModel
import com.pms.placemanagementsystemserverside.service.user.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping(
        value = ["/pms-api/user"],
        consumes = ["application/json"],
        produces = ["application/json"]
)
class UserApiController : ApiController<UserModel> {

    private val logger = LoggerFactory.getLogger(UserApiController::class.java)

    @Autowired
    private lateinit var userService: UserService

    @PostMapping(value = ["/login"])
    fun login(@RequestBody authenticatorRequestModel: AuthenticatorRequestModel):
            ResponseEntity<AuthenticatorResponseModel> {
        print("AQUI!")
        logger.error("AQUI!")
        logger.debug("AQUI!")
        logger.info("AQUI!")
        return ResponseEntity.ok(AuthenticatorResponseModel())
    }

    override fun create(item: UserModel): ResponseEntity<Unit> {
        return try {
            logger.info("create::item: $item")
            val itemUpdated = userService.create(item)
            ResponseEntity.created(URI.create("/users/${itemUpdated.id}")).build()
        } catch (e: Exception) {
            logger.info("create::catch: ${e.message}")
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

    override fun readByFilter(item: UserModel): ResponseEntity<List<UserModel>> {
        var filteredUsers: List<UserModel>? = null

        return try {
            logger.info("readByFilter::item: $item")
            filteredUsers = userService.read()
            logger.info("readByFilter::filteredUsers: $filteredUsers")
            ResponseEntity.ok(filteredUsers)

        } catch (e: Exception) {
            ResponseEntity(filteredUsers, HttpStatus.NOT_FOUND)
        }

    }

    override fun read(): ResponseEntity<List<UserModel>> {
//        val userModelList = userService.read()
        val userModelList = getUsers()
        logger.info("read::userModelList: $userModelList")
        return ResponseEntity.ok(userModelList)
    }

    override fun update(item: UserModel, id: Long): ResponseEntity<UserModel> {
        return try {
            logger.info("read::update: $item")
            userService.update(item)
            ResponseEntity.noContent().build()
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    override fun delete(id: Long): ResponseEntity<Unit> {
        return try {
            userService.delete(id)
            ResponseEntity.noContent().build()
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }

    }

    private fun getUsers(): List<UserModel> {
        return mutableListOf(UserModel(), UserModel(), UserModel())
    }
}