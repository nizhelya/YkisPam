package com.ykis.ykispam.ui.screens.meter

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.R
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.BaseDualPanelContent
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.ContentType
import com.ykis.ykispam.ui.navigation.METER_TAB_ITEM
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.screens.appartment.content.DetailContent
import com.ykis.ykispam.ui.screens.meter.heat.HeatMeterList
import com.ykis.ykispam.ui.screens.meter.water.WaterMeterList

@Composable
fun MainMeterScreen(
        modifier: Modifier = Modifier,
        viewModel : MeterViewModel= hiltViewModel(),
        baseUIState: BaseUIState,
        navigationType:NavigationType,
        onDrawerClick: () ->Unit,
        contentType: ContentType,
        displayFeature: List<DisplayFeature>
    ) {

    val showDetail by viewModel.showDetail.collectAsStateWithLifecycle()
    if(contentType==ContentType.DUAL_PANE){
        BaseDualPanelContent(
            displayFeatures = displayFeature ,
            firstScreen = {
                MeterListScreen(
                    baseUIState = baseUIState,
                    navigationType = navigationType,
                    onDrawerClick = onDrawerClick,
                    viewModel = viewModel
                )
                          },
            secondScreen = {
                DetailContent(
                    baseUIState = baseUIState,
                    contentType = contentType,
                    contentDetail = ContentDetail.BTI,
                    onBackPressed = { /*TODO*/ },
                    showDetail = showDetail
                ) {
                    Text("SecondPanel")
                }
            }
        )
    }else Text("Single Panel")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeterListScreen(
    modifier: Modifier = Modifier,
    viewModel : MeterViewModel,
    baseUIState: BaseUIState,
    navigationType:NavigationType,
    onDrawerClick: () ->Unit,
)
{
    var selectedTab by rememberSaveable{
        mutableIntStateOf(0)
    }

    Column(Modifier.fillMaxSize()) {
        DefaultAppBar(
            title = stringResource(id = R.string.meters),
            onBackClick ={},
            onDrawerClick = onDrawerClick,
            canNavigateBack = false,
            navigationType = navigationType
        )
        PrimaryTabRow(
            selectedTabIndex = selectedTab ,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            METER_TAB_ITEM.forEachIndexed { index, tabItem ->
                LeadingIconTab(
                    selected = selectedTab==index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(text = stringResource( tabItem.titleId))
                    },
                    icon = {
                        Icon (
                            imageVector = if(index == selectedTab) tabItem.selectedIcon else tabItem.unselectedIcon,
                            contentDescription = stringResource( tabItem.titleId)
                        )
                    }
                )

            }
        }
        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            Crossfade(
                targetState = selectedTab, label = ""
            ) { targetState ->
                when (targetState) {
                    0 -> WaterMeterList(viewModel = viewModel, baseUIState = baseUIState)
                    else -> HeatMeterList(viewModel = viewModel, baseUIState = baseUIState)
                }
            }
        }
    }
}