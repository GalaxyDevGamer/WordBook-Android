package galaxysoftware.wordbook.viewModel

import galaxysoftware.wordbook.helper.RealmManager

class RecentTabModel: TabModelBase() {

    init {
        loadWords()
    }

    override fun loadWords() {
        words = RealmManager.instance.loadRecentWords()
    }
}