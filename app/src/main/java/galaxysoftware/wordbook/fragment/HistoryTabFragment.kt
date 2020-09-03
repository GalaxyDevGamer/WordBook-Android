package galaxysoftware.wordbook.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import galaxysoftware.wordbook.BaseFragment
import galaxysoftware.wordbook.R
import galaxysoftware.wordbook.adapter.HistoryTabAdapter
import galaxysoftware.wordbook.callback.WordSelectedListener
import galaxysoftware.wordbook.viewModel.HistoryTabModel
import kotlinx.android.synthetic.main.fragment_word_to_study_tab.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [WordSelectedListener] interface.
 */
class HistoryTabFragment : BaseFragment(), WordSelectedListener {

    private lateinit var adapter: HistoryTabAdapter

    private lateinit var viewModel: HistoryTabModel

    override fun initialize() {
        viewModel = ViewModelProviders.of(this).get(HistoryTabModel::class.java)
        adapter = HistoryTabAdapter(items = viewModel.words, listener = this)
        wordList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@HistoryTabFragment.adapter
        }
        swipeRefresh.setOnRefreshListener {
            viewModel.loadWords()
        }
    }

    override fun getLayoutId() = R.layout.fragment_history_tab

    override fun updateFragment() {

    }

    override fun onClick(word: String, index: Int) {
        viewModel.addToHistory(index)
        findNavController().navigate(HistoryTabFragmentDirections.actionHistoryTabToWordViewFragment(word))
    }
}
