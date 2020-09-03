package galaxysoftware.wordbook.realm

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DictionaryObject: RealmObject() {
    @PrimaryKey
    var name: String? = null
    var words = RealmList<WordObject>()
}