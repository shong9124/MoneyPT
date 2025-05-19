package com.capstone.data.repository

import com.capstone.data.model.signUp.SignUpCompleteRequestDTO
import com.capstone.data.model.signUp.SignUpRequestDTO
import com.capstone.data.remote.AuthRemoteDataSource
import com.capstone.domain.model.RegisterInfo
import com.capstone.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthRemoteDataSource,
) : AuthRepository {
    override suspend fun signUp(email: String, password: String): Result<Boolean> {
        return try {
            val response = dataSource.signUp(SignUpRequestDTO(email, password))
            if(response.isSuccessful) {
                Result.success(true)
            } else {
                throw Exception("Request is failure")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUpComplete(registerInfo: RegisterInfo): Result<Boolean> {
        return try {
            val response = dataSource.signUpComplete(
                registerInfo.run {
                    SignUpCompleteRequestDTO(asset, birthDate, gender, nickname, salary)
                }
            )
            if(response.isSuccessful) {
                Result.success(true)
            } else {
                throw Exception("Request is failure")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            val response = dataSource.login(SignUpRequestDTO(email, password))
            if(response.isSuccessful) {
                Result.success(true)
            } else {
                throw Exception("Request is failure")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}