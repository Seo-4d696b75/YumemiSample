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
import kotlin.collections.ArrayList

/**
 * @author Seo-4d696b75
 * @version 2020/11/25.
 * This dialog show details about the given account.
 * Info of the account is passed as Bundle
 */
class InfoDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Details")
        builder.setPositiveButton("OK"){ dialog, id ->
            dismiss()
        }

        val data : List<Bundle>? = arguments?.getParcelableArrayList(Companion.KEY_DATA)
        if ( data != null && context != null ){
            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.dialog_details, null, false)
            val list = ArrayList<Pair<String,String>>()
            for ( d in data ){
                list.add(Pair<String,String>(d.getString("key", ""), d.getString("value","")))
            }
            view.findViewById<ListView>(R.id.details_list).adapter = InfoAdapter(context!!, list)
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
        const val KEY_DATA = "bundle_key_data"
    }
}
