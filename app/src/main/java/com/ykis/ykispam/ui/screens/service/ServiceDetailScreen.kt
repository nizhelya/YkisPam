package com.ykis.ykispam.ui.screens.service

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ykis.ykispam.R
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.screens.service.detail.ServiceDetailContent
import com.ykis.ykispam.ui.screens.service.payment.PaymentContentStateful

@Composable
fun ServiceDetailScreen(
    modifier:Modifier = Modifier,
    navigationType: NavigationType,
    viewModel: ServiceViewModel,
    contentDetail: ContentDetail,
    baseUIState: BaseUIState
) {
    Column(
        modifier = modifier
    ){
        DefaultAppBar(
            navigationType = navigationType,
            canNavigateBack = true,
            onBackClick =  {
                viewModel.closeContentDetail()
            },
            title = when(contentDetail){
                ContentDetail.OSBB -> baseUIState.osbb
                ContentDetail.WATER_SERVICE -> stringResource(id = R.string.vodokanal)
                ContentDetail.WARM_SERVICE -> stringResource(id = R.string.ytke)
                ContentDetail.GARBAGE_SERVICE -> stringResource(id = R.string.yzhtrans)
                else -> stringResource(id = R.string.payment_list)
            }
        )
        if(contentDetail == ContentDetail.PAYMENT_LIST){
            PaymentContentStateful(
                serviceViewModel = viewModel,
                baseUIState = baseUIState
            )
        }else{
            ServiceDetailContent(
                contentDetail = contentDetail,
                baseUIState = baseUIState
            )
        }
    }

}