package galaxysoftware.wordbook.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import galaxysoftware.wordbook.BaseFragment
import galaxysoftware.wordbook.R
import galaxysoftware.wordbook.adapter.AddMeanAdapter
import galaxysoftware.wordbook.entity.request.BaseRequest
import galaxysoftware.wordbook.realm.WordObject
import io.realm.Realm
import kotlinx.android.synthetic.main.editor_fragment.*
import kotlin.collections.ArrayList

/**
 * Use the [EditorFragment] factory method to
 * create an instance of this fragment.
 */
class EditorFragment : BaseFragment() {

    private var word: String? = null

    private var means = ArrayList<String>()

    private lateinit var adapter: AddMeanAdapter<String>

    override fun initialize() {
        word = arguments.let { EditorFragmentArgs.fromBundle(it!!).word }
        if (word != null) {
            Realm.getDefaultInstance().where(WordObject::class.java).equalTo("word", word).findFirst()?.apply {
                wordField.setText(this.word)
                this@EditorFragment.means.addAll(means)
                descriptionFiled.setText(this.note)
            }
        }
        adapter = AddMeanAdapter(means)
        meanList.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = this@EditorFragment.adapter
        }
        addMean.setOnClickListener {
            means.add(meanField.text.toString())
            adapter.updateItems(means)
        }
        updateToolbar("")
    }

    override fun getLayoutId() = R.layout.editor_fragment

    override fun updateFragment() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.editor, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add) {
            if (word == null)
                BaseRequest.instance.add(0, wordField.text.toString(), means, descriptionFiled.text.toString()) { success, msg ->
                    if (success) {

                    } else {

                    }
                }
            else
                BaseRequest.instance.update(wordField.text.toString(), means, descriptionFiled.text.toString()) { success, msg ->

                }
            return super.onOptionsItemSelected(item)
        }
        return true
    }
}
