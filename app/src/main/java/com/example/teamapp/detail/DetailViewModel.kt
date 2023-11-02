package com.example.teamapp.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamapp.network.ApiClient
import com.example.teamapp.utils.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel() : ViewModel() {
    val resultDetaiUser = MutableLiveData<Result>()

    fun getDetailUser(username : String) {
        // Menggunakan viewModelScope untuk mengambil data dari API
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .getDetailUserGithub(username)

                emit(response)
            }.onStart {
                resultDetaiUser.value = Result.Loading(true)
            }.onCompletion {
                resultDetaiUser.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultDetaiUser.value = Result.Error(it)
            }.collect {
                resultDetaiUser.value = Result.Success(it)
            }
        }
    }
}
