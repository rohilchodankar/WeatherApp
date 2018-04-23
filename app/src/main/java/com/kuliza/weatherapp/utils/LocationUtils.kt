package com.kuliza.weatherapp.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper.getMainLooper
import android.provider.Settings
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observable.create
import io.reactivex.Single
import io.reactivex.exceptions.Exceptions
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by rohilchodankar on 4/23/18.
 */
class LocationUtils @Inject constructor(val activity: DaggerAppCompatActivity, val rxPermissions: RxPermissions) {
  private val UPDATE_MIN_TIME: Long = 5000 // Ms
  private val UPDATE_MIN_DISTANCE = 10f // Meters

  private lateinit var locationManager: LocationManager

  /**
   * Request Location Permission
   *
   * @return Single<Boolean> - Location Permission granted or not
   *
   * ** Later might have to integrate rationale permission and check if GPS setting is enabled or not
   */
  fun requestPermission() = requestPermissionInternal()

  /**
   * Get Periodic location updates
   *
   * @return Observable<Location> - Location updates in observable
   */
  fun getLocationUpdates(): Observable<Location> {
    return requestPermissionInternal().map {
      checkAndInitLocationManager()
    }.toObservable()
        .flatMap {
          return@flatMap requestLocationUpdates()
        }
  }

  /**
   * Get Location once
   *
   * @return Single<Location> - Location update in Single
   */
  fun getLocation(): Single<Location> {
    return requestPermissionInternal().map {
      checkAndInitLocationManager()
    }.flatMap {
          return@flatMap requestLocationUpdateOnce()
        }
  }

  //Check if location manager is initialized and initialize
  private fun checkAndInitLocationManager() {
    if (!::locationManager.isInitialized) {
      locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
  }

  /**
   * Request location permission
   */
  private fun requestPermissionInternal(): Single<Boolean> {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return Single.just(true)
    }
    return rxPermissions.request(
        Manifest.permission.ACCESS_COARSE_LOCATION).singleOrError().map {
      if (!it) {
        Exceptions.propagate(Exception("Permission denied"))
      }
      it
    }
  }

  /**
   * Check if Location setting is enabled or not
   */
  private fun checkIfLocationEnabled(): Boolean {
    val locationManager: LocationManager = activity.getSystemService(
        Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(
        LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER)
  }

  /**
   * Start Location settings page
   */
  private fun startEnableLocationActivity() {
    val locationIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    activity.startActivity(locationIntent)
  }

  /**
   * Open App info page to allow user enable location settings,
   * Usually called after checking rationale permission
   */
  private fun openAppInfo() {
    try {
      val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
      intent.data = Uri.parse("package:" + activity.packageName)
      activity.startActivity(intent)

    } catch (e: ActivityNotFoundException) {
      val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
      activity.startActivity(intent)
    }
  }

  @SuppressLint("MissingPermission")
  private fun requestLocationUpdates(): Observable<Location> {
    return create<Location> {
      locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, UPDATE_MIN_TIME,
          UPDATE_MIN_DISTANCE,
          object : LocationListener {
            override fun onLocationChanged(location: Location?) {
              Timber.d("onLocationChanged(location: ${location.toString()})")
              location?.let { it1 -> it.onNext(it1) }
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
              Timber.d("onStatusChanged(provider: $provider, status: $status, extras: $extras)")
            }

            override fun onProviderEnabled(provider: String?) {
              Timber.d("onProviderEnabled(provider: $provider)")
            }

            override fun onProviderDisabled(provider: String?) {
              Timber.d("onProviderDisabled(provider: $provider)")
            }
          }, getMainLooper())
    }
  }

  @SuppressLint("MissingPermission")
  private fun requestLocationUpdateOnce(): Single<Location> {
    return Single.create<Location> {
      locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,
          object : LocationListener {
            override fun onLocationChanged(location: Location?) {
              Timber.d("onLocationChanged(location: ${location.toString()})")
              location?.let { it1 -> it.onSuccess(it1) }
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
              Timber.d("onStatusChanged(provider: $provider, status: $status, extras: $extras)")
            }

            override fun onProviderEnabled(provider: String?) {
              Timber.d("onProviderEnabled(provider: $provider)")
            }

            override fun onProviderDisabled(provider: String?) {
              Timber.d("onProviderDisabled(provider: $provider)")
            }
          }, getMainLooper())
    }
  }
}