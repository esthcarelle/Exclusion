package com.mine.exclusion.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mine.exclusion.model.ExclusionsItem
import com.mine.exclusion.model.OptionsItem
import com.mine.exclusion.services.APIService
import com.mine.exclusion.model.Response
import kotlinx.coroutines.launch

class RoomViewModel : ViewModel() {
    private var _roomsList by mutableStateOf(Response())
    private var errorMessage: String by mutableStateOf("")
    val roomsList: Response
        get() = _roomsList

    init {
        getRooms()
    }

    fun isEnabled(
        facilityId: String?,
        optionId: OptionsItem,
        selectedItems: HashMap<String, OptionsItem>
    ): Boolean {
        _roomsList.exclusions?.let { list ->
            for (exclusions in list) {
                val exclusion =
                    exclusions?.find { it.facility_id == facilityId && it.options_id == optionId.id }
                for (i in selectedItems) {
                    val excl = ExclusionsItem(i.value.id, i.key)
                    if (exclusions != null) {
                        if (exclusions.contains(excl) && exclusions.contains(exclusion)) {
                            return false
                        }
                    }
                }
            }
        }
        selectedItems[facilityId.toString()] = optionId
        return true
    }

    private fun getRooms() {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                val list = apiService.getRooms()

                _roomsList = list

                Log.e(ContentValues.TAG, "getImages: $_roomsList")

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e(ContentValues.TAG, "getImages: $errorMessage")
            }
        }
    }
}