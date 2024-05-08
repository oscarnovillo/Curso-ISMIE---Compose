package com.example.primerxmlmvvm.data

import com.example.primerxmlmvvm.data.local.CocheDao
import com.example.primerxmlmvvm.data.remote.NetworkResult
import com.example.primerxmlmvvm.data.remote.modelo.UserRemote
import com.example.primerxmlmvvm.data.remote.modelo.toUser
import com.example.primerxmlmvvm.di.IoDispatcher
import com.example.primerxmlmvvm.domain.modelo.User
import io.buildwithnd.demotmdb.network.services.UserService
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val userService: UserService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {


    suspend fun fetchUsers(): NetworkResult<List<User>> {

        try {
            val response = userService.getUsers()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body.map { userRemote -> userRemote.toUser() })
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }

    }

    suspend fun fetchUser(id: Int): NetworkResult<User> {

        try {
            val response = userService.getUser(id)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body.toUser())
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }

    }


    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")

}