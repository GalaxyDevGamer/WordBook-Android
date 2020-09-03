package galaxysoftware.wordbook.viewModel

import galaxysoftware.wordbook.helper.RealmManager

class HistoryTabModel: TabModelBase() {

    init {
        loadWords()
    }

    override fun loadWords() {
        words = RealmManager.instance.loadHistory()
    }
}