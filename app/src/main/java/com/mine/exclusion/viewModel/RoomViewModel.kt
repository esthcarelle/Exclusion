package com.mine.exclusion.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mine.exclusion.services.APIService
import com.mine.exclusion.model.Response
import kotlinx.coroutines.launch

class RoomViewModel : ViewModel() {
    private var _roomsList by mutableStateOf<Response>(Response())
    private var errorMessage: String by mutableStateOf("")
    val roomsList: Response
        get() = _roomsList

    init {
        getImages()
    }

    fun getImages() {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                val list = apiService.getRooms()

                    _roomsList = list

                Log.e(ContentValues.TAG, "getImages: "+_roomsList)

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e(ContentValues.TAG, "getImages: "+errorMessage )
            }
        }
    }
}