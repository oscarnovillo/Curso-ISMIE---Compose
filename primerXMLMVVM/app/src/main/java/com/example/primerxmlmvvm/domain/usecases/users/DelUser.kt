package com.example.primerxmlmvvm.domain.usecases.users

import com.example.primerxmlmvvm.data.UsersRepository
import javax.inject.Inject

class DelUser  @Inject constructor(val userRepository: UsersRepository) {

    suspend operator fun invoke(id : Int) = userRepository.delUserConFlow(id)
}