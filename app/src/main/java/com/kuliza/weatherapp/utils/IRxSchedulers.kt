package com.kuliza.weatherapp.utils

import io.reactivex.Scheduler

interface IRxSchedulers {
  fun main(): Scheduler
  fun io(): Scheduler
}