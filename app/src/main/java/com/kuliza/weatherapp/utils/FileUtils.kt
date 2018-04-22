package com.kuliza.weatherapp.utils

import android.content.Context
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import com.kuliza.weatherapp.BuildConfig
import java.io.File

object FileUtils {
  fun getFileUri(file: File, context: Context): Uri {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, file)
    } else {
      return Uri.fromFile(file)
    }
  }
}
