package com.example.proyectoappmoviles.Api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ApiViewModelFactory(private val repository: Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ApiViewModel(repository) as T
    }
}