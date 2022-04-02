package kuznetsov.hse.kmm

import co.touchlab.kermit.platformLogWriter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.toLocalDate
import kuznetsov.hse.kmm.database.DatabaseInitializer
import kuznetsov.hse.kmm.database.selectAll
import kuznetsov.hse.kmm.database.selectByPlatform

class MpViewModel(
    private val onPicturesFromNet: ((Map<String, SpacePictureVO>) -> Unit)? = null,
    private val onPicturesFromDB: ((Map<String, SpacePictureVO>) -> Unit)? = null,
) {

    private val client = SpacePictureHttpClient()

    private val scope = MainScope()

    private val _todoList = MutableStateFlow(mutableMapOf<String, SpacePictureVO>())

    val todoList: StateFlow<Map<String, SpacePictureVO>> = _todoList


    private val database = DatabaseInitializer().database

    init {
        observe()
    }

    private fun observe() {
    }

    fun add(text: String) {
        _todoList.value[text] = SpacePictureVO(
            title = text
        )
    }

    fun change(key: String, value:  String) {
        _todoList.value.remove(key)
        _todoList.value[value] = SpacePictureVO(
            title = value
        )
    }

}