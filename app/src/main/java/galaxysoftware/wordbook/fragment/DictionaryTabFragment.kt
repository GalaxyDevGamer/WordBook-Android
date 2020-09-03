package galaxysoftware.wordbook.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import galaxysoftware.wordbook.BaseFragment

import galaxysoftware.wordbook.R
import galaxysoftware.wordbook.adapter.DictionaryTabAdapter
import galaxysoftware.wordbook.viewModel.DictionaryTabModel
import kotlinx.android.synthetic.main.dictionary_tab_fragment.*

class DictionaryTabFragment : BaseFragment() {

    private lateinit var adapter: DictionaryTabAdapter

    private lateinit var viewModel: DictionaryTabModel

    override fun initialize() {
        viewModel = ViewModelProviders.of(this).get(DictionaryTabModel::class.java)
        adapter = DictionaryTabAdapter(viewModel.dictionaries, this)
        list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@DictionaryTabFragment.adapter
        }
    }

    override fun getLayoutId() = R.layout.dictionary_tab_fragment

    override fun updateFragment() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                AlertDialog.Builder(this.context!!).setTitle("Add Dictionary").create().show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(word: String, index: Int) = findNavController().navigate(DictionaryTabFragmentDirections.actionDictionaryTabToWordListViewFragment(word))
}
