package galaxysoftware.wordbook.fragment

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import galaxysoftware.wordbook.BaseFragment

import galaxysoftware.wordbook.R
import galaxysoftware.wordbook.adapter.WordListViewAdapter
import galaxysoftware.wordbook.callback.SwipeCallback
import galaxysoftware.wordbook.callback.WordSelectedListener
import galaxysoftware.wordbook.viewModel.WordListViewModel
import kotlinx.android.synthetic.main.word_list_view_fragment.*

class WordListViewFragment : BaseFragment(), WordSelectedListener, SwipeCallback {

    private lateinit var adapter: WordListViewAdapter

    private lateinit var viewModel: WordListViewModel

    private lateinit var dictionaryName: String

    override fun initialize() {
        arguments.let { dictionaryName = WordListViewFragmentArgs.fromBundle(it!!).name }
        viewModel = ViewModelProviders.of(this).get(WordListViewModel::class.java)
        adapter = WordListViewAdapter(viewModel.words, this)
        wordList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@WordListViewFragment.adapter
        }
    }

    override fun getLayoutId() = R.layout.dictionary_tab_fragment

    override fun updateFragment() {

    }

    override fun onClick(word: String, index: Int) = findNavController().navigate(DictionaryTabFragmentDirections.actionDictionaryTabToWordListViewFragment(word))

    override fun onRefreshComplete() {

    }
}
