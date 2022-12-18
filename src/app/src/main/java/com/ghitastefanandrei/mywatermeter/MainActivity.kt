package com.ghitastefanandrei.mywatermeter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ghitastefanandrei.mywatermeter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.value.text = "00065500"
    }
}