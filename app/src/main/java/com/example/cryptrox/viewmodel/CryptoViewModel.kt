package com.example.cryptrox.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptrox.model.ParentModelClass
import com.example.cryptrox.repository.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CryptoViewModel(private val repository: CryptoRepository): ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getNowPlayingMoviesList()
            //Log.d("APPDATA",repository.cryptoLiveData.toString())
        }
    }

    val cryptoData: LiveData<ParentModelClass>
        get() = repository.cryptoLiveData

    val progress: LiveData<Boolean>
        get() = repository.progressBar

}