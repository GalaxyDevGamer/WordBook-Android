package galaxysoftware.wordbook.adapter

import androidx.recyclerview.widget.RecyclerView
import galaxysoftware.wordbook.R
import galaxysoftware.wordbook.callback.WordSelectedListener
import galaxysoftware.wordbook.model.Dictionary

import galaxysoftware.wordbook.model.Word

/**
 * [RecyclerView.Adapter] that can display a [Word] and makes a call to the
 * specified [WordSelectedListener].
 * TODO: Replace the implementation with code for your data type.
 */
class DictionaryTabAdapter(items: ArrayList<Dictionary>, private val listener: WordSelectedListener)
    : BaseAdapter<Dictionary>(items) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.wordView.text = item.name
        holder.itemView.setOnClickListener {
            listener.onClick(item.name, position)
        }
    }

    override fun getCellLayout() = R.layout.wordcell_fragment
}
