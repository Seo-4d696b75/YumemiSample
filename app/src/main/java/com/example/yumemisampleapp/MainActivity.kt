package com.example.yumemisampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var mListView : ListView? = null
    private var mData : JSONArray? = null

    val KEY_DATA_LIST = "data_list"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mListView = findViewById<ListView>(R.id.contributor_list)
        if ( savedInstanceState == null ){
            fetchData()
        } else {
            try{
                mData = JSONArray(savedInstanceState.getString(KEY_DATA_LIST, "[]"))
                updateView()
            }catch (e : JSONException){
                Log.e("JSON parse", e.toString())
            }
        }
    }

    private fun fetchData(){
        getString(R.string.api_url).httpGet().response { request, response, result ->
            when (result) {
                is Result.Success -> {
                    Log.d("HTTP", "success")
                    val data = JSONArray(String(response.data))
                    Log.d("HTTP", data.toString())
                    mData = data
                    updateView()
                }
                is Result.Failure -> {
                    Log.e("HTTP", result.error.toString())
                    Toast.makeText(applicationContext, result.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateView(){
        val list = ArrayList<String>()
        val key = getString(R.string.data_name_key)
        try{
            for ( i in 0 until (mData?.length() ?: 1)){
                list.add(mData?.getJSONObject(i)?.getString(key) ?: "unknown")
            }
        }catch (e : JSONException){
            Log.e("JSON parse", e.toString())
        }
        val adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, list)
        mListView?.adapter = adapter
        mListView?.setOnItemClickListener { adapterView, view, pos, id ->
            Log.d("list", "selected pos:$pos")
            val data = mData?.getJSONObject(pos)
            if ( data != null ){
                val dialog = InfoDialog()
                val arg = Bundle()
                val info = ArrayList<Bundle>()
                for ( key in data.keys()){
                    val item = Bundle()
                    item.putString("key", key)
                    item.putString("value", data.get(key).toString())
                    info.add(item)
                }
                arg.putParcelableArrayList(InfoDialog.KEY_DATA, info)
                dialog.arguments = arg
                dialog.show(supportFragmentManager, "info")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_DATA_LIST, mData?.toString() ?: "[]")
    }
}
