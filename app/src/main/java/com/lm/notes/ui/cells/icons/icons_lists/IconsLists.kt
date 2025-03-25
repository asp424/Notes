package com.lm.notes.ui.cells.icons.icons_lists

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.Sort
import androidx.compose.material.icons.rounded.ClearAll
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.ContentCut
import androidx.compose.material.icons.rounded.ContentPaste
import androidx.compose.material.icons.rounded.CopyAll
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.icons.rounded.SelectAll
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material.icons.rounded.Translate
import androidx.compose.material.icons.rounded.Widgets
import androidx.compose.ui.unit.dp
import com.lm.notes.data.local_data.ShareType


val listIconsMainBar by lazy {
    listOf(
        Pair(Icons.Rounded.Settings, 0.dp),
        Pair(Icons.AutoMirrored.Sharp.Sort, 10.dp),
        Pair(Icons.Rounded.SwapVert, 20.dp),
        Pair(Icons.Rounded.Public, 30.dp),
    )
}

val listIconsClipboard
        by lazy {
            listOf(
                Icons.Rounded.ClearAll,
                Icons.Rounded.ContentPaste,
                Icons.Rounded.SelectAll,
                Icons.Rounded.ContentCopy,
                Icons.Rounded.CopyAll,
                Icons.Rounded.ContentCut,
            )
        }
val listOfDeleteBarIcon by lazy {
    listOf(
        Pair(Icons.Rounded.Delete, 0.dp),
        Pair(Icons.Rounded.DeleteForever, (-10).dp)
    )
}

val listIconsNote by lazy {
    listOf(
        Pair(Icons.Rounded.Share, 0.dp),
        Pair(Icons.Rounded.Widgets, (-20).dp),
        Pair(Icons.Rounded.Translate, (-10).dp),
        Pair(Icons.Rounded.Save, 0.dp)
    )
}

val listShareTypes by lazy {
    listOf(
       ShareType.AsTxt,
       ShareType.AsHtml,
       ShareType.TextPlain,
       ShareType.Null
    )
}