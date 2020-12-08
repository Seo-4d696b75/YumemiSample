package com.example.yumemisampleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import java.lang.IllegalArgumentException

/**
 * This view model manages the data in InfoDialog
 * @author Seo-4d696b75
 * @version 2020/12/04.
 */
class InfoViewModel(private val repository: DataRepository) : ViewModel() {

    companion object {
        fun getFactory(repository : DataRepository) : ViewModelProvider.Factory {
            return object: ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    if ( modelClass.isAssignableFrom(InfoViewModel::class.java) ){
                        return InfoViewModel(repository) as T
                    } else {
                        throw IllegalArgumentException("unknown ViewModel Class")
                    }
                }
            }
        }
    }

    fun getDetails(index : Int) : LiveData<List<Pair<String,String>>> {
        return repository.contributors.map {
            val list = ArrayList<Pair<String,String>>()
            if ( index < it.size ) {
                val data = it[index].data
                for ( entry in data.entrySet()) {
                    list.add(Pair(entry.key, entry.value.asString))
                }
            }
            list
        }
    }
}
