package com.randomanimals.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.randomanimals.data.api.ApiService
import com.randomanimals.data.repository.AnimalsRepository

class ViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimalsViewModel::class.java)) {
            return AnimalsViewModel(AnimalsRepository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}