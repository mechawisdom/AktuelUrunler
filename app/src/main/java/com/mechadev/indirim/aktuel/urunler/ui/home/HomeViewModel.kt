package com.mechadev.indirim.aktuel.urunler.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechadev.indirim.aktuel.urunler.data.local.dao.RecentlyAddedDao
import com.mechadev.indirim.aktuel.urunler.model.RecentlyAddedModel
import com.mechadev.indirim.aktuel.urunler.data.remote.api.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recentlyAddedDao: RecentlyAddedDao,
    private val apiService: ApiService
) : ViewModel() {
    private val _recentlyAddedList = MutableLiveData<List<RecentlyAddedModel>>()
    val recentlyAddedList: LiveData<List<RecentlyAddedModel>> get() = _recentlyAddedList


    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading



    fun getRecentlyAddedListLocal() {
        if (_recentlyAddedList.value.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    //recentlyAddedDao.deleteAllItems()

                    val localData = recentlyAddedDao.getRecentlyAddedItems()
                    if (localData.isNotEmpty()) {
                        Log.e("TAG", "local not empty")
                        _recentlyAddedList.postValue(localData)
                    } else {
                        _isLoading.postValue(true)
                        Log.e("TAG", "local empty")
                    }
                    getRecentlyAddedListRemote()
                } catch (e: Exception) {
                    _errorMessage.postValue(e.message)
                }
            }
        }


    }

    fun getRecentlyAddedListRemote() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val remoteData = apiService.getRecentlyAdded()
                val localData = recentlyAddedDao.getRecentlyAddedItems()
                if (!areListsEqual(remoteData, localData)) {

                    Log.e("TAG", "not equlity")
                    _recentlyAddedList.postValue(remoteData)
                    recentlyAddedDao.deleteAllItems()
                    recentlyAddedDao.insertRecentlyAddedItems(remoteData)
                } else {


                    Log.e("TAG", "equlity")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun areListsEqual(
        remoteData: List<RecentlyAddedModel>,
        localData: List<RecentlyAddedModel>
    ): Boolean {
        val sortedRemote = remoteData.sortedBy { it.id }
        val sortedLocal = localData.sortedBy { it.id }
        return sortedRemote == sortedLocal
    }

}