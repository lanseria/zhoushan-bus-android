package com.csafety.whereisbus

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.csafety.whereisbus.ui.theme.WhereIsBusTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleBar(title: String) {
    CenterAlignedTopAppBar(title = {
        Text(text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
    })
//    Row(
//        Modifier
//            .fillMaxWidth()
//            .padding(0.dp, 12.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center) {
//
//    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTitleBar() {
    WhereIsBusTheme {
        TitleBar("TitleBar")
    }
}