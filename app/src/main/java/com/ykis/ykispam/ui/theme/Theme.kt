package com.ykis.ykispam.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Material 3 color schemes

@Immutable
data class ExtendedColorScheme(
    val selectedElement: ColorFamily,
    val sectorColor1 : ColorFamily,
    val sectorColor2 : ColorFamily,
    val sectorColor3 : ColorFamily,
    val sectorColor4 : ColorFamily,
)


@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color?=null,
    val colorContainer: Color?=null,
    val onColorContainer: Color?=null
)

val extendedDark = ExtendedColorScheme(
    selectedElement = ColorFamily(
        selectedElementDark ,
        onSelectedElementDark,
        selectedElementContainerDark,
        onSelectedElementContainerDark
    ),
    sectorColor1 = ColorFamily(
        sectorColor1ContainerDark,
    ),
    sectorColor2 = ColorFamily(
        sectorColor2ContainerDark
    ),
    sectorColor3 = ColorFamily(
        sectorColor3ContainerDark
    ),
    sectorColor4 = ColorFamily(
        sectorColor4ContainerDark
    ),
)
val extendedLight = ExtendedColorScheme(
    selectedElement = ColorFamily(
        selectedElementLight ,
        onSelectedElementLight,
        selectedElementContainerLight,
        onSelectedElementContainerLight
    ),
    sectorColor1 = ColorFamily(
        sectorColor1ContainerLight,
    ),
    sectorColor2 = ColorFamily(
        sectorColor2ContainerLight
    ),
    sectorColor3 = ColorFamily(
        sectorColor3ContainerLight
    ),
    sectorColor4 = ColorFamily(
        sectorColor4ContainerLight
    ),
)
val ColorScheme.extendedColor: ExtendedColorScheme
    @Composable
get() = if (!isSystemInDarkTheme()) extendedLight else extendedDark

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)


@Composable
fun YkisPAMTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        lightScheme
    } else {
        darkScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.primary.toArgb()
            window.navigationBarColor = colors.surfaceContainer.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                useDarkTheme
        }
    }
    MaterialTheme(
        colorScheme = colors,
        content = content,
        typography = typography,
        shapes = shapes
    )
}