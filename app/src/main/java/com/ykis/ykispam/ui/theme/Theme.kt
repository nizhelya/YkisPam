package com.ykis.ykispam.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


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

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun YkisPAMTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}