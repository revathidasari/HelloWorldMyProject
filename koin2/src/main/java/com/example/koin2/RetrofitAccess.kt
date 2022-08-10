package com.example.koin2

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koin2.model.POJO
import com.example.koin2.viewmodel.RetrofitViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.dsl.module.applicationContext

class RetrofitAccess : AppCompatActivity() {

    private var retrofitViewModel = RetrofitViewModel()
    var recyclerView:RecyclerView? = null
    private var recycledAdapterViews : RecycledAdapterViews?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView!!.layoutManager = LinearLayoutManager(this)
        retrofitViewModel = ViewModelProvider(this)[RetrofitViewModel::class.java]
        retrofitViewModel.getLivePojo().observe(this,{
            it.let { datalist ->
                recycledAdapterViews= RecycledAdapterViews(this,datalist)
                recyclerView?.adapter = recycledAdapterViews
            }
        })
    }
}

private class RecycledAdapterViews(val context : Context, val items:List<POJO>) :
    RecyclerView.Adapter<RecycledAdapterViews.CustomViewHolder>() {
  //  RecyclerView.Adapter<RecycledAdapterViews.CustomViewHolder> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycledAdapterViews.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item,parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecycledAdapterViews.CustomViewHolder, position: Int) {
      holder.text.text = items.get(position).getName()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text = itemView.findViewById<TextView>(R.id.itemText)
    }
}