package galaxysoftware.wordbook.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import kotlinx.android.synthetic.main.wordcell_fragment.view.*

abstract class BaseAdapter<T>(items: ArrayList<T>) : RecyclerView.Adapter<BaseAdapter<T>.ViewHolder>() {

    var list = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(getCellLayout(), parent, false))

    override fun getItemCount() = list.size

    fun updateItems(items: ArrayList<T>) {
        list = items
        this.notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        this.notifyDataSetChanged()
    }

    abstract fun getCellLayout() : Int

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val wordView: TextView = mView.wordView
        val meanView: TextView = mView.meanView
    }
}