package com.example.weatherapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.weatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val binding:ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var cityName=binding.searchV.toString()
        fetchWeatherData(cityName)
    }

    private fun fetchWeatherData(cityName:String) {
       val retrofit= Retrofit.Builder()
           .addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.openweathermap.org/data/2.5/")
           .build().create(Apiinterface::class.java)
        val response=retrofit.getWeatherData(cityName,"1cc57b65bcb38adfd6ff91503fc40336","metric")
        response.enqueue(object:Callback<weatherapp> {
            override fun onResponse(call: Call<weatherapp>, response: Response<weatherapp>) {
                val responseBody=response.body()
                if(response.isSuccessful&&responseBody!=null)
                {
                    val temperature=responseBody.main.temp
                    val humidity=responseBody.main.humidity
                    val wind=responseBody.wind.speed
                    val sunrise=responseBody.sys.sunrise
                    val sunset=responseBody.sys.sunset
                    val mint=responseBody.main.temp_min
                    val maxt=responseBody.main.temp_max
                    val sealevel=responseBody.main.pressure
                    val condition=responseBody.weather.firstOrNull()?.main?:"Unknown"


                    binding.temperature.text="$temperature C"
                    binding.condition.text=condition
                    binding.maxtV.text= maxt.toString()
                    binding.mintV.text=mint.toString()
                    binding.sunrise.text=sunrise.toString()
                    binding.sunset.text=sunset.toString()
                    binding.sea.text=sealevel.toString()
                    binding.humidity.text=humidity.toString()
                    binding.windspeed.text=wind.toString()

                    binding.today.text= cityName




//                    Log.d("TAG","onResponse: $temperature")


                }
            }

            override fun onFailure(call: Call<weatherapp>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })



    }
}