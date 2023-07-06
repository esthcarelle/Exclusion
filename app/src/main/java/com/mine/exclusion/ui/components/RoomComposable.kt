package com.mine.exclusion.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.text.style.TextAlign
import com.mine.exclusion.viewModel.RoomViewModel

@Composable
fun RoomComposable(vm: RoomViewModel) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2)
    ) {
        vm.roomsList.facilities?.let {
            items(it) { room ->
                Text(text = room.toString(), modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
            }
        }
    }
}