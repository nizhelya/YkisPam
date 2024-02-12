package com.ykis.ykispam.data.remote.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.type.HandleOnce


fun <T : Any?, L : LiveData<T>> LifecycleOwner.onSuccess(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<HandleOnce<Failure>>> LifecycleOwner.onFailure(liveData: L, body: (Failure?) -> Unit) =
    liveData.observe(this) {
        it.getContentIfNotHandled()?.let(body)
    }