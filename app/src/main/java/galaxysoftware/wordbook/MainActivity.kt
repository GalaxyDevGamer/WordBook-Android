package galaxysoftware.wordbook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import android.view.Menu
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.FirebaseApp
import galaxysoftware.wordbook.entity.request.BaseRequest
import galaxysoftware.wordbook.fragment.RecentTabFragment
import galaxysoftware.wordbook.fragment.HomeTabFragment
import galaxysoftware.wordbook.type.FragmentType
import galaxysoftware.wordbook.type.NavigationType
import galaxysoftware.wordbook.type.TabType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var currentTabType = TabType.HOME

    private val fragmentTypeHistory = HashMap<TabType, ArrayList<FragmentType>>()
    private val dataContainer = HashMap<FragmentType, Any>()
    private val tabHistory = HashMap<TabType, ArrayList<BaseFragment>>()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ContextData.getInstance().activity = this
        ContextData.getInstance().mainActivity = this@MainActivity
        navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottom_navigation, navController)
        NavigationUI.setupWithNavController(toolbar, navController)
        setSupportActionBar(toolbar)
        initVariable()
        FirebaseApp.initializeApp(this)
        BaseRequest.instance.sync()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initVariable() {
        tabHistory[TabType.HOME] = ArrayList()
        tabHistory[TabType.SEARCH] = ArrayList()
        tabHistory[TabType.HOME]?.add(RecentTabFragment.newInstance())
        tabHistory[TabType.SEARCH]?.add(HomeTabFragment.newInstance())
        fragmentTypeHistory[TabType.HOME] = ArrayList()
        fragmentTypeHistory[TabType.SEARCH] = ArrayList()
        fragmentTypeHistory[TabType.HOME]?.add(FragmentType.HOME_TAB)
        fragmentTypeHistory[TabType.SEARCH]?.add(FragmentType.SEARCH_TAB)
        setTabData(FragmentType.HOME_TAB, NavigationType.NONE, getString(R.string.home), R.menu.add)
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

//    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
//        menu?.clear()
//        val data = dataContainer[fragmentTypeHistory[currentTabType]!![fragmentTypeHistory[currentTabType]!!.size - 1]] as HashMap<String, Any>
//        menuInflater.inflate(data["menu"] as Int, menu)
//        if (fragmentTypeHistory[currentTabType]!![fragmentTypeHistory[currentTabType]!!.size - 1] == FragmentType.SEARCH_TAB) {
//            val searchFragment = tabHistory[TabType.SEARCH]!![tabHistory[TabType.SEARCH]!!.size-1] as HomeTabFragment
//            val searchBar = menu?.findItem(R.id.search)?.actionView as SearchView
//            searchBar.setIconifiedByDefault(false)
//            searchBar.clearFocus()
//            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(p0: String?): Boolean {
//                    return false
//                }
//
//                override fun onQueryTextChange(p0: String?): Boolean {
//                    if (p0.isNullOrEmpty()) {
//                        searchFragment.clear()
//                        return false
//                    }
//                    searchFragment.searchWord(p0!!)
//                    return false
//                }
//            })
//        }
//        return super.onPrepareOptionsMenu(menu)
//    }

    fun backFragment() {
//        if (fragmentTypeHistory[currentTabType]!![fragmentTypeHistory[currentTabType]!!.size-1] == FragmentType.EDIT)
//            bottom_navigation.visibility = View.VISIBLE
//        tabHistory[currentTabType]!!.removeAt(tabHistory[currentTabType]!!.size - 1)
//        fragmentTypeHistory[currentTabType]!!.removeAt(fragmentTypeHistory[currentTabType]!!.size - 1)
//        updateToolbar()
    }

    override fun onBackPressed() {
        if (currentTabType == TabType.HOME && tabHistory[TabType.HOME]!!.size == 1) {
            finish()
            return
        }
        if (tabHistory[currentTabType]!!.size == 1) {
            return
        }
//        bottom_navigation.selectedItemId = when(currentTabType) {
//            TabType.HOME -> R.id.homeTab
//        }
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
    fun updateToolbar(title: String) {
//        val data = dataContainer[fragmentTypeHistory[currentTabType]!![fragmentTypeHistory[currentTabType]!!.size - 1]] as HashMap<String, Any>
//        when (data["nav"]) {
//            NavigationType.BACK -> {
//                toolbar.navigationIcon = ContextCompat.getDrawable(this, R.mipmap.baseline_keyboard_arrow_left_black_24)
//                toolbar.setNavigationOnClickListener { backFragment() }
//            }
//            else -> {
//                toolbar.navigationIcon = null
//                toolbar.setNavigationOnClickListener(null)
//            }
//        }
        toolbar.title = title
//        invalidateOptionsMenu()
    }
}