package com.kuliza.weatherapp.utils

/**
 * Created by rohilchodankar on 4/22/18.
 */
fun String?.addDegrees() = StringBuilder().append(this).append(0x00B0.toChar()).toString()