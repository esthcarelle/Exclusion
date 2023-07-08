package com.mine.exclusion.ui.components

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mine.exclusion.R
import com.mine.exclusion.viewModel.RoomViewModel


@Composable
fun RoomComposable(vm: RoomViewModel) {
    val context = LocalContext.current

    val selectedItems = hashMapOf<String,String>()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        vm.roomsList.facilities?.let {
            itemsIndexed(it) {i, room ->
                room?.name?.let { it1 ->
                    Text(
                        text = it1,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(500),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        color = Color.White,
                        fontStyle = FontStyle.Normal,
                        textAlign = TextAlign.Center
                    )
                }
                val selectedValue = remember { mutableStateOf("") }
                val selectedId = remember { mutableStateOf("0") }

                LazyRow {
                    room?.options?.let { options ->
                        itemsIndexed(options) {
                                index, optionItem ->

                            Row(modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 16.dp)) {

                                Image(
                                    modifier = Modifier.size(100.dp),
                                    painter = painterResource(
                                        id = getDrawable(
                                            optionItem?.icon,
                                            context = context
                                        )
                                    ),
                                    contentDescription = ""
                                )

                                RadioButton(
                                    enabled = vm.isEnabled(vm.roomsList.exclusions?.get(i), facilityId = room?.facility_id , optionId = optionItem?.id),
                                    selected = selectedId.value == optionItem?.id,
                                    onClick = { selectedId.value = optionItem?.id.toString()
                                        selectedItems[room?.facility_id.toString()] = optionItem?.name.toString()

                                              },
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .fillMaxSize()
                                        .align(Alignment.CenterVertically)
                                )

                                Text(
                                    text = optionItem?.name.toString(),
                                    color = Color.White,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .align(Alignment.CenterVertically)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("DiscouragedApi")
fun getDrawable(name: String?, context: Context): Int {
    return if (name == "no-room") {
        R.drawable.no_room
    } else
        context.resources.getIdentifier(
            name,
            "drawable",
            context.packageName
        )
}