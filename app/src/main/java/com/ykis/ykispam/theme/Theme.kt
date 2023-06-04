package com.ykis.ykispam.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

//
//// Material 3 color schemes
//private val replyDarkColorScheme = darkColorScheme(
//    primary = replyDarkPrimary,
//    onPrimary = replyDarkOnPrimary,
//    primaryContainer = replyDarkPrimaryContainer,
//    onPrimaryContainer = replyDarkOnPrimaryContainer,
//    inversePrimary = replyDarkPrimaryInverse,
//    secondary = replyDarkSecondary,
//    onSecondary = replyDarkOnSecondary,
//    secondaryContainer = replyDarkSecondaryContainer,
//    onSecondaryContainer = replyDarkOnSecondaryContainer,
//    tertiary = replyDarkTertiary,
//    onTertiary = replyDarkOnTertiary,
//    tertiaryContainer = replyDarkTertiaryContainer,
//    onTertiaryContainer = replyDarkOnTertiaryContainer,
//    error = replyDarkError,
//    onError = replyDarkOnError,
//    errorContainer = replyDarkErrorContainer,
//    onErrorContainer = replyDarkOnErrorContainer,
//    background = replyDarkBackground,
//    onBackground = replyDarkOnBackground,
//    surface = replyDarkSurface,
//    onSurface = replyDarkOnSurface,
//    inverseSurface = replyDarkInverseSurface,
//    inverseOnSurface = replyDarkInverseOnSurface,
//    surfaceVariant = replyDarkSurfaceVariant,
//    onSurfaceVariant = replyDarkOnSurfaceVariant,
//    outline = replyDarkOutline
//)
//
//private val replyLightColorScheme = lightColorScheme(
//    primary = replyLightPrimary,
//    onPrimary = replyLightOnPrimary,
//    primaryContainer = replyLightPrimaryContainer,
//    onPrimaryContainer = replyLightOnPrimaryContainer,
//    inversePrimary = replyLightPrimaryInverse,
//    secondary = replyLightSecondary,
//    onSecondary = replyLightOnSecondary,
//    secondaryContainer = replyLightSecondaryContainer,
//    onSecondaryContainer = replyLightOnSecondaryContainer,
//    tertiary = replyLightTertiary,
//    onTertiary = replyLightOnTertiary,
//    tertiaryContainer = replyLightTertiaryContainer,
//    onTertiaryContainer = replyLightOnTertiaryContainer,
//    error = replyLightError,
//    onError = replyLightOnError,
//    errorContainer = replyLightErrorContainer,
//    onErrorContainer = replyLightOnErrorContainer,
//    background = replyLightBackground,
//    onBackground = replyLightOnBackground,
//    surface = replyLightSurface,
//    onSurface = replyLightOnSurface,
//    inverseSurface = replyLightInverseSurface,
//    inverseOnSurface = replyLightInverseOnSurface,
//    surfaceVariant = replyLightSurfaceVariant,
//    onSurfaceVariant = replyLightOnSurfaceVariant,
//    outline = replyLightOutline
//)
//
//@Composable
//fun YkisPAMTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    dynamicColor: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    val replyColorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//        darkTheme -> replyDarkColorScheme
//        else -> replyLightColorScheme
//    }
//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            window.statusBarColor = replyColorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//        }
//    }
//
//    androidx.compose.material3.MaterialTheme(
//        colorScheme = replyColorScheme,
//        typography = replyTypography,
//        shapes = shapes,
//        content = content
//    )
//}


/**
 * A [MaterialTheme] for Rally.
 */
@Composable
fun YkisPAMTheme(content: @Composable () -> Unit) {

    MaterialTheme(colors = ColorPalette, typography = Typography, content = content)
}

/**
 * A theme overlay used for dialogs.
 */
@Composable
fun RallyDialogThemeOverlay(content: @Composable () -> Unit) {
    // Rally is always dark themed.
    val dialogColors = darkColors(
        primary = Color.White,
        surface = Color.White.copy(alpha = 0.12f).compositeOver(Color.Black),
        onSurface = Color.White
    )

    // Copy the current [Typography] and replace some text styles for this theme.
    val currentTypography = MaterialTheme.typography
    val dialogTypography = currentTypography.copy(
        body2 = currentTypography.body1.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            letterSpacing = 1.sp
        ),
        button = currentTypography.button.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.2.em
        )
    )
    MaterialTheme(colors = dialogColors, typography = dialogTypography, content = content)
}
