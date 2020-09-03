package galaxysoftware.wordbook.realm

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class WordObject: RealmObject() {
    @PrimaryKey
    var docId: String? = null
    var id: Int? = null
    var word: String? = null
    var means = RealmList<String>()
    var note: String? = null
    var remembered = false
}