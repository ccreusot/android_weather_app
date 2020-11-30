package fr.cedriccreusot.weatherapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import fr.cedriccreusot.data_adapter.local.repositories.LocalCitiesRepository
import fr.cedriccreusot.data_adapter.local.repositories.LocalFavoriteRepository
import fr.cedriccreusot.data_adapter.local.repositories.LocalLocationRepository
import fr.cedriccreusot.data_adapter.network.WeatherService
import fr.cedriccreusot.data_adapter.network.repositories.NetworkCitiesRepository
import fr.cedriccreusot.data_adapter.network.repositories.NetworkWeatherRepository
import fr.cedriccreusot.domain.repositories.CitiesRepository
import fr.cedriccreusot.domain.repositories.FavoriteLocationRepository
import fr.cedriccreusot.domain.repositories.LocationRepository
import fr.cedriccreusot.domain.repositories.WeatherRepository
import fr.cedriccreusot.domain.usecases.SearchCityFromListUseCase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object WeatherModule {
    @Singleton
    @Provides
    fun provideWeatherService(): WeatherService =
        WeatherService.create()

    @Singleton
    @Provides
    fun provideCitiesRepository(service: WeatherService): CitiesRepository =
        LocalCitiesRepository(NetworkCitiesRepository(service))

    @Singleton
    @Provides
    fun provideFavoriteLocationRepository(@ApplicationContext context: Context): FavoriteLocationRepository =
        LocalFavoriteRepository(context)

    @Singleton
    @Provides
    fun provideWeatherRepository(service: WeatherService): WeatherRepository =
        NetworkWeatherRepository(service)

    @Singleton
    @Provides
    fun provideLocationRepository(@ApplicationContext context: Context): LocationRepository =
        LocalLocationRepository(context)
}

@Module
@InstallIn(ActivityRetainedComponent::class)
object SearchCityModule {
    @Provides
    fun provideSearchCityFromListUseCase(): SearchCityFromListUseCase =
        SearchCityFromListUseCase()
}
