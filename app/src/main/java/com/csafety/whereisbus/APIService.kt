package com.csafety.whereisbus

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.BusAlert
import androidx.compose.material.icons.twotone.DirectionsBus
import androidx.compose.material.icons.twotone.LocalGasStation
import androidx.compose.material.icons.twotone.Newspaper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

data class HotBusLine(
    val count: Int,
    val name: String,
    val _id: String
)

data class HotBusStop(
    val count: Int,
    val stationName: String,
    val _id: String
)


data class BusNew(
    val content: String,
    val date: String,
    val link: String,
    val title: String,
    val _id: String
)

data class GasPrice(
    val date: String,
    val diesel: String,
    val gasoline92: String,
    val gasoline95: String,
    val gasoline98: String,
    val province: String,
    val _id: String,
)

data class GasType(
    val label: String,
    val value: String
)

data class BusPagesData(
    val hotBusLine: List<HotBusLine>,
    val hotBusStop: List<HotBusStop>,
    val busNew: List<BusNew>,
    val gasPrice: List<GasPrice>
)

val gasTypeList = listOf(
    GasType(
        label = "省份",
        value = "province",
    ),
    GasType(
        label = "#92",
        value = "gasoline92",
    ),
    GasType(
        label = "#95",
        value = "gasoline95",
    ),
    GasType(
        label = "#98",
        value = "gasoline98",
    ),
    GasType(
        label = "柴油",
        value = "diesel",
    ),
)

val mockBusPagesData = BusPagesData(
    hotBusLine = listOf(
        HotBusLine(_id = "1", name = "228路", count = 1),
        HotBusLine(_id = "11", name = "137路", count = 1),
    ),
    hotBusStop = listOf(
        HotBusStop(
            _id = "2",
            stationName = "昌州花苑",
            count = 1
        ),
        HotBusStop(
            _id = "22",
            stationName = "东海小学",
            count = 1
        )
    ),
    busNew = listOf(
        BusNew(
            _id = "3",
            content = "接相关部门通知，2023年7月20日12时至18时白泉万毛线部分路段（全家站—毛竹山站）将进行全封闭道路沥青施工（具体根据实际施工情况，调整或恢复线路）受其影响，公交55路、88路、515路、216路临时调整线路走向，具体如下",
            date = "2023-07-19",
            link = "/art/2023/7/19/art_2698_93049.html",
            title = "关于7月20日万毛线沥青铺装施工公交线路临时调整的公告"
        ),
        BusNew(
            _id = "33",
            content = "接相关部门通知，2023年7月20日12时至18时白泉万毛线部分路段（全家站—毛竹山站）将进行全封闭道路沥青施工（具体根据实际施工情况，调整或恢复线路）受其影响，公交55路、88路、515路、216路临时调整线路走向，具体如下",
            date = "2023-07-19",
            link = "/art/2023/7/19/art_2698_93049.html",
            title = "关于7月20日万毛线沥青铺装施工公交线路临时调整的公告"
        )
    ),
    gasPrice = listOf(
        GasPrice(
            date = "8/10/2023",
            diesel = "7.80",
            gasoline92 = "8.08",
            gasoline95 = "8.60",
            gasoline98 = "9.58",
            province = "北京",
            _id = "64d42886cd438eea298308af",
        ),
        GasPrice(
            date = "8/10/2023",
            diesel = "7.73",
            gasoline92 = "8.04",
            gasoline95 = "8.55",
            gasoline98 = "9.55",
            province = "上海",
            _id = "64d42886cd438eea298308b0",
        )
    )
)

data class TabItem(
    val path: String,
    val name: String,
    val icon: ImageVector,
    val comp: @Composable (navBackStackEntry: NavBackStackEntry) -> Unit
)

val tabBarList = listOf(TabItem(
    path = "bus",
    name = "公交查询",
    icon = Icons.TwoTone.DirectionsBus,
    comp = {
        BusPage(mockBusPagesData, "公交查询")
    }
),
    TabItem(
        path = "stop",
        name = "站点查询",
        icon = Icons.TwoTone.BusAlert,
        comp = {
            StopPage(mockBusPagesData, "站点查询")
        }
    ),
    TabItem(
        path = "news",
        name = "信息查询",
        icon = Icons.TwoTone.Newspaper,
        comp = {
            NewsPage(mockBusPagesData, "信息查询")
        }
    ),
    TabItem(
        path = "gas",
        name = "油价查询",
        icon = Icons.TwoTone.LocalGasStation,
        comp = {
            GasPage(mockBusPagesData, "油价查询")
        }
    )
)
const val BASE_URL = "https://s8zygv.laf.run/"
val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
private val api = retrofit.create(APIService::class.java)

interface APIService {
    @Headers(
        "Accept: application/json"
    )

    @GET("/getHotBusLines")
    fun getHotBusLine(): Call<List<HotBusLine>?>?

    @GET("/getHotBusStops")
    fun getHotBusStops(): Call<List<HotBusStop>?>?

    @GET("/queryBusNews")
    fun getBusNews(): Call<List<BusNew>?>?

    @GET("/queryGasOnline")
    fun getGasPrice(): Call<List<GasPrice>?>?
}


fun sendRequestToGetHotBusLine(profileState: MutableState<List<HotBusLine>>) {
    val call: Call<List<HotBusLine>?>? = api.getHotBusLine()
    call!!.enqueue(object : Callback<List<HotBusLine>?> {
        override fun onResponse(
            call: Call<List<HotBusLine>?>,
            response: Response<List<HotBusLine>?>
        ) {
            if (response.isSuccessful) {
                Log.d("Main", "success!" + response.body().toString())
                profileState.value = response.body()!!
            }
        }

        override fun onFailure(call: Call<List<HotBusLine>?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
        }
    })
}

fun sendRequestToGetHotBusStops(profileState: MutableState<List<HotBusStop>>) {
    val call: Call<List<HotBusStop>?>? = api.getHotBusStops()
    call!!.enqueue(object : Callback<List<HotBusStop>?> {
        override fun onResponse(
            call: Call<List<HotBusStop>?>,
            response: Response<List<HotBusStop>?>
        ) {
            if (response.isSuccessful) {
                Log.d("Main", "success!" + response.body().toString())
                profileState.value = response.body()!!
            }
        }

        override fun onFailure(call: Call<List<HotBusStop>?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
        }
    })
}

fun sendRequestToGetBusNews(profileState: MutableState<List<BusNew>>) {
    val call: Call<List<BusNew>?>? = api.getBusNews()
    call!!.enqueue(object : Callback<List<BusNew>?> {
        override fun onResponse(call: Call<List<BusNew>?>, response: Response<List<BusNew>?>) {
            if (response.isSuccessful) {
                Log.d("Main", "success!" + response.body().toString())
                profileState.value = response.body()!!
            }
        }

        override fun onFailure(call: Call<List<BusNew>?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
        }
    })
}

fun sendRequestToGetGasPrice(profileState: MutableState<List<GasPrice>>) {
    val call: Call<List<GasPrice>?>? = api.getGasPrice()
    call!!.enqueue(object : Callback<List<GasPrice>?> {
        override fun onResponse(call: Call<List<GasPrice>?>, response: Response<List<GasPrice>?>) {
            if (response.isSuccessful) {
                Log.d("Main", "success!" + response.body().toString())
                profileState.value = response.body()!!
            }
        }

        override fun onFailure(call: Call<List<GasPrice>?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
        }
    })
}