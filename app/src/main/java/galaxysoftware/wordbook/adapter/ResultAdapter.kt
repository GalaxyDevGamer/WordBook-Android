package galaxysoftware.wordbook.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import galaxysoftware.wordbook.R
import galaxysoftware.wordbook.callback.WordSelectedListener

import galaxysoftware.wordbook.realm.Words
import io.realm.Realm
import io.realm.RealmResults

import kotlinx.android.synthetic.main.fragment_word.view.*

class ResultAdapter(private val listener: WordSelectedListener) : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    private var results: RealmResults<Words> = Realm.getDefaultInstance().where(Words::class.java).equalTo("word", "").findAll()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_word, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = results[position]
        holder.wordView.text = item!!.word
        holder.meanView.text = item.mean
        holder.itemView.setOnClickListener {
            listener.onClick(item.word!!)
        }
    }

    override fun getItemCount() = results.size

    fun search(word: String) {
        results = Realm.getDefaultInstance().where(Words::class.java).beginsWith("word", word).findAll()
        notifyDataSetChanged()
    }

    fun clear() {
        results = Realm.getDefaultInstance().where(Words::class.java).equalTo("word", "").findAll()
        notifyDataSetChanged()
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val wordView: TextView = mView.wordView
        val meanView: TextView = mView.meanView
    }
}