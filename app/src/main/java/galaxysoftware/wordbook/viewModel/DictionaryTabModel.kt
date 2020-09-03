package galaxysoftware.wordbook.viewModel

import androidx.lifecycle.ViewModel
import galaxysoftware.wordbook.helper.RealmManager
import galaxysoftware.wordbook.model.Dictionary

class DictionaryTabModel : ViewModel() {

    var dictionaries = ArrayList<Dictionary>()

    init {
        loadWords()
    }

    fun loadWords() {
        dictionaries = RealmManager.instance.loadDictionaries()
    }
}
