package br.com.jera.example.paginations

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

/**
 * Created by daividsilverio on 02/12/16.
 */
class AnyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun format(thing: Thing) {
        with(itemView as TextView) {
            text = thing.name
        }
    }
}