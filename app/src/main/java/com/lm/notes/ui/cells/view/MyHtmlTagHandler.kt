package com.lm.notes.ui.cells.view

import android.text.Editable
import android.text.Html
import android.text.Spannable
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import org.xml.sax.XMLReader

class MyHtmlTagHandler(private val proportion: Float) : Html.TagHandler {

    override fun handleTag(
        opening: Boolean, tag: String, output: Editable,
        xmlReader: XMLReader?
    ) {
        output.setSpan(RelativeSizeSpan(proportion), 0, output.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}