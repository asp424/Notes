package com.lm.notes.ui.cells.icons.icons_lists

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.Sort
import androidx.compose.material.icons.rounded.AddLink
import androidx.compose.material.icons.rounded.ClearAll
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.ContentCut
import androidx.compose.material.icons.rounded.ContentPaste
import androidx.compose.material.icons.rounded.CopyAll
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.FormatBold
import androidx.compose.material.icons.rounded.FormatClear
import androidx.compose.material.icons.rounded.FormatColorFill
import androidx.compose.material.icons.rounded.FormatColorText
import androidx.compose.material.icons.rounded.FormatItalic
import androidx.compose.material.icons.rounded.FormatStrikethrough
import androidx.compose.material.icons.rounded.FormatUnderlined
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.icons.rounded.SelectAll
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material.icons.rounded.Translate
import androidx.compose.material.icons.rounded.Widgets
import androidx.compose.ui.unit.dp
import com.lm.notes.data.models.ShareType


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
                Icons.Rounded.Delete
            )
        }
val listOfDeleteBarIcon by lazy {
    listOf(
        Pair(Icons.Rounded.Delete, 0.dp),
        Pair(Icons.Rounded.DeleteForever, 7.dp)
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
        Pair(ShareType.AsTxt, (-30).dp),
        Pair(ShareType.AsHtml, (-60).dp),
        Pair(ShareType.TextPlain, (-90).dp),
        Pair(ShareType.Null, 0.dp)
    )
}

val listFormatBarIcons by lazy {
    listOf(
        Icons.Rounded.AddLink,
        Icons.Rounded.FormatBold,
        Icons.Rounded.FormatItalic,
        Icons.Rounded.FormatUnderlined,
        Icons.Rounded.FormatStrikethrough,
        Icons.Rounded.FormatColorText,
        Icons.Rounded.FormatColorFill,
        Icons.Rounded.FormatClear
    )
}
