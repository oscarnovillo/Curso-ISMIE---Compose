package io.buildwithnd.demotmdb.network.services

import com.example.primerxmlmvvm.data.remote.modelo.UserRemote
import retrofit2.Response
import retrofit2.http.GET


/**
 * Retrofit API Service
 */
interface UserService {

    @GET("/users")
    suspend fun getUsers() : Response<List<UserRemote>>
//
//    @GET("/3/movie/{movie_id}")
//    suspend fun getMovie(@Path("movie_id") id: Int) : Response<MovieDesc>
}