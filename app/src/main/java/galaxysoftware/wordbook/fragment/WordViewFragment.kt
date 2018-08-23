package galaxysoftware.wordbook.fragment

import android.os.Bundle
import android.view.MenuItem
import galaxysoftware.wordbook.BaseFragment
import galaxysoftware.wordbook.R
import galaxysoftware.wordbook.realm.Words
import galaxysoftware.wordbook.type.FragmentType
import galaxysoftware.wordbook.type.NavigationType
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_wordview.*

/**
 * Use the [WordViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WordViewFragment : BaseFragment() {

    override fun initialize() {
        arguments?.let {
            arg = it.getString(BUNDLE_KEY_OBJECT)
        }
        val data = Realm.getDefaultInstance().where(Words::class.java).equalTo("word", arg).findFirst()
        wordField.text = data?.word
        meanField.text = data?.mean
        eikenField.text = data?.eiken
        TOEICField.text = data?.TOEIC.toString()
        schoolField.text = data?.schoolLevel
        descriptionFiled.text = data?.note
    }

    override fun getLayoutId() = R.layout.fragment_wordview

    override fun updateFragment() {

    }

    private var arg: String? = null

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        updateToolbar(FragmentType.EDIT, NavigationType.BACK, "", R.menu.editer)
        requestChangeFragment(FragmentType.EDIT, wordField.text)
        return super.onOptionsItemSelected(item)
    }

    companion object {
        var BUNDLE_KEY_OBJECT = "bundle_key_object"

        @JvmStatic
        fun newInstance(any: Any) =
                WordViewFragment().apply {
                    arguments = Bundle().apply {
                        putString(BUNDLE_KEY_OBJECT, any as String)
                    }
                }
    }
}