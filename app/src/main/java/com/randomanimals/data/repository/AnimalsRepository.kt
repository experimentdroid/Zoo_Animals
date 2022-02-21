package com.randomanimals.data.repository

import com.randomanimals.data.api.ApiService
import com.randomanimals.data.model.Animal
import com.randomanimals.utils.Result
import retrofit2.HttpException

class AnimalsRepository(private val apiService: ApiService) : BaseRepository() {

    companion object {
        private val TAG = AnimalsRepository::class.java.name
        const val GENERAL_ERROR_CODE = 499
    }

    suspend fun getAnimalsFromApi(): Result<ArrayList<Animal>> {
        var result: Result<ArrayList<Animal>> = handleSuccess(arrayListOf())
        try {
            val response = apiService.getAnimalsData()
            response.let {
                it.body()?.let { animalsResponse ->
                    result = handleSuccess(animalsResponse)
                }
                it.errorBody()?.let { responseErrorBody ->
                    if (responseErrorBody is HttpException) {
                        responseErrorBody.response()?.code()?.let { errorCode ->
                            result = handleException(errorCode)
                        }
                    } else result = handleException(GENERAL_ERROR_CODE)
                }
            }
        } catch (error: HttpException) {
            return handleException(error.code())
        }
        return result
    }

}