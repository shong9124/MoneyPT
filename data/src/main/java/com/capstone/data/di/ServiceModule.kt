package com.capstone.data.di

import com.capstone.data.service.AuthService
import com.capstone.data.service.BankProductService
import com.capstone.data.service.CardRecommendService
import com.capstone.data.service.ChatBotService
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

    @Provides
    fun providesChatService(
        client: Retrofit
    ): ChatBotService = client.create(ChatBotService::class.java)

    @Provides
    fun providesBankProductService(
        client: Retrofit
    ): BankProductService = client.create(BankProductService::class.java)

    @Provides
    fun providesCardRecommendationService(
        client: Retrofit
    ): CardRecommendService = client.create(CardRecommendService::class.java)

}