package galaxysoftware.wordbook.fragment

import android.os.Bundle
import android.view.MenuItem
import com.google.firebase.firestore.FirebaseFirestore
import galaxysoftware.wordbook.BaseFragment
import galaxysoftware.wordbook.R
import galaxysoftware.wordbook.realm.Words
import galaxysoftware.wordbook.type.NavigationType
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_editer.*
import java.util.*

/**
 * Use the [EditorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditorFragment : BaseFragment() {

    private var word = ""

    override fun initialize() {
        if (arguments != null) {
            word = arguments?.getString(BUNDLE_KEY_OBJECT)!!
            val data = Realm.getDefaultInstance().where(Words::class.java).equalTo("word", word).findFirst()
            wordField.setText(data!!.word)
            meanField.setText(data.mean)
            eikenField.setText(data.eiken)
            TOEICField.setText(data.TOEIC.toString())
            schoolField.setText(data.schoolLevel)
            descriptionFiled.setText(data.note)
        }
    }

    override fun getLayoutId() = R.layout.fragment_editer

    override fun updateFragment() {

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val db = FirebaseFirestore.getInstance()
        val data = HashMap<String, Any>()
        data["word"] = wordField.text.toString()
        data["mean"] = meanField.text.toString()
        data["eiken"] = eikenField.text.toString()
        data["TOEIC"] = TOEICField.text.toString().toInt()
        data["schoolLevel"] = schoolField.text.toString()
        data["description"] = descriptionFiled.text.toString()
        db.collection("words").document(wordField.text.toString()).set(data).addOnCompleteListener {
            if (it.isSuccessful) {
                val word = Words()
                word.word = wordField.text.toString()
                word.mean = meanField.text.toString()
                word.eiken = eikenField.text.toString()
                word.TOEIC = TOEICField.text.toString().toInt()
                word.schoolLevel = schoolField.text.toString()
                word.note = descriptionFiled.text.toString()
                Realm.getDefaultInstance().executeTransaction {
                    it.insertOrUpdate(word)
                }
            }
            backFragment()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val BUNDLE_KEY_OBJECT = "bundle_key_object"

        @JvmStatic
        fun newInstance(any: Any) = EditorFragment().apply {
            arguments = Bundle().apply {
                putString(BUNDLE_KEY_OBJECT, any as String)
            }
        }
    }
}
