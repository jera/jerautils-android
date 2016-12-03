package br.com.jera.example.paginations

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import br.com.jera.example.R
import br.com.jera.jerautils.paginations.Paginator

class DefaultPaginationExample : AppCompatActivity() {

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
                .start()
    }
}