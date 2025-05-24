package com.capstone.data.di

import com.capstone.data.repository.AuthRepositoryImpl
import com.capstone.data.repository.BankRecommendRepositoryImpl
import com.capstone.data.repository.CardRecommendationRepositoryImpl
import com.capstone.data.repository.ChatBotRepositoryImpl
import com.capstone.data.repository.PropensityRepositoryImpl
import com.capstone.domain.repository.AuthRepository
import com.capstone.domain.repository.BankRecommendRepository
import com.capstone.domain.repository.CardRecommendationRepository
import com.capstone.domain.repository.ChatBotRepository
import com.capstone.domain.repository.PropensityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindsPropensityRepository(impl: PropensityRepositoryImpl): PropensityRepository

    @Binds
    @Singleton
    abstract fun bindsChatBotRepository(impl: ChatBotRepositoryImpl): ChatBotRepository

    @Binds
    @Singleton
    abstract fun bindsBankRecommendRepository(impl: BankRecommendRepositoryImpl): BankRecommendRepository

    @Binds
    @Singleton
    abstract fun bindsCardRecommendationRepository(impl: CardRecommendationRepositoryImpl): CardRecommendationRepository

}