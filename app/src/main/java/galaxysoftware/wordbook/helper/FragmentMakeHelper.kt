package galaxysoftware.wordbook.helper

import galaxysoftware.wordbook.BaseFragment
import galaxysoftware.wordbook.type.FragmentType
import galaxysoftware.wordbook.type.TabType
import galaxysoftware.wordbook.fragment.*

class FragmentMakeHelper {
    companion object {
        fun makeFragment(fragmentType: FragmentType, any: Any): BaseFragment =
            when (fragmentType) {
                FragmentType.HOME_TAB -> HomeFragment.newInstance()
                FragmentType.SEARCH_TAB -> SearchFragment.newInstance()
                FragmentType.VIEW -> WordViewFragment.newInstance(any)
                FragmentType.EDIT -> EditorFragment.newInstance(any)
            }
    }
}