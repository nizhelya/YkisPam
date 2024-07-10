package com.ykis.ykispam.ui.screens.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ykis.ykispam.R
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar
import com.ykis.ykispam.ui.navigation.NavigationType

@Composable
fun UserListScreen(
    modifier: Modifier = Modifier,
    userList: List<UserEntity>,
    baseUIState: BaseUIState,
    onUserClicked : (UserEntity) -> Unit,
    onDrawerClicked :()->Unit,
    navigationType: NavigationType,

) {
    Column {
        DefaultAppBar(
            title = stringResource(id = R.string.chat),
            onDrawerClick = onDrawerClicked,
            canNavigateBack = false,
            navigationType = navigationType

        )
        UserList(
            userList = userList,
            baseUIState = baseUIState ,
            onUserClick = onUserClicked
        )
    }
}