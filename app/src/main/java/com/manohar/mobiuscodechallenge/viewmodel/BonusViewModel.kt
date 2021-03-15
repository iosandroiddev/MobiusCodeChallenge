package com.manohar.mobiuscodechallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manohar.mobiuscodechallenge.data.remote.RetrofitBuilder
import com.manohar.mobiuscodechallenge.model.CouponCodeResponse
import kotlinx.coroutines.*

class BonusViewModel : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onErrorMessage("Exception:${throwable.localizedMessage}")
    }

    private var _job: Job? = null
    val couponCodesResponse = MutableLiveData<CouponCodeResponse>()
    val apiErrorResponse = MutableLiveData<String>()
    val apiLoadingIndicator = MutableLiveData<Boolean>()

    /**
     * Call the endpoint and fetch the Coupon Codes.
     *
     */
    fun fetchBonusCodes() {
        apiLoadingIndicator.postValue(true)
        _job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = RetrofitBuilder.apiService.getBonusCodes()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        couponCodesResponse.postValue(response.body())
                        apiErrorResponse.postValue("")
                        apiLoadingIndicator.postValue(false)
                    } else {
                        apiLoadingIndicator.postValue(false)
                        apiErrorResponse.postValue(response.errorBody()?.charStream()?.readText())
                    }
                } else {
                    apiLoadingIndicator.postValue(false)
                    apiErrorResponse.postValue(response.errorBody()?.charStream()?.readText())
                }
            }
        }
    }

    private fun onErrorMessage(message: String) {
        apiErrorResponse.postValue(message)
        apiLoadingIndicator.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        _job?.cancel()
    }
}