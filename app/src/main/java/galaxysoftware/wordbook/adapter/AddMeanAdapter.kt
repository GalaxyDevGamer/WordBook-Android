package galaxysoftware.wordbook.adapter

import galaxysoftware.wordbook.R


class AddMeanAdapter<String>(items: ArrayList<String>) : BaseAdapter<String>(items) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.wordView.text = item.toString()
    }

    override fun getCellLayout() = R.layout.wordcell_fragment
}