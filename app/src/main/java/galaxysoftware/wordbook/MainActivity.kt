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
    private var homeFragmentHistory: MutableList<BaseFragment> = ArrayList()
    private var searchFragmentHistory: MutableList<BaseFragment> = ArrayList()

    private val homeFragment = HomeFragment.newInstance()
    private val searchFragment = SearchFragment.newInstance()
    private var currentTabType = TabType.HOME

    private val navData = HashMap<TabType, NavigationType>()
    private val titleData = HashMap<TabType, String>()
    private val menuData = HashMap<TabType, Int>()

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
        homeFragmentHistory.add(homeFragment)
        searchFragmentHistory.add(searchFragment)
        menuData[TabType.HOME] = R.menu.home
        menuData[TabType.SEARCH] = R.menu.search
    }

    private fun changeFirstFragment() {
        fragmentTransaction(homeFragment)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        menuInflater.inflate(menuData[currentTabType]!!, menu)
        if (getCurrentFragment() == searchFragment) {
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

    override fun onChangeFragment(fragmentType: FragmentType, any: Any) {
        changeFragment(fragmentType, any)
    }

    private fun changeFragment(fragmentType: FragmentType, any: Any) {
        val fragment = FragmentMakeHelper.makeFragment(fragmentType, any)
        when (currentTabType) {
            TabType.HOME -> { homeFragmentHistory.add(fragment) }
            TabType.SEARCH -> { searchFragmentHistory.add(fragment) }
        }
        fragmentTransaction(fragment)
    }

    private fun changeTab(tabType: TabType) {
        currentTabType = tabType
        fragmentTransaction(getCurrentFragment())
        updateToolbar()
        homeTabContainer.visibility = if (currentTabType == TabType.HOME) View.VISIBLE else View.INVISIBLE
        searchTabContainer.visibility = if (currentTabType == TabType.SEARCH) View.VISIBLE else View.INVISIBLE
        invalidateOptionsMenu()
    }

    private fun fragmentTransaction(fragment: BaseFragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (currentTabType) {
            TabType.HOME -> fragmentTransaction.replace(R.id.homeTabContainer, fragment)
            TabType.SEARCH -> fragmentTransaction.replace(R.id.searchTabContainer, fragment)
        }
        fragmentTransaction.commit()
    }

    private fun getCurrentFragment(): BaseFragment {
        return when (currentTabType) {
            TabType.HOME -> homeFragmentHistory[homeFragmentHistory.size - 1]
            TabType.SEARCH -> searchFragmentHistory[searchFragmentHistory.size - 1]
        }
    }

    fun backFragment() {
        when (currentTabType) {
            TabType.HOME -> homeFragmentHistory.removeAt(homeFragmentHistory.size - 1)
            TabType.SEARCH -> {
                if (searchFragmentHistory.size > 1)
                    searchFragmentHistory.removeAt(searchFragmentHistory.size - 1)
                else
                    changeTab(TabType.HOME)
            }
        }
        fragmentTransaction(getCurrentFragment())
    }

    override fun onBackPressed() {
        if (currentTabType == TabType.HOME && homeFragmentHistory[homeFragmentHistory.size - 1] == homeFragment) {
            finish()
            return
        }
        bottom_navigation.selectedItemId = when(currentTabType) {
            TabType.HOME -> R.id.homeTab
            TabType.SEARCH -> R.id.searchTab
        }
        backFragment()
    }

    private fun updateToolbar() {
        when (navData[currentTabType]) {
            NavigationType.BACK -> {
                toolbar.navigationIcon = ContextCompat.getDrawable(this, R.mipmap.baseline_keyboard_arrow_left_black_24)
                toolbar.setNavigationOnClickListener { backFragment() }
            }
            else -> {
                toolbar.navigationIcon = null
                toolbar.setNavigationOnClickListener(null)
            }
        }
        toolbar.title = titleData[currentTabType]
    }

    fun setData(navigationType: NavigationType, title: String, menu: Int) {
        navData[currentTabType] = navigationType
        titleData[currentTabType] = title
        menuData[currentTabType] = menu
        updateToolbar()
    }
}