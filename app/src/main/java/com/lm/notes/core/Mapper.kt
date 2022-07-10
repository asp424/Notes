package com.lm.notes.core

interface Mapper {

    interface DataToUI<in S, out R> {
        suspend fun map(state: S): R
    }
}