package com.example.mediconnect.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.mediconnect.common.SharedPreference
import com.example.mediconnect.data.AuthRepo
import com.example.mediconnect.data.AuthRepoImp
import com.example.mediconnect.data.TopicRepo
import com.example.mediconnect.data.TopicRepoImp
import com.example.mediconnect.domain.usecase.ClearDataBaseUseCase
import com.example.mediconnect.domain.usecase.ClearUserUseCase
import com.example.mediconnect.domain.usecase.DeleteRateUseCase
import com.example.mediconnect.domain.usecase.GetListCommentUseCase
import com.example.mediconnect.domain.usecase.GetListRateUseCase
import com.example.mediconnect.domain.usecase.GetListTopicUseCase
import com.example.mediconnect.domain.usecase.GetListUserUseCase
import com.example.mediconnect.domain.usecase.GetTopicByDateUseCase
import com.example.mediconnect.domain.usecase.GetTopicByIdUseCase
import com.example.mediconnect.domain.usecase.GetUserUseCase
import com.example.mediconnect.domain.usecase.SaveCommentUseCase
import com.example.mediconnect.domain.usecase.SaveListTopicUseCase
import com.example.mediconnect.domain.usecase.SaveRateUseCase
import com.example.mediconnect.domain.usecase.SaveTopicUseCase
import com.example.mediconnect.domain.usecase.SaveUserUseCase
import com.example.mediconnect.domain.usecase.UpdateRateUseCase
import com.example.mediconnect.domain.usecase.loginUseCase
import com.example.mediconnect.domain.usecase.registerUseCase
import com.example.mediconnect.feature.AuthViewModel
import com.example.mediconnect.feature.HomeViewModel
import com.example.mediconnect.feature.screens.AdminViewModel
import com.example.mediconnect.feature.screens.HistoryViewModel
import com.example.mediconnect.feature.screens.RateViewModel
import com.example.mediconnect.feature.screens.ScanViewModel
import com.example.mediconnect.feature.screens.SplashViewModel
import com.example.mediconnect.feature.screens.TopicsViewModel
import com.example.mediconnect.local.AppDatabase
import com.example.mediconnect.local.dao.RateDao
import com.example.mediconnect.local.dao.TopicDao
import com.example.mediconnect.local.datastore.DataStoreManager
import com.example.mediconnect.local.source.LocalDataSource
import com.example.mediconnect.local.source.LocalDataSourceImp
import com.example.mediconnect.remote.NetworkModuleFactory
import com.example.mediconnect.remote.api.AuthApi
import com.example.mediconnect.remote.source.RemoteAuthSource
import com.example.mediconnect.remote.source.RemoteAuthSourceImp
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaggerModule {

    @Singleton
    @Provides
    fun provideApiService(): AuthApi {
        return NetworkModuleFactory.makeService()
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: AuthApi): RemoteAuthSource =
        RemoteAuthSourceImp(apiService)

    @Singleton
    @Provides
    fun provideRepository(
        remoteAuthSource: RemoteAuthSource,
        dataStoreManager: DataStoreManager
    ): AuthRepo {
        return AuthRepoImp(remoteAuthSource, dataStoreManager)
    }

    @Singleton
    @Provides
    fun provideSaveUserUseCase(repository: AuthRepo): SaveUserUseCase {
        return SaveUserUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideClearUsersUseCase(repository: AuthRepo): ClearUserUseCase {
        return ClearUserUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetUsersUseCase(repository: AuthRepo): GetListUserUseCase {
        return GetListUserUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideLoginUseCase(repository: AuthRepo): loginUseCase {
        return loginUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideRegisterUseCase(repository: AuthRepo): registerUseCase {
        return registerUseCase(repository)
    }

    @Provides
    fun provideViewModelFactory(
        loginUseCase: loginUseCase,
        registerUseCase: registerUseCase,

        ): AuthViewModel {
        return AuthViewModel(loginUseCase, registerUseCase)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(
        postDAO: TopicDao,
        rateDao: RateDao
    ): LocalDataSource {
        return LocalDataSourceImp(postDAO, rateDao)
    }

    @Singleton
    @Provides
    fun provideTopicRepository(
        remoteAuthSource: RemoteAuthSource,
        localDataSource: LocalDataSource
    ): TopicRepo {
        return TopicRepoImp(localDataSource, remoteAuthSource)
    }

    @Singleton
    @Provides
    fun provideSaveTopicUseCase(repository: TopicRepo): SaveTopicUseCase {
        return SaveTopicUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSaveRateUseCase(repository: TopicRepo): SaveRateUseCase {
        return SaveRateUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetListTopicUseCase(repository: TopicRepo): GetListTopicUseCase {
        return GetListTopicUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetListRateUseCase(repository: TopicRepo): GetListRateUseCase {
        return GetListRateUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetUserUseCase(repository: AuthRepo): GetUserUseCase {
        return GetUserUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideClearUseCase(repository: TopicRepo): ClearDataBaseUseCase {
        return ClearDataBaseUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteRateUseCase(repository: TopicRepo): DeleteRateUseCase {
        return DeleteRateUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateRateUseCase(repository: TopicRepo): UpdateRateUseCase {
        return UpdateRateUseCase(repository)
    }


    @Singleton
    @Provides
    fun provideSaveListTopicUseCase(repository: TopicRepo): SaveListTopicUseCase {
        return SaveListTopicUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetListTopicByIdUseCase(repository: TopicRepo): GetTopicByIdUseCase {
        return GetTopicByIdUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetListTopicByDateUseCase(repository: TopicRepo): GetTopicByDateUseCase {
        return GetTopicByDateUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSaveCommentUseCase(repository: TopicRepo): SaveCommentUseCase {
        return SaveCommentUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetCommentsByRateUseCase(repository: TopicRepo): GetListCommentUseCase {
        return GetListCommentUseCase(repository)
    }

    @Provides
    fun provideViewModelTopic(
        saveTopicUseCase: SaveTopicUseCase,
        saveListTopicUseCase: SaveListTopicUseCase,
        getListTopicUseCase: GetListTopicUseCase,
        clearDataBaseUseCase: ClearDataBaseUseCase,
    ): TopicsViewModel {
        return TopicsViewModel(
            saveTopicUseCase,
            saveListTopicUseCase,
            getListTopicUseCase,
            clearDataBaseUseCase
        )
    }

    @Provides
    fun provideViewModelHistory(
        getListTopicUseCase: GetListTopicUseCase,
        saveRateUseCase: SaveRateUseCase,
        saveCommentUseCase: SaveCommentUseCase,
        getTopicByIdUseCase: GetTopicByIdUseCase
    ): HistoryViewModel {
        return HistoryViewModel(
            getListTopicUseCase,
            saveRateUseCase,
            saveCommentUseCase,
            getTopicByIdUseCase
        )
    }

    @Provides
    fun provideViewModelAdmin(
        getTopicByDateUseCase: GetTopicByDateUseCase,
        getListUserUseCase: GetListUserUseCase
    ): AdminViewModel {
        return AdminViewModel(getTopicByDateUseCase, getListUserUseCase)
    }

    @Provides
    fun provideViewModelRate(
        getListRateUseCase: GetListRateUseCase,
        deleteRateUseCase: DeleteRateUseCase,
        updateRateUseCase: UpdateRateUseCase,
        saveCommentUseCase: SaveCommentUseCase,
        getListCommentUseCase: GetListCommentUseCase
    ): RateViewModel {
        return RateViewModel(
            getListRateUseCase,
            deleteRateUseCase,
            updateRateUseCase,
            saveCommentUseCase,
            getListCommentUseCase
        )
    }

    @Provides
    fun provideViewModelHome(
        clearDataBaseUseCase: ClearDataBaseUseCase,
        dataStoreManager: DataStoreManager

    ): HomeViewModel {
        return HomeViewModel(clearDataBaseUseCase, dataStoreManager)
    }

    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreference =
        SharedPreference(context)

    /**
     * Provides [AppDatabase] instance
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    /**
     * Provides [PostDAO] instance
     */
    @Provides
    @Singleton
    fun providePostDAO(appDatabase: AppDatabase) = appDatabase.postDao()

    /**
     * Provides [TopicDAO] instance
     */
    @Provides
    @Singleton
    fun provideTopicDAO(appDatabase: AppDatabase) = appDatabase.topicDao()

    /**
     * Provides [RateDAO] instance
     */
    @Provides
    @Singleton
    fun provideRateDAO(appDatabase: AppDatabase) = appDatabase.rateDao()


    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideBarCodeOptions(): GmsBarcodeScannerOptions {
        return GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
    }

    @Provides
    @Singleton
    fun provideBarCodeScanner(
        context: Context,
        options: GmsBarcodeScannerOptions
    ): GmsBarcodeScanner {
        return GmsBarcodeScanning.getClient(context, options)
    }

    @Provides
    fun provideViewModelScan(
        scanner: GmsBarcodeScanner,
        saveUserUseCase: SaveUserUseCase,
        getListUserUseCase: GetListUserUseCase,
        getUserUseCase: GetUserUseCase
    ): ScanViewModel {
        return ScanViewModel(scanner, saveUserUseCase, getListUserUseCase, getUserUseCase)
    }

    @Provides
    fun provideViewModelSplash(
        saveUserUseCase: SaveUserUseCase,
        getListUserUseCase: GetListUserUseCase
    ): SplashViewModel {
        return SplashViewModel(saveUserUseCase, getListUserUseCase)
    }
}


