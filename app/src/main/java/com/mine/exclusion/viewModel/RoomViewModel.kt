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
        solveEquation(x = 18, y = 14, coeffA = -6, coeffB = -4)
    }

    fun isEnabled(
        facilityId: String?,
        optionId: OptionsItem?,
        selectedItems: HashMap<String, OptionsItem>
    ): Boolean {
        if (_roomsList.exclusions != null) {
            for (exclusions in _roomsList.exclusions!!) {
                val exclusion =
                    exclusions?.find { it?.facility_id == facilityId && it?.options_id == optionId?.id }
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
        selectedItems[facilityId.toString()] = optionId!!
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
    fun calculateOranges(total:Int, difference:Int){

        var maxwell = 0;
        var x = 0

        var gerald = maxwell + difference

        x = maxwell + gerald
        x = total

        maxwell = gerald - difference
        maxwell = x - gerald

//        x - gerald = gerald - difference
//        2 * gerald = x + difference

        gerald = (difference + x)/2

        maxwell = x - gerald

        println("Maxwell has $maxwell oranges and Gerald has $gerald oranges.")

    }
    fun solveEquation(x: Int,y: Int,coeffA: Int,coeffB: Int){

        var a = 0
        var b = 0

        // b = 18 - 6a
        // b = 14 - 4a

        // 18 - 6a = 14 - 4a
        // (-6+4)a = 14 - 18

        a = (y - x)/(coeffB - coeffA)
        b = x - (coeffA*a)

        println("a is $a and b is $b")
    }
}