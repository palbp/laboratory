@file:Suppress("unused")

package palbp.laboratory.demos.quoteofday.testutils

import androidx.compose.ui.test.SemanticsMatcher
import palbp.laboratory.demos.quoteofday.ui.expandedPropertyKey


fun hasExpandableProperty() =
    SemanticsMatcher.keyIsDefined(expandedPropertyKey)

fun isExpanded() =
    SemanticsMatcher.expectValue(expandedPropertyKey, true)