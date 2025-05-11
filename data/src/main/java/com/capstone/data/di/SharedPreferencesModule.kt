package com.capstone.data.di

import android.content.Context
import com.capstone.data.service.AuthService
import com.capstone.data.util.MySharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal class SharedPreferencesModule {
    @Provides
    fun provideSharedPreference(
        @ApplicationContext context: Context
    ): MySharedPreferences = MySharedPreferences(context)
}