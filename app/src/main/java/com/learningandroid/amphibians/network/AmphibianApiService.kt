package com.learningandroid.amphibians.network

import retrofit2.http.GET

interface AmphibianApiService {
  @GET("amphibians")
  suspend fun getAmphibiansData(): List<AmphibianData>
}