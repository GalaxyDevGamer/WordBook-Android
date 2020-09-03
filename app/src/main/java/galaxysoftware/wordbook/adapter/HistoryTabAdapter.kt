package galaxysoftware.wordbook.adapter

import androidx.recyclerview.widget.RecyclerView
import galaxysoftware.wordbook.R
import galaxysoftware.wordbook.callback.WordSelectedListener

import galaxysoftware.wordbook.model.Word

/**
 * [RecyclerView.Adapter] that can display a [Word] and makes a call to the
 * specified [WordSelectedListener].
 * TODO: Replace the implementation with code for your data type.
 */
class HistoryTabAdapter(items: ArrayList<Word>, private val listener: WordSelectedListener)
    : BaseAdapter<Word>(items) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.wordView.text = item.word
        holder.meanView.text = item.means.joinToString(", ")
        holder.itemView.setOnClickListener {
            listener.onClick(item.word, position)
        }
    }

    override fun getCellLayout() = R.layout.wordcell_fragment
}
