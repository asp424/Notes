package com.lm.notes.data.local_data.paging

import com.lm.notes.data.models.NoteModel

interface Paginator<Key, Item> {

    suspend fun loadNextItem()

    fun reset()

    class Base<Key, Item>(
        private val initialKey: Key,
        private inline val onLoadUpdated: (Boolean) -> Unit,
        private inline val onRequest: suspend (nextKey: Key) -> Result<List<Item>>,
        private inline val getNextKey: suspend (List<Item>) -> Key,
        private inline val onError: suspend (Throwable?) -> Unit,
        private inline val onSuccess: suspend (items: List<Item>, nextKey: Key) -> Unit
    ) : Paginator<Key, Item> {

        private var currentKey = initialKey

        private var isMakingRequest = false

        override suspend fun loadNextItem() {
            if (isMakingRequest) return
            isMakingRequest = true
            onLoadUpdated(true)
            val result = onRequest(currentKey)
            isMakingRequest = false
            val items = result.getOrElse {
                onError(it)
                onLoadUpdated(false)
                return
            }
            currentKey = getNextKey(items)
            onSuccess(items, currentKey)
            onLoadUpdated(false)
        }

        override fun reset() { currentKey = initialKey }
    }
}


data class ScreenState(
    val isLoading: Boolean = false,
    val items: List<NoteModel> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)
