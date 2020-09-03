package galaxysoftware.wordbook.viewModel

import galaxysoftware.wordbook.entity.request.BaseRequest
import galaxysoftware.wordbook.helper.RealmManager

class WordListViewModel(private val dictionaryName: String) : TabModelBase() {

    init {
        if (isCustom())
            sync()
        else
            loadWords()
    }

    override fun loadWords() {
        words = when(dictionaryName) {
            "All words" -> RealmManager.instance.loadWordByAlphabeticalOrder()
            "Words to study" -> RealmManager.instance.loadWordsToStudy()
            "Remembered words" -> RealmManager.instance.loadRememberedWords()
            else -> RealmManager.instance.loadWordByAlphabeticalOrder()
        }
    }

    private fun isCustom() = when (dictionaryName) {
        "All words" -> false
        "Words to study" -> false
        "Remembered words" -> false
        else -> true
    }

    private fun sync() {
        BaseRequest.instance.syncDictionary(dictionaryName) { words, _ ->
            if (words != null) {
                this.words = words
            }
        }
    }
}