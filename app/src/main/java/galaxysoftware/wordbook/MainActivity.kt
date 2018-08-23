package galaxysoftware.wordbook

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.View
import galaxysoftware.wordbook.callback.ChangeFragmentListener
import galaxysoftware.wordbook.fragment.HomeFragment
import galaxysoftware.wordbook.fragment.SearchFragment
import galaxysoftware.wordbook.helper.FragmentMakeHelper
import galaxysoftware.wordbook.type.FragmentType
import galaxysoftware.wordbook.type.NavigationType
import galaxysoftware.wordbook.type.TabType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ChangeFragmentListener {
    private var currentTabType = TabType.HOME

    private val fragmentTypeHistory = HashMap<TabType, ArrayList<FragmentType>>()
    private val dataContainer = HashMap<FragmentType, Any>()
    private val tabHistory = HashMap<TabType, ArrayList<BaseFragment>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ContextData.getInstance().activity = this
        ContextData.getInstance().mainActivity = this@MainActivity
        setSupportActionBar(toolbar)
        initVariable()
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeTab -> {
                    this@MainActivity.changeTab(TabType.HOME)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.searchTab -> {
                    this@MainActivity.changeTab(TabType.SEARCH)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
        changeFirstFragment()
    }

    private fun initVariable() {
        tabHistory[TabType.HOME] = ArrayList()
        tabHistory[TabType.SEARCH] = ArrayList()
        tabHistory[TabType.HOME]?.add(HomeFragment.newInstance())
        tabHistory[TabType.SEARCH]?.add(SearchFragment.newInstance())
        fragmentTypeHistory[TabType.HOME] = ArrayList()
        fragmentTypeHistory[TabType.SEARCH] = ArrayList()
        fragmentTypeHistory[TabType.HOME]?.add(FragmentType.HOME_TAB)
        fragmentTypeHistory[TabType.SEARCH]?.add(FragmentType.SEARCH_TAB)
        setTabData(FragmentType.HOME_TAB, NavigationType.NONE, getString(R.string.home), R.menu.home)
        setTabData(FragmentType.SEARCH_TAB, NavigationType.NONE, getString(R.string.search), R.menu.search)
    }

    /**
     * Set the data for each tab.
     */
    private fun setTabData(fragmentType: FragmentType, navigationType: NavigationType, title: String, menu: Int) {
        val data = HashMap<String, Any>()
        data["nav"] = navigationType
        data["title"] = title
        data["menu"] = menu
        dataContainer[fragmentType] = data
    }

    private fun changeFirstFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.homeTabContainer, tabHistory[TabType.HOME]!![0])
            replace(R.id.searchTabContainer, tabHistory[TabType.SEARCH]!![0])
            commit()
        }
        changeTab(TabType.HOME)
    }

    private fun changeTab(tabType: TabType) {
        currentTabType = tabType
        updateToolbar()
        homeTabContainer.visibility = if (currentTabType == TabType.HOME) View.VISIBLE else View.INVISIBLE
        searchTabContainer.visibility = if (currentTabType == TabType.SEARCH) View.VISIBLE else View.INVISIBLE
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        val data = dataContainer[fragmentTypeHistory[currentTabType]!![fragmentTypeHistory[currentTabType]!!.size - 1]] as HashMap<String, Any>
        menuInflater.inflate(data["menu"] as Int, menu)
        if (fragmentTypeHistory[currentTabType]!![fragmentTypeHistory[currentTabType]!!.size - 1] == FragmentType.SEARCH_TAB) {
            val searchFragment = tabHistory[TabType.SEARCH]!![tabHistory[TabType.SEARCH]!!.size-1] as SearchFragment
            val searchBar = menu?.findItem(R.id.search)?.actionView as SearchView
            searchBar.setIconifiedByDefault(false)
            searchBar.clearFocus()
            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    if (p0.isNullOrEmpty()) {
                        searchFragment.clear()
                        return false
                    }
                    searchFragment.searchWord(p0!!)
                    return false
                }
            })
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onChangeFragment(fragmentType: FragmentType, any: Any) = changeFragment(fragmentType, any)

    private fun changeFragment(fragmentType: FragmentType, any: Any) {
        if (fragmentType == FragmentType.EDIT)
            bottom_navigation.visibility = View.GONE
        val fragment = FragmentMakeHelper.makeFragment(fragmentType, any)
        tabHistory[currentTabType]?.add(fragment)
        replaceFragment(fragment)
        invalidateOptionsMenu()
    }

    private fun getCurrentFragment() = tabHistory[currentTabType]!![tabHistory[currentTabType]!!.size - 1]

    /**
     * Replacing Fragment
     */
    private fun replaceFragment(fragment: BaseFragment) = supportFragmentManager.beginTransaction().apply {
        when (currentTabType) {
            TabType.HOME -> replace(R.id.homeTabContainer, fragment)
            TabType.SEARCH -> replace(R.id.searchTabContainer, fragment)
        }
        commit()
    }

    fun backFragment() {
        if (fragmentTypeHistory[currentTabType]!![fragmentTypeHistory[currentTabType]!!.size-1] == FragmentType.EDIT)
            bottom_navigation.visibility = View.VISIBLE
        tabHistory[currentTabType]!!.removeAt(tabHistory[currentTabType]!!.size - 1)
        fragmentTypeHistory[currentTabType]!!.removeAt(fragmentTypeHistory[currentTabType]!!.size - 1)
        replaceFragment(getCurrentFragment())
        updateToolbar()
    }

    override fun onBackPressed() {
        if (currentTabType == TabType.HOME && tabHistory[TabType.HOME]!!.size == 1) {
            finish()
            return
        }
        if (tabHistory[currentTabType]!!.size == 1) {
            changeTab(TabType.HOME)
            return
        }
        bottom_navigation.selectedItemId = when(currentTabType) {
            TabType.HOME -> R.id.homeTab
            TabType.SEARCH -> R.id.searchTab
        }
        backFragment()
    }

    /**
     * Updating Toolbar
     * Data is Stored on dataContainer
     *
     * *Icon: BACK or CLOSE
     * *Title: Title
     * invalidateOptionsMenu(): Update Menu
     */
    private fun updateToolbar() {
        val data = dataContainer[fragmentTypeHistory[currentTabType]!![fragmentTypeHistory[currentTabType]!!.size - 1]] as HashMap<String, Any>
        when (data["nav"]) {
            NavigationType.BACK -> {
                toolbar.navigationIcon = ContextCompat.getDrawable(this, R.mipmap.baseline_keyboard_arrow_left_black_24)
                toolbar.setNavigationOnClickListener { backFragment() }
            }
            else -> {
                toolbar.navigationIcon = null
                toolbar.setNavigationOnClickListener(null)
            }
        }
        toolbar.title = data["title"] as String
        invalidateOptionsMenu()
    }

    /**
     * Setting the data when changing Fragment
     * Data is stored on dataContainer by fragmentType
     */
    fun setData(fragmentType: FragmentType, navigationType: NavigationType, title: String, menu: Int) {
        val data = HashMap<String, Any>()
        data["nav"] = navigationType
        data["title"] = title
        data["menu"] = menu
        dataContainer[fragmentType] = data
        this.fragmentTypeHistory[currentTabType]?.add(fragmentType)
        updateToolbar()
    }
}