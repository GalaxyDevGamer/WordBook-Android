package galaxysoftware.wordbook.fragment

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import galaxysoftware.wordbook.BaseFragment
import galaxysoftware.wordbook.R
import galaxysoftware.wordbook.adapter.WordToStudyTabAdapter
import galaxysoftware.wordbook.viewModel.WordToStudyTabModel
import kotlinx.android.synthetic.main.fragment_word_to_study_tab.*

class WordToStudyTabFragment : BaseFragment() {

    private lateinit var adapter: WordToStudyTabAdapter

    private lateinit var viewModel: WordToStudyTabModel

    override fun initialize() {
        viewModel = ViewModelProviders.of(this).get(WordToStudyTabModel::class.java)
        adapter = WordToStudyTabAdapter(items = viewModel.words, listener = this)
        wordList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@WordToStudyTabFragment.adapter
        }
        swipeRefresh.setOnRefreshListener {
            viewModel.loadWords()
        }
    }

    override fun getLayoutId() = R.layout.fragment_word_to_study_tab

    override fun updateFragment() {

    }

    override fun onClick(word: String, index: Int) {
        viewModel.addToHistory(index)
        findNavController().navigate(WordToStudyTabFragmentDirections.actionWordToStudyTabToWordViewFragment(word))
    }
}