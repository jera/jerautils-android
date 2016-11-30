package br.com.jera.example.paginations

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import br.com.jera.example.R
import br.com.jera.jerautils.paginations.adapters.BasePaginatedRecyclerViewAdapter

class PaginationExample : AppCompatActivity() {

    lateinit var recyclerVier: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagiantion_example)
        recyclerVier = findViewById(R.id.recyclerView) as RecyclerView
        recyclerVier.layoutManager = LinearLayoutManager(this)
        recyclerVier.adapter = BasePaginatedRecyclerViewAdapter()
    }
}
