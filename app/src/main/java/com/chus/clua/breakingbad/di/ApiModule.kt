package com.chus.clua.breakingbad.di

import com.chus.clua.breakingbad.BuildConfig
import com.chus.clua.breakingbad.data.network.api.BreakingBadApi
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


val apiModule = module {

    single {
        get<Retrofit>(
            qualifier = named(DEFAULT_RETROFIT_CLIENT),
            parameters = { parametersOf(BuildConfig.BASE_URL) }
        ).create(BreakingBadApi::class.java)
    }

}
