package com.hank.credit.other

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hank.credit.other.net.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OtherViewModel : ViewModel(){

    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    init {
        getProperties()
    }

    private fun getProperties(){
        coroutineScope.launch {
            val dataDeffer = NetworkApi.photos.getPhotos()
            try {
                val listResult = dataDeffer.await()
                _properties.value = listResult
            }catch (t : Throwable){
                Log.d("getProperties", "fail : ${t.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}