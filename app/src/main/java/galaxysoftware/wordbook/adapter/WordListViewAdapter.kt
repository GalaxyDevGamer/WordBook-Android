package galaxysoftware.wordbook.adapter

import galaxysoftware.wordbook.R
import galaxysoftware.wordbook.callback.WordSelectedListener
import galaxysoftware.wordbook.model.Word


class WordListViewAdapter(items: ArrayList<Word>, private val listener: WordSelectedListener) : BaseAdapter<Word>(items) {

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