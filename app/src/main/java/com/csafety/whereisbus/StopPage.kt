package com.csafety.whereisbus

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.csafety.whereisbus.ui.theme.WhereIsBusTheme


@Composable
fun HotStopButtons(hotBusStopList: List<HotBusStop>?) {
    val profile = remember {
        mutableStateOf(hotBusStopList ?: listOf())
    }
    LaunchedEffect(key1 = Unit, block = {
        sendRequestToGetHotBusStops(
            profileState = profile
        )
        Log.d("Main Activity", profile.toString())
    })

    Column(
        Modifier
            .padding(24.dp, 12.dp)
            .fillMaxWidth(),
    ) {
        Text(text = "热门站点: ")
        LazyRow(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(0.dp, 8.dp)
        ) {
            itemsIndexed(items = profile.value) { _, item ->
                Button(onClick = {
                }, Modifier.padding(4.dp)) {
                    Text(text = item.stationName)
                }
            }

        }
    }
}

@Composable
fun StopPage(data: BusPagesData, title: String = "站点查询") {
    val hotBusStopList = data.hotBusStop
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top
    ) {
        TitleBar(title)
        SearchBar()
        HotStopButtons(hotBusStopList)
    }
}


@Preview(showBackground = true)
@Composable
fun StopPagePreview() {
    WhereIsBusTheme {
        StopPage(mockBusPagesData)
    }
}