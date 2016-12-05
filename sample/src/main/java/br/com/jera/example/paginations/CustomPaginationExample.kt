package br.com.jera.example.paginations

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.jera.example.R
import br.com.jera.jerautils.paginations.Paginator
import br.com.jera.jerautils.paginations.adapters.PaginationViewProvider
import br.com.jera.jerautils.paginations.interfaces.PaginationError

class CustomPaginationExample : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    private lateinit var paginator: Paginator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagiantion_example)
        recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AnyAdapter()
        paginator = Paginator.with(FakeRequesterDataSource())
                .fromPage(1)
                .withPageSize(20)
                .over(recyclerView)
                .usingPaginationViewProvider(object : PaginationViewProvider<LoadingViewHolder, ErrorViewHolder> {
                    override fun onCreateLoadingViewHolder(parent: ViewGroup?): LoadingViewHolder {
                        return LoadingViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.my_loading_view, parent, false))
                    }

                    override fun onCreateErrorViewHolder(parent: ViewGroup?): ErrorViewHolder {
                        return ErrorViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.my_error_view, parent, false))
                    }

                    override fun onBindLoadingViewHolder(holder: LoadingViewHolder?) {

                    }

                    override fun onBindErrorViewHolder(holder: ErrorViewHolder?, error: PaginationError?, errorCallback: Paginator.TryAgainCallback?) {
                        holder?.format(error, errorCallback)
                    }

                })
                .start()
    }

    class LoadingViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    class ErrorViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun format(error: PaginationError?, errorCallback: Paginator.TryAgainCallback?) {
            itemView.findViewById(R.id.try_again).setOnClickListener { errorCallback?.tryAgain() }
        }
    }
}