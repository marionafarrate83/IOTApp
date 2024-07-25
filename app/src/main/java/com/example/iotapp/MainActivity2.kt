package com.example.iotapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.iotapp.databinding.ActivityMain2Binding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import retrofit2.Response
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    @RequiresApi(Build.VERSION_CODES.O)
    private fun timestampToFormattedDate(timestamp: Long): String {
        val instant = Instant.ofEpochSecond(timestamp)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault())
        return formatter.format(instant)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val retrofitService = RetrofitInstance.getRetrofitInstance().create(WeatherService::class.java)

        var actionVentilas = ""
        var actionAspersores = ""
        var actionCasasombra = ""

        val responseLiveData: LiveData<Response<WeatherItem>> =
            liveData{
                val response = retrofitService.getWeather()
                emit(response)
            }

        responseLiveData.observe(this) {
            //weatherItem

            val weatherItemCoord = it.body()?.coord
            val weatherLon = "Lon: ${weatherItemCoord?.lon}\n"
            val weatherLat = "Lat: ${weatherItemCoord?.lat}\n"
            binding.lonTextView.append(weatherLon)
            binding.latTextView.append(weatherLat)

            var weatherListItemMain = ""
            var weatherHumidityVal = ""
            var weatherTempVal = ""

            val weatherList = it.body()?.weather?.listIterator()
            if (weatherList != null){
                val weatherListItem = weatherList?.next()
                val weatherListItemId = "Weather ID: ${weatherListItem?.id}\n"
                binding.weatherListItemIdTextView.append(weatherListItemId)
                if (weatherListItem != null) {
                    weatherListItemMain = weatherListItem.main
                }
                binding.weatherListItemMainTextView.append(weatherListItemMain)
                val weatherListItemDescription = "Description: ${weatherListItem?.description}\n"
                binding.weatherListItemDescriptionTextView.append(weatherListItemDescription)
                val weatherListItemIcon = "Icon: ${weatherListItem?.icon}\n"
                binding.weatherListItemIconTextView.append(weatherListItemIcon)
                val weatherimageUrl = "https://openweathermap.org/img/w/${weatherListItem?.icon}.png"
                Picasso.get()
                    .load(weatherimageUrl)
                    .into(binding.weatherListItemIconImageView, object : Callback {
                        override fun onSuccess() {
                            Log.d("Picasso", "success")
                        }
                        override fun onError(e: Exception?) {
                            Log.d("Picasso", "error")
                        }
                    })
            }

            val weatherItemMain = it.body()?.main
            val weatherTemp = "Temp: ${weatherItemMain?.temp} °C\n"
            if (weatherItemMain != null) {
                weatherTempVal = weatherItemMain.temp.toString()
            }
            val weatherHumidity = "Humidity: ${weatherItemMain?.humidity} %\n"
            weatherHumidityVal = weatherItemMain?.humidity.toString()
            binding.weatherMainTempTextView.append(weatherTemp)
            binding.weatherMainHumidityTextView.append(weatherHumidity)

            val weatherItemWind = it.body()?.wind
            val weatherWindSpeed = "Wind Speed: ${weatherItemWind?.speed} Km/h\n"
            binding.weatherWindSpeedTextView.append(weatherWindSpeed)

            val weatherItemDt = it.body()?.dt
            val weatherDt = weatherItemDt?.let { it1 -> timestampToFormattedDate(it1)}
            binding.weatherDtTextView.append(weatherDt)

            if (weatherHumidityVal > "60"){
                actionVentilas = "open"
            } else {
                actionVentilas = "closed"
            }

            if ( binding.weatherListItemMainTextView.text.toString() == "Rain"){
                actionAspersores = "closed"
            } else {
                actionAspersores = "open"
            }

            if (weatherTempVal > "25"){
                actionCasasombra = "closed"
            } else {
                actionCasasombra = "open"
            }

        }

        val boton = this.findViewById<View>(R.id.actionsBtn) as Button
        boton.setOnClickListener {
            val intent = Intent(this, ActionsActivity::class.java)
            intent.putExtra("actionVentilas", actionVentilas)
            intent.putExtra("actionAspersores", actionAspersores)
            intent.putExtra("actionCasasombra", actionCasasombra)
            this.startActivity(intent)
        }
    }
}