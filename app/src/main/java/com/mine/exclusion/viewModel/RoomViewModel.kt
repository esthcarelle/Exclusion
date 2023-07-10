package com.mine.exclusion.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mine.exclusion.model.ExclusionsItemItem
import com.mine.exclusion.model.OptionsItem
import com.mine.exclusion.services.APIService
import com.mine.exclusion.model.Response
import kotlinx.coroutines.launch

class RoomViewModel : ViewModel() {
    private var _roomsList by mutableStateOf<Response>(Response())
    private var errorMessage: String by mutableStateOf("")
    val roomsList: Response
        get() = _roomsList

    val selectedOptions = mutableMapOf<String, OptionsItem>()

    init {
        getRooms()
    }

    fun isExcluded(option: OptionsItem): Boolean {
        _roomsList.exclusions?.forEach { exclusion ->
            exclusion?.forEach {
                if (selectedOptions[it?.facility_id]?.id == it?.options_id) {
                    val excludedFacility =
                        _roomsList.facilities?.find { facilitiesItem -> facilitiesItem?.facility_id == it?.facility_id }
                    return excludedFacility != null && excludedFacility.options?.any { optionsItem -> optionsItem?.id == option.id } ?: false
                }
            }


        }
        return false
    }

    fun selectOption(facilityId: String, optionId: String) {
        // Find the facility and option based on the IDs
        val facility = _roomsList.facilities?.find { it?.facility_id == facilityId }
        val option = facility?.options?.find { it?.id == optionId }

        // If the option is excluded, return without selecting it
        if (option != null && isExcluded(option)) {
            println("Option ${option.name} is excluded.")
            return
        }

        // Remove the previously selected option for the same facility
        selectedOptions.remove(facilityId)

        // Select the new option
        if (option != null) {
            selectedOptions[facilityId] = option
            println("Option ${option.name} selected.")
        } else {
            println("Invalid option.")
        }
    }

    fun isEnabled(
        exclusionList: List<List<ExclusionsItemItem?>?>?,
        facilityId: String?,
        optionId: OptionsItem?,
        selectedItems: HashMap<String, OptionsItem>
    ): Boolean {
        if (exclusionList != null) {
            for (exclusions in exclusionList) {
                val exclusion =
                    exclusions?.find { it?.facility_id == facilityId && it?.options_id == optionId?.id }

                if (exclusion == null) {
                    selectedItems[facilityId.toString()] = optionId!!
                    return true
                } else {
                    for (i in selectedItems) {
                        val exclu = ExclusionsItemItem(i.value.id, i.key)
                        if (exclusions.contains(exclu) && exclusions.contains(exclusion)) {
                            return false
                        }
                    }
                }
            }
        }
        selectedItems[facilityId.toString()] = optionId!!
        return true
    }

    private fun getRooms() {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                val list = apiService.getRooms()

                _roomsList = list

                Log.e(ContentValues.TAG, "getImages: " + _roomsList)

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e(ContentValues.TAG, "getImages: " + errorMessage)
            }
        }
    }
}