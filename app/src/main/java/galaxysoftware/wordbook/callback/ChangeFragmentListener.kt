package galaxysoftware.wordbook.callback

import galaxysoftware.wordbook.type.FragmentType

interface ChangeFragmentListener {
    fun onChangeFragment(fragmentType: FragmentType, any: Any)
}