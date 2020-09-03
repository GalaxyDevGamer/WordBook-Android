package galaxysoftware.wordbook.entity.request

import com.google.firebase.firestore.FirebaseFirestore
import galaxysoftware.wordbook.helper.RealmManager
import galaxysoftware.wordbook.model.Word

class BaseRequest {

    private val endPoint = FirebaseFirestore.getInstance().collection("words")

    private val dictionaryURL = FirebaseFirestore.getInstance().collection("dictionaries")

    fun sync() {
        endPoint.addSnapshotListener { snapshot, exception ->
            if (exception == null) {
                for (document in snapshot!!.documents) {
                    val remembered = if (document.data?.get("remembered") == null) false else document.data!!["remembered"] as Boolean
                    RealmManager.instance.addWord(docId = document.id, id = document.data!!["id"].toString().toInt(), word = document.data!!["word"].toString(), means = document.data!!["means"] as ArrayList<String>, note = document.data!!["description"].toString(), remembered = remembered)
                }
            }
        }
        dictionaryURL.addSnapshotListener { snapshot, error ->
            if (error == null) {
                for (document in snapshot!!.documents) {
                    RealmManager.instance.createDictionary(name = document.data!!["name"].toString())
                }
            }
        }
    }

    fun add(index: Int, word: String, means: ArrayList<String>, description: String, completion: (Boolean, String?) -> Unit) {
        val data = HashMap<String, Any>().apply {
            this["id"] = index
            this["word"] = word
            this["means"] = means
            this["description"] = description
            this["remembered"] = false
        }
        endPoint.document(word).set(data).addOnCompleteListener {
            if (it.isSuccessful)
                completion(true, null)
            else
                completion(false, it.exception?.message.toString())
        }
    }

    fun update(word: String, means: ArrayList<String>, description: String, completion: (Boolean, String?) -> Unit) {
        val data = HashMap<String, Any>().apply {
            this["word"] = word
            this["means"] = means
            this["description"] = description
        }
        FirebaseFirestore.getInstance().batch().update(endPoint.document(word), data).commit().addOnCompleteListener {
            if (it.isSuccessful)
                completion(true, null)
            else
                completion(false, it.exception?.message.toString())
        }
    }

    fun searchFrom(Japanese: String, completion: (ArrayList<Word>?, String?) -> Unit) {
        endPoint.whereArrayContains("means", Japanese).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val result = ArrayList<Word>()
                if (it.result?.documents?.size != 0) {
                    for (document in it.result!!.documents) {
                        val means = ArrayList<String>()
                        for (mean in document.data!!["means"] as ArrayList<String>) {
                            means.add(mean)
                        }
                        result.add(Word(docId = document.id, id = document.data!!["id"].toString().toInt(), word = document.data!!["word"].toString(), means = document.data!!["means"] as ArrayList<String>, note = document.data!!["description"].toString()))
                    }
                }
                completion(result, null)
            } else {
                completion(null, it.exception?.message)
            }
        }
    }

    fun updateRemembered(word: String, to: Boolean, completion: (Boolean, String?) -> Unit) {
        FirebaseFirestore.getInstance().batch().update(endPoint.document(word), HashMap<String, Any>().apply { this["remembered"] = to }).commit().addOnCompleteListener {
            if (it.isSuccessful)
                completion(true, null)
            else
                completion(false, it.exception?.message.toString())
        }
    }

    fun createDictionary(name: String, completion: (Boolean, String?) -> Unit) {
        dictionaryURL.document(name).set(HashMap<String, Any>().apply { this["name"] = name }).addOnCompleteListener {
            if (it.isSuccessful) {
                completion(true, null)
            } else {
                completion(false, it.exception?.message.toString())
            }
        }
    }

    fun addToDictionary(word: Word, to: String, completion: (Boolean, String?) -> Unit) {
        val data = HashMap<String, Any>().apply {
            this["id"] = word.id
            this["word"] = word.word
            this["means"] = word.means
            this["description"] = word.note.toString()
        }
        dictionaryURL.document(to).collection("words").add(data).addOnCompleteListener {
            if (it.isSuccessful)
                completion(true, null)
            else
                completion(false, it.exception?.message.toString())
        }
    }

    fun syncDictionary(name: String, completion: (ArrayList<Word>?, String?) -> Unit) {
        FirebaseFirestore.getInstance().collection("dictionaries").document(name).collection("words").addSnapshotListener { snapshot, exception ->
            if (exception == null) {
                val words = ArrayList<Word>()
                for (document in snapshot!!.documents) {
                    words.add(Word(docId = document.id, id = document.data!!["id"].toString().toInt(), word = document.data!!["word"].toString(), means = document.data!!["means"] as ArrayList<String>, note = document.data!!["description"].toString()))
                }
                completion(words, null)
            } else
                completion(null, exception.message)

        }
    }

    companion object {
        @JvmStatic
        val instance = BaseRequest()
    }
}