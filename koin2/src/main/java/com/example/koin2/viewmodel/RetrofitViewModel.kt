package com.example.koin2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.koin2.model.POJO
import com.example.koin2.model.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Handler

class RetrofitViewModel : ViewModel() {

    val livePojo = MutableLiveData<List<POJO>>()

    fun getLivePojo(): LiveData<List<POJO>> {
        val retrofitInterface = RetrofitInterface.startAPICall().getListOfData()
        retrofitInterface.enqueue(object : Callback<List<POJO>> {
            override fun onResponse(call: Call<List<POJO>>, response: Response<List<POJO>>) {
                livePojo.postValue(response.body())
            }

            override fun onFailure(call: Call<List<POJO>>, t: Throwable) {
            }
        })

        return livePojo
    }

}
