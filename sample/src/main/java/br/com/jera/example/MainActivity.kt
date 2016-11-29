package br.com.jera.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.jera.example.MainActivity.Companion.examples
import br.com.jera.example.dialogs.DialogsExample
import br.com.jera.example.placeholders.PlaceholdersExample

class MainActivity : AppCompatActivity() {
    companion object {
        val examples = listOf<Pair<String, Class<out Activity>>>(
                "Placeholders" to PlaceholdersExample::class.java,
                "Dialogs" to DialogsExample::class.java
        )
    }

    lateinit var itemsList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemsList = findViewById(R.id.items_list) as RecyclerView
        setupExamplesList()
    }

    private fun setupExamplesList() {
        itemsList.layoutManager = LinearLayoutManager(this)
        itemsList.adapter = ExamplesAdapter { openExample(it) }
    }

    private fun openExample(activityClass: Class<out Activity>) {
        startActivity(Intent(this, activityClass))
    }
}

class ExamplesAdapter : RecyclerView.Adapter<ExamplesViewHolder> {

    private var actionCallback: (Class<out Activity>) -> Unit

    constructor(action: (Class<out Activity>) -> Unit) {
        actionCallback = action
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

}

class ExamplesViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    fun format(name: String, activityClass: Class<out Activity>, action: (Class<out Activity>) -> Unit) {
        with(itemView.findViewById(R.id.name_text_view) as TextView) {
            text = name
            setOnClickListener { action(activityClass) }
        }
    }
}
