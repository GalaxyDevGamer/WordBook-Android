package galaxysoftware.wordbook.entity.response

interface Response {
    fun onEvent(result: Boolean, message: String)
}