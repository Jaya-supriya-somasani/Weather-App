package com.example.weather.di

import com.example.weather.ui.adapter.WeeksAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object AdapterModule {

    @Provides
    @FragmentScoped
    fun provideWeeksAdapter(): WeeksAdapter {
        return WeeksAdapter()
    }
}