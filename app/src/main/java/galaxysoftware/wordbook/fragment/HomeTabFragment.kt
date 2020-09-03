package galaxysoftware.wordbook.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import galaxysoftware.wordbook.BaseFragment
import galaxysoftware.wordbook.R
import galaxysoftware.wordbook.adapter.HomeTabAdapter
import galaxysoftware.wordbook.callback.WordSelectedListener
import galaxysoftware.wordbook.viewModel.HomeTabModel
import kotlinx.android.synthetic.main.home_tab_fragment.*

class HomeTabFragment : BaseFragment(), WordSelectedListener {

    private lateinit var viewModel: HomeTabModel

    private lateinit var adapter: HomeTabAdapter

    override fun initialize() {
        viewModel = ViewModelProviders.of(this).get(HomeTabModel::class.java)
        adapter = HomeTabAdapter(viewModel.words, this)
        list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@HomeTabFragment.adapter
        }
        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                searchWord(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                searchWord(query)
                return false
            }

        })
        updateToolbar("Home")
    }

    override fun getLayoutId() = R.layout.home_tab_fragment

    override fun updateFragment() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> findNavController().navigate(HomeTabFragmentDirections.actionHomeTabToEditorFragment(null))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(word: String, index: Int) {
        viewModel.addToHistory(index)
        findNavController().navigate(HomeTabFragmentDirections.actionHomeTabToWordViewFragment(word))
    }

    fun searchWord(query: String) = adapter.updateItems(viewModel.search(query))

    fun clear() = adapter.clear()

    companion object {

        @JvmStatic
        fun newInstance() = HomeTabFragment()
    }
}
