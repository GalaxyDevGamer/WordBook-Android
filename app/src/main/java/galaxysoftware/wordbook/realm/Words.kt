package galaxysoftware.wordbook.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Words: RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var word: String? = null
    var mean: String? = null
    var note: String? = null
    var eiken: String? = null
    var TOEIC: Int = 0
    var schoolLevel: String ? = null
}