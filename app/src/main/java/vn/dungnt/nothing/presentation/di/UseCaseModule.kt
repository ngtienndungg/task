package vn.dungnt.nothing.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vn.dungnt.nothing.data.repositories.BookRepositoryImpl
import vn.dungnt.nothing.data.repositories.LoginRepositoryImpl
import vn.dungnt.nothing.domain.usecases.BookUseCase
import vn.dungnt.nothing.domain.usecases.LoginUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideLoginUseCase(loginRepositoryImpl: LoginRepositoryImpl): LoginUseCase {
        return LoginUseCase(loginRepositoryImpl)
    }

    @Singleton
    @Provides
    fun provideBookUseCase(bookRepositoryImpl: BookRepositoryImpl): BookUseCase {
        return BookUseCase(bookRepositoryImpl)
    }
}