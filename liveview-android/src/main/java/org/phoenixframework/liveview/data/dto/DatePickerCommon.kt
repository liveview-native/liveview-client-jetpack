package org.phoenixframework.liveview.data.dto

import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableMap
import org.phoenixframework.liveview.data.constants.ColorAttrs
import org.phoenixframework.liveview.domain.extensions.toColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun getDatePickerColors(colors: ImmutableMap<String, String>?): DatePickerColors {
    val defaultValue = DatePickerDefaults.colors()
    return if (colors == null) {
        defaultValue
    } else {
        DatePickerDefaults.colors(
            containerColor = colors[ColorAttrs.colorAttrContainerColor]?.toColor()
                ?: MaterialTheme.colorScheme.surface,
            titleContentColor = colors[ColorAttrs.colorAttrTitleContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onSurfaceVariant,
            headlineContentColor = colors[ColorAttrs.colorAttrHeadlineContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onSurfaceVariant,
            weekdayContentColor = colors[ColorAttrs.colorAttrWeekdayContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onSurface,
            subheadContentColor = colors[ColorAttrs.colorAttrSubheadContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onSurfaceVariant,
            yearContentColor = colors[ColorAttrs.colorAttrYearContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onSurfaceVariant,
            currentYearContentColor = colors[ColorAttrs.colorAttrCurrentYearContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.primary,
            selectedYearContentColor = colors[ColorAttrs.colorAttrSelectedYearContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onPrimary,
            selectedYearContainerColor = colors[ColorAttrs.colorAttrSelectedYearContainerColor]?.toColor()
                ?: MaterialTheme.colorScheme.primary,
            dayContentColor = colors[ColorAttrs.colorAttrDayContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onSurface,
            disabledDayContentColor = colors[ColorAttrs.colorAttrDisabledDayContentColor]?.toColor()
                ?: (colors[ColorAttrs.colorAttrDayContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface).copy(alpha = 0.38f),
            selectedDayContentColor = colors[ColorAttrs.colorAttrSelectedDayContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onPrimary,
            disabledSelectedDayContentColor = colors[ColorAttrs.colorAttrDisabledSelectedDayContentColor]?.toColor()
                ?: (colors[ColorAttrs.colorAttrSelectedDayContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onPrimary).copy(alpha = 0.38f),
            selectedDayContainerColor = colors[ColorAttrs.colorAttrSelectedDayContainerColor]?.toColor()
                ?: MaterialTheme.colorScheme.primary,
            disabledSelectedDayContainerColor = colors[ColorAttrs.colorAttrDisabledSelectedDayContainerColor]?.toColor()
                ?: (colors[ColorAttrs.colorAttrSelectedDayContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary).copy(alpha = 0.38f),
            todayContentColor = colors[ColorAttrs.colorAttrTodayContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.primary,
            todayDateBorderColor = colors[ColorAttrs.colorAttrTodayBorderColor]?.toColor()
                ?: MaterialTheme.colorScheme.primary,
            dayInSelectionRangeContentColor = colors[ColorAttrs.colorAttrDayInSelectionRangeContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onSecondaryContainer,
            dayInSelectionRangeContainerColor = colors[ColorAttrs.colorAttrDayInSelectionRangeContainerColor]?.toColor()
                ?: MaterialTheme.colorScheme.secondaryContainer,
        )
    }
}