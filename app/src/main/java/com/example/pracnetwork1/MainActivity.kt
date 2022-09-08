package com.example.pracnetwork1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.pracnetwork1.databinding.ActivityMainBinding
import com.example.pracnetwork1.models.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var API_KEY = "cfbc9e9de5829ddf92de290a5dd84969"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //버튼 클릭 시 edittext에 입력한 값을 가져와 api를 호출할 수 있도록 함
        binding.activityMainBtn.setOnClickListener {
            val cityName = binding.activityMainEt.text.toString()
            getWeatherData(cityName, API_KEY)
        }
    }

    private fun getWeatherData(city : String, key : String) {
        //앞서 구현했던 WeatherInterface 구현
        val weatherInterface = RetrofitClient.sRetrofit.create(WeatherInterface::class.java)
        //enqueue()메소드를 통해 비동기적으로 네트워크 통신을 진행
        weatherInterface.getWeather(city, key).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse( // response를 정상적으로 잘 받았을 때
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body() as WeatherResponse
                    binding.activityMainTv.text = result.main.temp.toString() + "도"
                } else {
                    Log.d(
                        "MainActivity",
                        "getWeatherData - onResponse : Error code ${response.code()}"
                    )
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) { // 실패했을 때
                Log.d("MainActivity", t.message ?: "통신 오류")
            }
        })
    }
}