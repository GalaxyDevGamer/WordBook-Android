package galaxysoftware.wordbook.viewModel

import androidx.lifecycle.ViewModel
import galaxysoftware.wordbook.helper.RealmManager
import galaxysoftware.wordbook.model.Word

abstract class TabModelBase: ViewModel() {
    var words = ArrayList<Word>()

    abstract fun loadWords()

    fun addToHistory(index: Int) {
        RealmManager.instance.deleteFromHistory(words[index].word)
        RealmManager.instance.addToHistory(words[index])
    }
}