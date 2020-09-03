package galaxysoftware.wordbook.fragment

import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import galaxysoftware.wordbook.BaseFragment
import galaxysoftware.wordbook.R
import galaxysoftware.wordbook.realm.WordObject
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_wordview.*

/**
 * Use the [WordViewFragment] factory method to
 * create an instance of this fragment.
 */
class WordViewFragment : BaseFragment() {

    lateinit var word: String

    override fun initialize() {
        arguments?.let { word = WordViewFragmentArgs.fromBundle(it).word }
        Realm.getDefaultInstance().where(WordObject::class.java).equalTo("word", word).findFirst()!!.apply {
            wordField.text = this.word
            meanField.text = this.means.joinToString(", ")
            descriptionFiled.text = this.note
        }
    }

    override fun getLayoutId() = R.layout.fragment_wordview

    override fun updateFragment() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        findNavController().navigate(WordViewFragmentDirections.actionWordViewFragmentToEditorFragment(word))
        return super.onOptionsItemSelected(item)
    }
}