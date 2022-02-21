package com.randomanimals.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomanimals.data.model.Animal
import com.randomanimals.data.repository.AnimalsRepository
import com.randomanimals.utils.Result
import kotlinx.coroutines.launch

class AnimalsViewModel(private val animalsRepository: AnimalsRepository) : ViewModel() {

    private val animalsLiveData = MutableLiveData<Result<ArrayList<Animal>>>()

    fun getAnimalsLiveData() = animalsLiveData

    fun loadAnimalsData() {
        animalsLiveData.postValue(Result.InProgress)
        viewModelScope.launch {
            val response = animalsRepository.getAnimalsFromApi()
            response.let { it ->
                when (it) {
                    is Result.Success -> {
                        val animalsList = it.extractData
                        animalsList?.let { list ->
                            animalsLiveData.postValue(Result.Success(list.sortedBy { it.name }
                                .toCollection(ArrayList())))
                        }
                    }
                    else -> animalsLiveData.postValue(it)
                }
            }
        }
    }
}