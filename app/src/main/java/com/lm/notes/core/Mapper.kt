package com.lm.notes.core

interface Mapper {

    fun interface Data<in S, out R> : Mapper {
        fun map(data: S): R
    }

    interface DataToUI<in S, out R> {
        suspend fun map(state: S): R
    }
}