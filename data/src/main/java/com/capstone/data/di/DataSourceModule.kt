package com.capstone.data.di

import com.capstone.data.remote.AuthRemoteDataSource
import com.capstone.data.remote.AuthRemoteDataSourceImpl
import com.capstone.data.remote.BankProductRemoteDataSource
import com.capstone.data.remote.BankProductRemoteDataSourceImpl
import com.capstone.data.remote.ChatBotRemoteDataSource
import com.capstone.data.remote.ChatBotRemoteDataSourceImpl
import com.capstone.data.remote.PropensityRemoteDataSource
import com.capstone.data.remote.PropensityRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindPropensityRemoteDataSource(
        propensityRemoteDataSourceImpl: PropensityRemoteDataSourceImpl
    ): PropensityRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindChatBotRemoteDataSource(
        chatBotRemoteDataSourceImpl: ChatBotRemoteDataSourceImpl
    ): ChatBotRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindBankProductRemoteDataSource(
        bankProductRemoteDataSourceImpl: BankProductRemoteDataSourceImpl
    ): BankProductRemoteDataSource

}
