package com.tps.challenge

import android.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tps.challenge.network.TPSCoroutineService
import com.tps.challenge.network.model.StoreResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoreFeedViewModel @Inject constructor(private val repository: StoreRepository) :
    ViewModel() {

    private var storeFeedState = MutableLiveData<UIState<List<Store>>>(UIState.Loading)
    val storefeeduistatedata: LiveData<UIState<List<Store>>> = storeFeedState

    init {
        getStoreFeed(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE)
    }
    fun getStoreFeed(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val stores = repository.getStoreFeed(latitude, longitude)
            stores.collect {
                storeFeedState.value = it
            }
        }
    }
}
