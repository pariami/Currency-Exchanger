package msi.paria.currencyexchanger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import msi.paria.currencyexchanger.data.datasource.api.ApiService
import msi.paria.currencyexchanger.data.datasource.db.TransactionDB
import msi.paria.currencyexchanger.data.datasource.db.TransactionDao
import msi.paria.currencyexchanger.data.repository.ExchangeRepositoryImp
import msi.paria.currencyexchanger.data.repository.TransactionRepositoryImp
import msi.paria.currencyexchanger.domain.repository.ExchangeRepository
import msi.paria.currencyexchanger.domain.repository.TransactionRepository
import msi.paria.currencyexchanger.domain.usecase.GetExchangeRates
import msi.paria.currencyexchanger.domain.usecase.GetTransactions
import msi.paria.currencyexchanger.domain.usecase.InsertTransaction
import msi.paria.currencyexchanger.domain.usecase.UseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://developers.paysera.com/tasks/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()

    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideExchangeRepository(apiService: ApiService): ExchangeRepository {
        return ExchangeRepositoryImp(apiService)
    }

    @Singleton
    @Provides
    fun provideTransactionRepository(transactionDB: TransactionDB): TransactionRepository {
        return TransactionRepositoryImp(transactionDB)
    }

    @Singleton
    @Provides
    fun provideGetExchangeRatesUseCase(repository: ExchangeRepository): GetExchangeRates {
        return GetExchangeRates(repository)
    }

    @Singleton
    @Provides
    fun provideInsertTransactionUseCase(repository: TransactionRepository): InsertTransaction {
        return InsertTransaction(repository)
    }

    @Singleton
    @Provides
    fun provideGetTransactionsUseCase(repository: TransactionRepository): GetTransactions {
        return GetTransactions(repository)
    }

    @Provides
    @Singleton
    fun provideTransactionDao(@ApplicationContext ctx: Context): TransactionDB {
        return TransactionDB.getDatabase(ctx)
    }

    fun provideUseCase(
        exchangeRepository: ExchangeRepository,
        transactionRepository: TransactionRepository
    ): UseCase {
        return UseCase(
            getExchangeRates = GetExchangeRates(exchangeRepository),
            insertTransaction = InsertTransaction(transactionRepository),
            getTransactions = GetTransactions(transactionRepository),
        )
    }
}