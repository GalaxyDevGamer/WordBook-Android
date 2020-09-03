package galaxysoftware.wordbook.model

data class Word(
        var docId: String,
        var id: Int,
        var word: String,
        var means: ArrayList<String>,
        var note: String?
)