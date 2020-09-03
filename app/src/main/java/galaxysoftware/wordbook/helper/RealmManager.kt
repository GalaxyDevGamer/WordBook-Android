package galaxysoftware.wordbook.helper

import galaxysoftware.wordbook.model.Dictionary
import galaxysoftware.wordbook.model.Word
import galaxysoftware.wordbook.realm.DictionaryObject
import galaxysoftware.wordbook.realm.HistoryObject
import galaxysoftware.wordbook.realm.WordObject
import io.realm.Realm
import io.realm.RealmList
import io.realm.Sort

class RealmManager {

    fun loadDictionaries(): ArrayList<Dictionary> {
        val list = ArrayList<Dictionary>()
        list.add(Dictionary("All words"))
        list.add(Dictionary("Words to study"))
        list.add(Dictionary("Remembered words"))
        Realm.getDefaultInstance().where(DictionaryObject::class.java).findAll().forEach {
            list.add(Dictionary(it.name!!))
        }
        return list
    }

    fun loadWordsToStudy(): ArrayList<Word> {
        val list = ArrayList<Word>()
        Realm.getDefaultInstance().where(WordObject::class.java).equalTo("remembered", false).findAll().forEach {
            val means = ArrayList<String>()
            it.means.forEach { mean ->
                means.add(mean)
            }
            means.addAll(it.means)
            list.add((Word(docId = it.docId!!, id = it.id!!, word = it.word!!, means = means, note = it.note)))
        }
        return list
    }

    fun loadRememberedWords(): ArrayList<Word> {
        val list = ArrayList<Word>()
        Realm.getDefaultInstance().where(WordObject::class.java).equalTo("remembered", true).findAll().forEach {
            val means = ArrayList<String>()
            it.means.forEach { mean ->
                means.add(mean)
            }
            means.addAll(it.means)
            list.add((Word(docId = it.docId!!, id = it.id!!, word = it.word!!, means = means, note = it.note)))
        }
        return list
    }

    fun loadRecentWords(): ArrayList<Word> {
        val list = ArrayList<Word>()
        Realm.getDefaultInstance().where(WordObject::class.java).sort("id", Sort.DESCENDING).findAll().forEach {
            val means = ArrayList<String>()
            means.addAll(it.means)
            list.add((Word(docId = it.docId!!, id = it.id!!, word = it.word!!, means = means, note = it.note)))
        }
        return list
    }

    fun loadHistory(): ArrayList<Word> {
        val list = ArrayList<Word>()
        Realm.getDefaultInstance().where(HistoryObject::class.java).findAll().forEach {
            val means = ArrayList<String>()
            means.addAll(it.means)
            list.add((Word(docId = it.docId!!, id = it.id!!, word = it.word!!, means = means, note = it.note)))
        }
        return list
    }

    fun loadWordByAlphabeticalOrder(): ArrayList<Word> {
        val list = ArrayList<Word>()
        Realm.getDefaultInstance().where(WordObject::class.java).sort("word", Sort.ASCENDING).findAll().forEach {
            val means = ArrayList<String>()
            means.addAll(it.means)
            list.add((Word(docId = it.docId!!, id = it.id!!, word = it.word!!, means = means, note = it.note)))
        }
        return list
    }

    fun addWord(docId: String, id: Int, word: String, means: ArrayList<String>, note: String, remembered: Boolean) {
        val data = WordObject()
        data.docId = docId
        data.id = id
        data.word = word
        val meanList = RealmList<String>()
        meanList.addAll(means)
        data.means = meanList
        data.note = note
        data.remembered = remembered
        Realm.getDefaultInstance().executeTransaction {
            it.insertOrUpdate(data)
        }
    }

    fun search(word: String): ArrayList<Word> {
        val result = ArrayList<Word>()
        Realm.getDefaultInstance().where(WordObject::class.java).contains("word", word).findAll().forEach {
            val means = ArrayList<String>()
            means.addAll(it.means)
            result.add((Word(docId = it.docId!!, id = it.id!!, word = it.word!!, means = means, note = it.note)))
        }
        return result
    }

    fun createDictionary(name: String) {
        val data = DictionaryObject()
        data.name = name
        Realm.getDefaultInstance().executeTransaction {
            it.insertOrUpdate(data)
        }
    }

    fun getWordsInDictionary(name: String): ArrayList<Word> {
        val list = ArrayList<Word>()
        val dictionary = Realm.getDefaultInstance().where(DictionaryObject::class.java).equalTo("name", name).findFirst()
        return list
    }

    fun addToDictionary(docId: String, id: Int, word: String, means: ArrayList<String>, note: String, remembered: Boolean, to: String) {
    }

    fun addToDictionary(word: Word, to: String) {
    }

    fun removeFromDictionary(index: Int, dictionary: String) {
        val data = Realm.getDefaultInstance().where(DictionaryObject::class.java).equalTo("name", dictionary).findFirst()
        Realm.getDefaultInstance().executeTransaction {
            data?.words?.removeAt(index)
            it.insertOrUpdate(data)
        }
    }

    fun addToRemembered(word: String) {
        val data = Realm.getDefaultInstance().where(WordObject::class.java).equalTo("word", word).findFirst().apply {
            this?.remembered = true
        }
        Realm.getDefaultInstance().executeTransaction {
            it.insertOrUpdate(data)
        }
    }

    //Have to delete object using Realm Instance
    fun removeFromRemembered(word: String) {
        Realm.getDefaultInstance().executeTransaction {
            val data = Realm.getDefaultInstance().where(WordObject::class.java).equalTo("words", word).findFirst().apply {
                this?.remembered = false
            }
            it.insertOrUpdate(data)
        }
    }

    fun addToHistory(word: Word) = Realm.getDefaultInstance().executeTransaction {
        it.insertOrUpdate(HistoryObject().apply {
            this.docId = word.docId
            this.id = word.id
            this.word = word.word
            val meanList = RealmList<String>()
            meanList.addAll(word.means)
            this.note = word.note
        })
    }

    fun deleteFromHistory(word: String) = Realm.getDefaultInstance().executeTransaction {
        Realm.getDefaultInstance().where(HistoryObject::class.java).equalTo("word", word).findFirst()?.deleteFromRealm()
    }

    companion object {

        @JvmStatic
        val instance = RealmManager()
    }
}