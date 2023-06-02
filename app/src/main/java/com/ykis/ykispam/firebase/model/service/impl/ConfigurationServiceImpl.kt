/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.ykis.ykispam.firebase.model.service.impl

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.ykis.ykispam.core.trace
import com.ykis.ykispam.firebase.model.service.repo.ConfigurationService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.ykis.ykispam.R.xml as AppConfig


class ConfigurationServiceImpl @Inject constructor() : ConfigurationService {
  private val remoteConfig
    get() = Firebase.remoteConfig

  init {
//    if (BuildConfig.DEBUG) {
//      val configSettings = remoteConfigSettings { minimumFetchIntervalInSeconds = 0 }
//      remoteConfig.setConfigSettingsAsync(configSettings)
//    }

    remoteConfig.setDefaultsAsync(AppConfig.remote_config_defaults)
  }

  override suspend fun fetchConfiguration(): Boolean =
    trace(FETCH_CONFIG_TRACE) { remoteConfig.fetchAndActivate().await() }

  override val isShowTaskEditButtonConfig: Boolean
    get() = remoteConfig[SHOW_TASK_EDIT_BUTTON_KEY].asBoolean()
  override val agreementTitle: String
    get() = remoteConfig[AGREMENT_TITLE].asString()

  override val agreementText: String
    get() = remoteConfig[AGREMENT_TEXT].asString()
  companion object {
    private const val SHOW_TASK_EDIT_BUTTON_KEY = "show_task_edit_button"
    private const val AGREMENT_TITLE = "agreement_title"
    private const val AGREMENT_TEXT = "agreement_text"

    private const val FETCH_CONFIG_TRACE = "fetchConfig"
  }
}
