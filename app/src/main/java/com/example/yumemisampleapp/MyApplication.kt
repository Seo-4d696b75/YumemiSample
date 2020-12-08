package com.example.yumemisampleapp

import android.app.Application

/**
 * @author Seo-4d696b75
 * @version 2020/12/04.
 */
class MyApplication : Application() {

    val repository : DataRepository by lazy {
        val repository = DataRepository(getAPIService())
        repository
    }

}
