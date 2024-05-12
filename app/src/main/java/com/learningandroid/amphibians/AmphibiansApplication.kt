package com.learningandroid.amphibians

import android.app.Application
import com.learningandroid.amphibians.data.AppContainer
import com.learningandroid.amphibians.data.DefaultAppContainer

class AmphibiansApplication : Application() {
  lateinit var container: AppContainer
  override fun onCreate() {
    super.onCreate()
    container = DefaultAppContainer()
  }
}
