package com.mechadev.indirim.aktuel.urunler.ui.bottomsheet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechadev.indirim.aktuel.urunler.data.local.dao.ShoppingItemDao
import kotlinx.coroutines.launch
import com.mechadev.indirim.aktuel.urunler.data.local.dao.ShoppingListDao
import com.mechadev.indirim.aktuel.urunler.model.ShoppingItemModel
import com.mechadev.indirim.aktuel.urunler.model.ShoppingListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val shoppingListDao: ShoppingListDao,
    private val shoppingItemDao: ShoppingItemDao
) : ViewModel() {

    private val _shoppingModel = MutableLiveData<ShoppingListModel>()
    val shoppingModel: LiveData<ShoppingListModel> get() = _shoppingModel

    private val _shoppingListModel = MutableLiveData<List<ShoppingListModel>>()
    val shoppingListModel: LiveData<List<ShoppingListModel>> get() = _shoppingListModel


    private val _shoppingItemList = MutableLiveData<List<ShoppingItemModel>>()
    val shoppingItemList: LiveData<List<ShoppingItemModel>> get() = _shoppingItemList


    fun getOrCreateShoppingList(dateText: String) {
        viewModelScope.launch {
            val existingList = shoppingListDao.getListByDate(dateText)

            if (existingList != null) {
                /*
                val updatedList = existingList.copy(
                    updateTime = System.currentTimeMillis()
                )
                shoppingListDao.updateList(updatedList)
                */
                _shoppingModel.postValue(existingList)
            } else {
                val systemTime = System.currentTimeMillis()
                val newList = ShoppingListModel(
                    title = "Alışveriş Listesi",
                    dateText = dateText,
                    createTime = systemTime,
                    updateTime = systemTime
                )
                shoppingListDao.insertList(newList)
                _shoppingModel.postValue(newList)
            }
        }
    }


    //  val shoppingLists: LiveData<List<ShoppingItemModel>> = shoppingItemDao.getItemsForShoppingList()

    fun getItemsForList(shoppingListId: Int): LiveData<List<ShoppingItemModel>> {
        return shoppingItemDao.getItemsForShoppingList(shoppingListId)
    }

    fun getAllShoppingList(dateText: String) {
        viewModelScope.launch {
            val list = shoppingListDao.getAllLists()
            val filteredList = mutableListOf<ShoppingListModel>()

            for (shoppingList in list) {
                val shoppingItems = shoppingItemDao.getItemsByListId(shoppingList.id)
                if (shoppingItems.isNotEmpty() || shoppingList.dateText == dateText) {
                    if (shoppingList.dateText == dateText) {
                        filteredList.add(0, shoppingList)  // 0 indeksine ekler
                        Log.e("SHOPLS", "add at index 0")
                    } else {
                        filteredList.add(shoppingList)
                        Log.e("SHOPLS", "add")
                    }
                    Log.e("SHOPLS", "add")
                } else {
                    Log.e("SHOPLS", "empty")
                }
            }

            val existingList = shoppingListDao.getListByDate(dateText)
            if (existingList == null) {
                val systemTime = System.currentTimeMillis()
                val newList = ShoppingListModel(
                    title = "Alışveriş Listesi",
                    dateText = dateText,
                    createTime = systemTime,
                    updateTime = systemTime
                )
                shoppingListDao.insertList(newList)
                filteredList.add(0, newList)
            } else {
                if (!filteredList.contains(existingList)) {
                    filteredList.add(0, existingList)
                }
            }

            _shoppingListModel.postValue(filteredList)
        }
    }

    fun getShoppingListByDate(dateText: String) {
        viewModelScope.launch {
            val list = shoppingListDao.getListByDate(dateText)
            _shoppingModel.postValue(list)
        }
    }

    fun addShoppingList(shoppingList: ShoppingListModel) {
        viewModelScope.launch {
            shoppingListDao.insertList(shoppingList)
        }
    }

    fun deleteShoppingList(shoppingList: ShoppingListModel) {
        viewModelScope.launch {
            shoppingListDao.deleteList(shoppingList)
        }
    }
    fun deleteShoppingListById(id: Int) {
        viewModelScope.launch {
            shoppingListDao.deleteItemById(id)
        }
    }

    fun getShoppingItems(shoppingListId: Int): Flow<List<ShoppingItemModel>> {
        return shoppingItemDao.getItemsByListIdFlow(shoppingListId)
    }

    fun fetchShoppingItems(shoppingListId: Int) {
        viewModelScope.launch {
            val items = shoppingItemDao.getItemsByListId(shoppingListId)
            _shoppingItemList.postValue(items)
        }
    }

    fun addShoppingItem(shoppingListId: Int, content: String) {
        viewModelScope.launch {
            val newItem = ShoppingItemModel(
                shoppingListId = shoppingListId,
                content = content,
                isChecked = false
            )
            shoppingItemDao.insertItem(newItem)
            fetchShoppingItems(shoppingListId) // Yeni eklenen öğe ile listeyi güncelle
        }
    }

    // Item güncelle (isChecked durumu)
    fun updateShoppingItem(item: ShoppingItemModel) {
        viewModelScope.launch {
            val updatedItem = item.copy(isChecked = !item.isChecked) // Checkbox durumu değiştir
            shoppingItemDao.updateItem(updatedItem)
            fetchShoppingItems(item.shoppingListId) // Güncel listeyi al
        }
    }

    // Item silme
    fun deleteShoppingItem(item: ShoppingItemModel) {
        viewModelScope.launch {
            shoppingItemDao.deleteItem(item)
            fetchShoppingItems(item.shoppingListId) // Listeyi güncelle
        }
    }
}
