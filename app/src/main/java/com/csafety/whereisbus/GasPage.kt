package com.csafety.whereisbus

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.csafety.whereisbus.ui.theme.WhereIsBusTheme


@Composable
fun GasPage(data: BusPagesData, title: String = "油价查询") {
    val gasPriceList = data.gasPrice
    val profile = remember {
        mutableStateOf(gasPriceList)
    }
    LaunchedEffect(key1 = Unit, block = {
        sendRequestToGetGasPrice(
            profileState = profile
        )
        Log.d("Main Activity", profile.toString())
    })
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top
    ) {
        TitleBar(title)
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            gasTypeList.forEach { item ->
                Text(item.label, Modifier.weight(1f))
            }
        }
        LazyColumn {
            itemsIndexed(
                items = profile.value
            ) { _, gas ->
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    gasTypeList.forEach { item ->
                        if (item.value == "province")
                            Text(gas.province, Modifier.weight(1f))
                        if (item.value == "gasoline92")
                            Text(gas.gasoline92, Modifier.weight(1f))
                        if (item.value == "gasoline95")
                            Text(gas.gasoline95, Modifier.weight(1f))
                        if (item.value == "gasoline98")
                            Text(gas.gasoline98, Modifier.weight(1f))
                        if (item.value == "diesel")
                            Text(gas.diesel, Modifier.weight(1f))
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GasPagePreview() {
    WhereIsBusTheme {
        GasPage(mockBusPagesData)
    }
}