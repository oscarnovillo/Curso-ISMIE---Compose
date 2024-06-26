package com.example.primerxmlmvvm.data.remote.datasource

import com.example.primerxmlmvvm.data.remote.NetworkResult
import com.example.primerxmlmvvm.data.remote.modelo.toUser
import com.example.primerxmlmvvm.di.IoDispatcher
import com.example.primerxmlmvvm.domain.modelo.User
import io.buildwithnd.demotmdb.network.services.UserService
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(
    private val userService: UserService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseApiResponse() {
    suspend fun fetchUsers(): NetworkResult<List<User>?> =
        safeApiCall { userService.getUsers() }.map { lista ->
            lista?.map { it.toUser() }
        }

    suspend fun delUser(id : Int): NetworkResult<Boolean> =
        safeApiCallNoBody { userService.delUser(id) }




}

