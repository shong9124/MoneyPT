package com.capstone.data.di

import com.capstone.data.service.AuthService
import com.capstone.data.service.PropensityService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal class ServiceModule {

    @Provides
    fun providesAuthService(
        client: Retrofit
    ): AuthService = client.create(AuthService::class.java)

    @Provides
    fun providesPropensityService(
        client: Retrofit
    ): PropensityService = client.create(PropensityService::class.java)

}