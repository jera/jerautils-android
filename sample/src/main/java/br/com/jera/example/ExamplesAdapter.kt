package br.com.jera.example

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ExamplesAdapter : RecyclerView.Adapter<ExamplesAdapter.ExamplesViewHolder> {

    private var actionCallback: (Class<out Activity>) -> Unit
    private var examples: List<Pair<String, Class<out Activity>>>

    constructor(examples: List<Pair<String, Class<out Activity>>>, action: (Class<out Activity>) -> Unit) {
        this.examples = examples
        this.actionCallback = action
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ExamplesViewHolder {
        return ExamplesViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_example, parent, false))
    }

    override fun onBindViewHolder(holder: ExamplesViewHolder?, position: Int) {
        holder?.format(examples[position].first, examples[position].second, actionCallback)
    }

    override fun getItemCount(): Int {
        return examples.count()
    }

    class ExamplesViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun format(name: String, activityClass: Class<out Activity>, action: (Class<out Activity>) -> Unit) {
            with(itemView.findViewById(R.id.name_text_view) as TextView) {
                text = name
                setOnClickListener { action(activityClass) }
            }
        }
    }
}