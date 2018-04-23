package com.kuliza.weatherapp.ui.home

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kuliza.weatherapp.data.model.Forecastday
import com.kuliza.weatherapp.databinding.LayoutItemForecastBinding
import com.kuliza.weatherapp.utils.addDegrees
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Calendar


/**
 * Created by rohilchodankar on 4/22/18.
 */
class ForecastViewHolder(private val binding: LayoutItemForecastBinding) : RecyclerView.ViewHolder(binding.root) {

  private val responseDateFormat =  SimpleDateFormat("yyyy-MM-dd")
  private val displayDateFormat = SimpleDateFormat("dd MMM YYYY")

   fun bindData(forecastday: Forecastday){
      val date = responseDateFormat.parse(forecastday.date)
      val tomorrow = Calendar.getInstance()
      tomorrow.add(Calendar.DATE,1)

      var dateText = ""

      if(DateUtils.isToday(date.time)){
        dateText = "Today"
      } else if(date.compareTo(tomorrow.time) <= 0){
        dateText = "Tomorrow"
      }
      else{
        dateText = displayDateFormat.format(date)
      }


      binding.tvDate.text = dateText
      binding.tvCondition.text = forecastday.day.condition.text;
      binding.tvMinMaxDegrees.text = StringBuilder().append(forecastday.day.mintemp_c.addDegrees())
                                                    .append("/")
                                                    .append(forecastday.day.maxtemp_c.addDegrees())

      if(!forecastday.day.condition.icon.startsWith("http",true)){
         forecastday.day.condition.icon = "http:" + forecastday.day.condition.icon
        Glide.with(binding.root.context).load(forecastday.day.condition.icon).asBitmap()
        .listener(
            object: RequestListener<String, Bitmap> {
               override fun onException(e: Exception?, model: String?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                  return false
               }

               override fun onResourceReady(resource: Bitmap?, model: String?, target: Target<Bitmap>?, isFromMemoryCache: Boolean,
                   isFirstResource: Boolean): Boolean {
                  binding.tvCondition.setCompoundDrawablesWithIntrinsicBounds(BitmapDrawable(binding.root.context.resources,resource),null,null,null)
                  return true
               }
            }
         ).into(100,100)
      }
   }
}