package galaxysoftware.wordbook

import android.app.Activity
import android.content.Context

class ContextData {
    var activity: Activity? = null
    var applicationContext: Context? = null
    var mainActivity: MainActivity? = null

    private fun ContextData() {}

    companion object {
        private var instance: ContextData? = null

        fun getInstance(): ContextData {
            if (instance == null)
                instance = ContextData()

            return instance!!
        }
    }
}