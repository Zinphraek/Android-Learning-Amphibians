package com.learningandroid.amphibians.data

import com.learningandroid.amphibians.network.AmphibianApiService
import com.learningandroid.amphibians.network.AmphibianData

interface AmphibiansRepository {
  suspend fun getAmphibiansData(): List<AmphibianData>
}

class NetworkAmphibiansRepository(private val amphibianApiService: AmphibianApiService) :
  AmphibiansRepository {
  override suspend fun getAmphibiansData(): List<AmphibianData> =
    amphibianApiService.getAmphibiansData()
}
