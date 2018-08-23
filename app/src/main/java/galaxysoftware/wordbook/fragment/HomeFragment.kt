package galaxysoftware.wordbook.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import galaxysoftware.wordbook.BaseFragment
import galaxysoftware.wordbook.R
import galaxysoftware.wordbook.adapter.HomeAdapter
import galaxysoftware.wordbook.callback.SwipeCallback
import galaxysoftware.wordbook.callback.WordSelectedListener
import galaxysoftware.wordbook.type.FragmentType
import galaxysoftware.wordbook.type.NavigationType
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), WordSelectedListener, SwipeCallback {
    private lateinit var homeAdapter: HomeAdapter

    override fun initialize() {
        homeAdapter = HomeAdapter(this, this)
        wordList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
        }
        swipeRefresh.setOnRefreshListener {
            homeAdapter.update()
        }
    }

    override fun getLayoutId() = R.layout.fragment_home


    override fun updateFragment() {

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.add -> {
                updateToolbar(FragmentType.EDIT, NavigationType.BACK, "", R.menu.editer)
                requestChangeFragment(FragmentType.EDIT, "")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(word: String) {
        updateToolbar(FragmentType.VIEW, NavigationType.BACK, word, R.menu.view)
        requestChangeFragment(FragmentType.VIEW, word)
    }

    override fun onRefreshComplete() {
        swipeRefresh.isRefreshing = false
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
