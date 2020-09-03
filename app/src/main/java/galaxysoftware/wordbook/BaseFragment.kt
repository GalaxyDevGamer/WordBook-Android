package galaxysoftware.wordbook

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import galaxysoftware.wordbook.callback.WordSelectedListener
import galaxysoftware.wordbook.type.FragmentType
import galaxysoftware.wordbook.type.NavigationType

abstract class BaseFragment : Fragment(), WordSelectedListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    fun backFragment() = getMainActivity().backFragment()

    fun getBaseActivity(): Activity = ContextData.getInstance().activity!!

    fun getMainActivity(): MainActivity = ContextData.getInstance().mainActivity!!

    abstract fun getLayoutId():Int

    abstract fun initialize()

    abstract fun updateFragment()

    override fun onClick(word: String, index: Int) {

    }

    /**
     * Used for setting the data on Toolbar
     */
    fun updateToolbar(title: String) = getMainActivity().updateToolbar(title)
}