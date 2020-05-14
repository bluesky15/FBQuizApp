package com.lkb.fbquizapp.view.result

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lkb.fbquizapp.BaseActivity
import com.lkb.fbquizapp.R
import com.lkb.fbquizapp.model.persistance.AppDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ResultActivity: BaseActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var viewModel: ResultViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_activity)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerViewAdapter()
        recyclerView.adapter = adapter
        val db = AppDatabase.getInstance(this)
        db?.let {
            viewModel = ViewModelProvider(
                this,
                ResultViewModelFactory(it)
            ).get(ResultViewModel::class.java)
        }
        viewModel.getTopResults()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe { e -> adapter.bindData(e) }
    }
}