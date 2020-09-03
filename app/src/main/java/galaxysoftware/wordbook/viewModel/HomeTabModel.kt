package galaxysoftware.wordbook.viewModel

import galaxysoftware.wordbook.helper.RealmManager

class HomeTabModel : TabModelBase() {

    init {
        loadWords()
    }

    override fun loadWords() {
        words = RealmManager.instance.loadRecentWords()
    }

    fun search(query: String) = RealmManager.instance.search(query)
}
