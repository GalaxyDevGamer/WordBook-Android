package galaxysoftware.wordbook.helper

import galaxysoftware.wordbook.BaseFragment
import galaxysoftware.wordbook.type.FragmentType
import galaxysoftware.wordbook.fragment.*

class FragmentMakeHelper {
    companion object {
        fun makeFragment(fragmentType: FragmentType, any: Any): BaseFragment =
            when (fragmentType) {
                FragmentType.HOME_TAB -> RecentTabFragment.newInstance()
                FragmentType.SEARCH_TAB -> HomeTabFragment.newInstance()
                else -> HomeTabFragment.newInstance()
            }
    }
}