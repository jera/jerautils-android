package br.com.jera.example.paginations

import android.R
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.jera.jerautils.paginations.adapters.PaginatedAdapter
import java.util.*

/**
 * Created by daividsilverio on 02/12/16.
 */
class AnyAdapter : RecyclerView.Adapter<AnyViewHolder>, PaginatedAdapter<Thing> {


    override fun onBindViewHolder(holder: AnyViewHolder?, position: Int) {
        holder?.format(data[position])
    }

    private var data: ArrayList<Thing>

    constructor() {
        this.data = ArrayList<Thing>()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AnyViewHolder {
        return AnyViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.simple_list_item_1, parent, false))
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