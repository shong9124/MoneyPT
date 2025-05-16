package com.capstone.data.di

import com.capstone.data.repository.AuthRepositoryImpl
import com.capstone.data.repository.PropensityRepositoryImpl
import com.capstone.domain.repository.AuthRepository
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
    abstract fun bindsPropensityRepository(impl: PropensityRepositoryImpl) : PropensityRepository

}