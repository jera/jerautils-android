package br.com.jera.example.paginations

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.jera.example.R
import br.com.jera.jerautils.paginations.Paginator
import br.com.jera.jerautils.paginations.adapters.BaseRecyclerViewAdapter
import br.com.jera.jerautils.paginations.interfaces.DataSource
import br.com.jera.jerautils.paginations.interfaces.DataSourceCallback
import br.com.jera.jerautils.paginations.interfaces.PaginationInfo
import java.util.*

class PaginationExample : AppCompatActivity() {

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

    class AnyAdapter : BaseRecyclerViewAdapter<Thing> {

        private var data: ArrayList<Thing>

        constructor() {
            this.data = ArrayList<Thing>()
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AnyViewHolder {
            return AnyViewHolder(LayoutInflater.from(parent?.context).inflate(android.R.layout.simple_list_item_1, parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            if (holder is AnyViewHolder) {
                holder.format(data[position])
            }
        }

        override fun getItemCount(): Int {
            return this.data.size
        }

        override fun addItems(items: List<Thing>?) {
            if (items != null) {
                data.addAll(items)
                notifyDataSetChanged()
            }
        }
    }

    class AnyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun format(thing: Thing) {
            with(itemView as TextView) {
                text = thing.name
            }
        }
    }

    class FakeRequesterDataSource : DataSource<Thing> {
        override fun fetchData(page: Int, pageSize: Int?, callback: DataSourceCallback<Thing>?) {
            Handler().postDelayed({
                val paginatedThings = request(page, pageSize ?: 10)
                callback?.onSuccess(paginatedThings.things, paginatedThings.paginationInfo)
            }, 1500)
        }

        fun request(page: Int, requestedAmount: Int): PaginatedThings {
            val offset = if ((page - 1) * requestedAmount > things.size) things.size else (page - 1) * requestedAmount
            val amount = if ((offset + requestedAmount) > things.size) things.size - offset else requestedAmount
            val pagedThings = ArrayList(things.subList(offset, offset + amount))
            return PaginatedThings(pagedThings, createPaginationInfo(page, requestedAmount, things))
        }

        fun createPaginationInfo(page: Int, amount: Int, source: List<Any>): PaginationInfo {
            return object : PaginationInfo {
                override fun getCurrentPage(): Int {
                    return page
                }

                override fun getNextPage(): Int {
                    return page + 1
                }

                override fun getTotalItens(): Int {
                    return source.size
                }

                override fun getTotalPages(): Int {
                    return source.size / amount
                }
            }
        }
    }

    companion object {
        private val things: ArrayList<Thing> = ArrayList((1..100).map { Thing("Thing $it") })
    }

    data class PaginatedThings(val things: ArrayList<Thing>, val paginationInfo: PaginationInfo)
    data class Thing(val name: String)
}