package com.capstone.data.di

import com.capstone.data.remote.AuthRemoteDataSource
import com.capstone.data.remote.AuthRemoteDataSourceImpl
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

}
