package fr.cedriccreusot.weatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import fr.cedriccreusot.data_adapter.local.repositories.LocalCitiesRepository
import fr.cedriccreusot.data_adapter.network.WeatherService
import fr.cedriccreusot.data_adapter.network.repositories.NetworkCitiesRepository
import fr.cedriccreusot.domain.repositories.CitiesRepository
import fr.cedriccreusot.domain.usecases.SearchCityFromListUseCase

@Module
@InstallIn(ActivityRetainedComponent::class)
object WeatherModule {
    @Provides
    fun provideWeatherService(): WeatherService =
        WeatherService.create()

    @Provides
    fun provideSearchCityFromListUseCase(): SearchCityFromListUseCase =
        SearchCityFromListUseCase()
}

@Module
@InstallIn(ActivityRetainedComponent::class)
object CitiesRepositoryModule {
    @Provides
    fun provideCitiesRepository(service: WeatherService): CitiesRepository =
        LocalCitiesRepository(NetworkCitiesRepository(service))
}