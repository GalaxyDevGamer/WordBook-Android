package galaxysoftware.wordbook.viewModel

import galaxysoftware.wordbook.helper.RealmManager

class WordToStudyTabModel: TabModelBase() {

    init {
        loadWords()
    }

    override fun loadWords() {
        words = RealmManager.instance.loadWordsToStudy()
    }
}