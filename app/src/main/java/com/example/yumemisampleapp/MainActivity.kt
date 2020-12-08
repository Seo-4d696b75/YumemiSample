package com.example.yumemisampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.json.JSONArray
import org.json.JSONException

/**
 *
 * This activity fetch data with HTTP request and show them in a list
 */
class MainActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by lazy {
        val factory = MainViewModel.getFactory((application as MyApplication).repository)
        ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // connect UI with data from ViewModel
        val listView = findViewById<ListView>(R.id.contributor_list)
        viewModel.contributors.observe(this, Observer {
            val adapter =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, it)
            listView.adapter = adapter
        })
        listView.setOnItemClickListener { adapterView, view, pos, id ->
            Log.d("list", "selected pos:$pos")
            val args = Bundle()
            args.putInt(InfoDialog.KEY_DATA_INDEX, pos)
            val dialog = InfoDialog()
            dialog.arguments = args
            dialog.show(supportFragmentManager, "info")
        }

        findViewById<View>(R.id.sync_button).setOnClickListener {
            viewModel.updateData()
        }

        val spinner = findViewById<View>(R.id.spinner)
        viewModel.loading.observe(this, Observer {
            spinner.visibility = if ( it ) View.VISIBLE else View.GONE
        })

        // update data if not yet
        viewModel.updateData(force = false)
    }

}
