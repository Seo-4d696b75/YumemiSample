package com.example.yumemisampleapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlin.collections.ArrayList

/**
 * @author Seo-4d696b75
 * @version 2020/11/25.
 * This dialog show details about the given account.
 * Info of the account is passed as BundleD
 */
class InfoDialog : DialogFragment() {

    private val viewModel : InfoViewModel by lazy {
        val factory = InfoViewModel.getFactory((activity?.application as MyApplication).repository)
        ViewModelProvider(this, factory).get(InfoViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Details")
        builder.setPositiveButton("OK"){ dialog, id ->
            dismiss()
        }

        arguments?.getInt(Companion.KEY_DATA_INDEX)?.let { index ->

            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.dialog_details, null, false)
            val listView =  view.findViewById<ListView>(R.id.details_list)
            viewModel.getDetails(index).observe(this, Observer {
                listView.adapter = InfoAdapter(context!!, it)
            })
            builder.setView(view)
        }

        return builder.create()
    }

    class InfoAdapter(
        context: Context,
        data: List<Pair<String,String>>
    ) : ArrayAdapter<Pair<String,String>>(context, 0, data){
        private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: inflater.inflate(R.layout.cell_info, parent, false)
            val data = getItem(position)
            view.findViewById<TextView>(R.id.info_key).text = data?.first
            view.findViewById<TextView>(R.id.info_value).text = data?.second
            return view
        }
    }

    companion object {
        const val KEY_DATA_INDEX = "bundle_key_index"
    }
}
