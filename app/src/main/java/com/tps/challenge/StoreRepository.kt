package com.tps.challenge

import android.util.Log
import com.tps.challenge.database.StoreDao
import com.tps.challenge.database.entities.PlaceholderEntity
import com.tps.challenge.network.TPSCoroutineService
import com.tps.challenge.network.model.StoreResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StoreRepository @Inject constructor(private val tpsCoroutineService: TPSCoroutineService,
    private val storeDao: StoreDao) {

    fun getStoreFeed(latitude: Double, longitude: Double): Flow<UIState<List<Store>>> = flow {
        emit(UIState.Loading)
         try {
             val networkstores = tpsCoroutineService.getStoreFeed(latitude, longitude)
             Log.d("repository", "network store size " + networkstores.size)
             val entitystore = networkstores.map { it.toEntity() }
             Log.d("repository", "entitystore size " + entitystore.size)
             val networkStoreIds = networkstores.map { it.id }
             Log.d("repository", "Network Store IDs: " + networkStoreIds.joinToString())
             storeDao.clearAll()
             storeDao.insertAll(entitystore)
         }catch (e: Exception){
             emit(UIState.Error("unable to ge store feed"))
         }
        val cachedstores = storeDao.getAllStores()
        Log.d("repository", "cachedstores size " + cachedstores.size)
        val stores = cachedstores.map { it.toStore() }
        Log.d("repository", "stores size " + stores.size)
        emit(UIState.Success(stores))
    }

    fun StoreResponse.toEntity(): PlaceholderEntity{
        return PlaceholderEntity(id = this.id, name = this.name, description = this.description, coverImgUrl = this.coverImgUrl,
            status = this.status, deliveryFeeCents = this.deliveryFeeCents)
    }

    fun PlaceholderEntity.toStore(): Store{
        return Store(id = this.id, name = this.name, description = this.description, coverImgUrl = this.coverImgUrl,
            status = this.status, deliveryFeeCents = this.deliveryFeeCents)
    }


}