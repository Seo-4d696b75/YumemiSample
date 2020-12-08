package com.example.yumemisampleapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject

/**
 * This class provides access to the all the data in this app. Updating the data is also managed here.
 * **NOTE** the data is NOT stored in a disk, so fetching data is required every time the app rebooted.
 * @author Seo-4d696b75
 * @version 2020/12/04.
 */
class DataRepository(private val network: APIService) {

    val contributors: MutableLiveData<List<Contributor>> = MutableLiveData()

    /**
     * Fetch data from network. Main-safe
     */
    suspend fun updateContributors() {
        val res = network.getContributors()
        Log.d("HTTP", res.toString())
        contributors.value = res.map { data -> Contributor(data.get("login").asString, data) }
    }

}

data class Contributor(
    val name: String,
    val data: JsonObject
)
