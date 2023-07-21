package com.example.cryptrox.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cryptrox.model.ParentModelClass
import com.example.cryptrox.network.ApiService


class CryptoRepository(private val apiService: ApiService){

    private val cryptoMlData= MutableLiveData<ParentModelClass>()
    val cryptoLiveData: LiveData<ParentModelClass>
        get() = cryptoMlData
    val progressBar= MutableLiveData<Boolean>()

    suspend fun getNowPlayingMoviesList(){
        val result=apiService.getCryptoData()
        progressBar.postValue(true)
        if(result?.body()!=null){
            cryptoMlData.postValue(result.body())
            progressBar.postValue(false)
        }
    }
}