package galaxysoftware.wordbook.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import galaxysoftware.wordbook.R
import galaxysoftware.wordbook.callback.SwipeCallback
import galaxysoftware.wordbook.callback.WordSelectedListener

import galaxysoftware.wordbook.realm.Words
import io.realm.Realm
import io.realm.RealmResults

import kotlinx.android.synthetic.main.fragment_word.view.*

class HomeAdapter(private val listener: WordSelectedListener, private val callback: SwipeCallback) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var data: RealmResults<Words> = Realm.getDefaultInstance().where(Words::class.java).findAll()

    init {
        if (data.count() == 0) {
            FirebaseFirestore.getInstance().collection("words").get().addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document: DocumentSnapshot in it.result) {
                        val local = Words()
                        local.word = document.data!!["word"].toString()
                        local.word = document.data!!["word"].toString()
                        local.mean = document.data!!["mean"].toString()
                        local.note = document.data!!["description"].toString()
                        local.eiken = document.data!!["eiken"].toString()
                        local.TOEIC = document.data!!["TOEIC"].toString().toInt()
                        local.schoolLevel = document.data!!["schoolLevel"].toString()
                        Realm.getDefaultInstance().executeTransaction {
                            it.insertOrUpdate(local)
                        }
                    }
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_word, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.wordView.text = item?.word
        holder.meanView.text = item?.mean
        holder.itemView.setOnClickListener {
            listener.onClick(item?.word!!)
        }
    }

    fun update() {
        FirebaseFirestore.getInstance().collection("words").get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (document in it.result) {
                    val data = Words()
                    data.id = document.data["id"].toString().toInt()
                    data.word = document.data["word"].toString()
                    data.mean = document.data["mean"].toString()
                    data.note = document.data["description"].toString()
                    data.eiken = document.data["eiken"].toString()
                    data.TOEIC = document.data["TOEIC"].toString().toInt()
                    data.schoolLevel = document.data["schoolLevel"].toString()
                    Realm.getDefaultInstance().executeTransaction {
                        it.insertOrUpdate(data)
                    }
                }
                notifyDataSetChanged()
            }
            callback.onRefreshComplete()
        }
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val wordView: TextView = mView.wordView
        val meanView: TextView = mView.meanView
    }
}