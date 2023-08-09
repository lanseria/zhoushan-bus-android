package com.csafety.whereisbus

import android.util.Log
import androidx.compose.runtime.MutableState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

data class TabItem (
    val path: String,
    val name: String,
    val icon: Int
)
val tabBarList = listOf(TabItem(
    path = "bus",
    name = "公交查询",
    icon = R.drawable.twotone_directions_bus_24
),TabItem(
    path = "stop",
    name = "站点查询",
    icon = R.drawable.twotone_bus_alert_24
),TabItem(
    path = "newspaper",
    name = "公交信息",
    icon = R.drawable.twotone_newspaper_24
),TabItem(
    path = "gas",
    name = "油价查询",
    icon = R.drawable.twotone_local_gas_station_24
))
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

data class BusPagesData(
    val hotBusLine: List<HotBusLine>,
    val hotBusStop: List<HotBusStop>
)


val mockBusPagesData = BusPagesData(
    hotBusLine = listOf(HotBusLine(_id = "1", name = "228路", count = 1)),
    hotBusStop = listOf(
        HotBusStop(
            _id = "2",
            stationName = "常州花苑",
            count = 1
        )
    )
)

const val BASE_URL = "https://s8zygv.laf.run/"

interface APIService {
    @Headers(
        "Accept: application/json"
    )

    @GET("/getHotBusLines")
    fun getHotBusLine(): Call<List<HotBusLine>?>?

    @GET("/getHotBusStops")
    fun getHotBusStops(): Call<List<HotBusStop>?>?
}


fun sendRequestToGetHotBusLine (profileState: MutableState<List<HotBusLine>>) {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(APIService::class.java)
    val call: Call<List<HotBusLine>?>? = api.getHotBusLine()
    call!!.enqueue(object: Callback<List<HotBusLine>?> {
        override fun onResponse(call: Call<List<HotBusLine>?>, response: Response<List<HotBusLine>?>) {
            if(response.isSuccessful) {
                Log.d("Main", "success!" + response.body().toString())
                profileState.value = response.body()!!
            }
        }
        override fun onFailure(call: Call<List<HotBusLine>?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
        }
    })
}

fun sendRequestToGetHotBusStops (profileState: MutableState<List<HotBusStop>>) {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(APIService::class.java)
    val call: Call<List<HotBusStop>?>? = api.getHotBusStops()
    call!!.enqueue(object: Callback<List<HotBusStop>?> {
        override fun onResponse(call: Call<List<HotBusStop>?>, response: Response<List<HotBusStop>?>) {
            if(response.isSuccessful) {
                Log.d("Main", "success!" + response.body().toString())
                profileState.value = response.body()!!
            }
        }
        override fun onFailure(call: Call<List<HotBusStop>?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
        }
    })
}