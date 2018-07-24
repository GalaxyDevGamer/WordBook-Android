package galaxysoftware.wordbook.callback

import galaxysoftware.wordbook.type.FragmentType

interface MainActivityView {
    fun requestChangeFragment(fragmentType: FragmentType) {}

    fun updateToolbar()
}