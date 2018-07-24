package galaxysoftware.wordbook.helper

import galaxysoftware.wordbook.BaseFragment
import galaxysoftware.wordbook.type.FragmentType
import galaxysoftware.wordbook.type.TabType
import galaxysoftware.wordbook.fragment.*

class FragmentMakeHelper {
    companion object {
        fun makeFragment(fragmentType: FragmentType, any: Any): BaseFragment =
            when (fragmentType) {
                FragmentType.VIEW -> WordViewFragment.newInstance(any)
                FragmentType.EDIT -> EditorFragment.newInstance(any)
            }
    }
}