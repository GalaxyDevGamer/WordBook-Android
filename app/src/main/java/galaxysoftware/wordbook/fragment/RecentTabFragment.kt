package galaxysoftware.wordbook.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import galaxysoftware.wordbook.BaseFragment
import galaxysoftware.wordbook.R
import galaxysoftware.wordbook.adapter.RecentTabAdapter
import galaxysoftware.wordbook.callback.SwipeCallback
import galaxysoftware.wordbook.callback.WordSelectedListener
import galaxysoftware.wordbook.viewModel.RecentTabModel
import kotlinx.android.synthetic.main.fragment_word_to_study_tab.*

class RecentTabFragment : BaseFragment(), WordSelectedListener, SwipeCallback {

    private lateinit var recentTabAdapter: RecentTabAdapter

    private lateinit var viewModel: RecentTabModel

    override fun initialize() {
        viewModel = ViewModelProviders.of(this).get(RecentTabModel::class.java)
        recentTabAdapter = RecentTabAdapter(items = viewModel.words, listener = this)
        wordList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recentTabAdapter
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
        findNavController().navigate(RecentTabFragmentDirections.actionRecentTabToWordViewFragment(word))
    }

    override fun onRefreshComplete() {
        swipeRefresh.isRefreshing = false
    }

    companion object {

        @JvmStatic
        fun newInstance() = RecentTabFragment()
    }
}
