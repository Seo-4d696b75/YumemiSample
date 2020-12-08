package com.example.yumemisampleapp

import android.app.Application

/**
 * Application context with my custom repository
 * @author Seo-4d696b75
 * @version 2020/12/04.
 */
class MyApplication : Application() {

    val repository : DataRepository by lazy {
        val repository = DataRepository(getAPIService())
        repository
    }

}
