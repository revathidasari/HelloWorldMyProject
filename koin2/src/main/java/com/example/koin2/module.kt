package com.example.koin2

import org.koin.dsl.module.module

val appModule = module {
    single { Course() }
    factory { Friend() }
    factory { Student(get(), get()) }
}