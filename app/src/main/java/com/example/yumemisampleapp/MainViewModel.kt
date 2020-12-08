package com.example.yumemisampleapp

import android.provider.ContactsContract
import android.util.Log
import android.util.MutableDouble
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.Factory
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.*

/**
 * @author Seo-4d696b75
 * @version 2020/12/04.
 */
class MainViewModel(private val repository: DataRepository) : ViewModel() {

    companion object {
        fun getFactory(repository : DataRepository) : ViewModelProvider.Factory {
            return object: ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    if ( modelClass.isAssignableFrom(MainViewModel::class.java) ){
                        return MainViewModel(repository) as T
                    } else {
                        throw IllegalArgumentException("unknown ViewModel Class")
                    }
                }
            }
        }
    }

    val contributors : LiveData<List<String>> = repository.contributors.map{
        it.map{ data -> data.name}
    }

    private val _loading = MutableLiveData<Boolean>(false)

    val loading : LiveData<Boolean>
        get() = _loading

    fun updateData(force : Boolean = true){
        if ( !force && hasLoaded ) return
        if (_loading.value == true) return
        viewModelScope.launch {
            try{
                _loading.value = true
                repository.updateContributors()
            } catch (err : Throwable){
                Log.e("HTTP", err.message?: "fail")
            } finally {
                _loading.value = false
                hasLoaded = true
            }
        }
    }

    private var hasLoaded : Boolean = false

}
