package com.example.assignment

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.assignment.databinding.ActivityAdditionBinding
import com.example.assignment.model.ApiData
import com.example.assignment.model.Repos
import com.example.assignment.model.service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Addition : AppCompatActivity() {
    private lateinit var binding: ActivityAdditionBinding
    private lateinit var repos: Repos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdditionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fetchbtn.setOnClickListener {
            binding.fetchbtn.visibility = View.INVISIBLE
            binding.donebtn.visibility = View.VISIBLE
            val ownerName = binding.ownerTv.text.toString()
            val repoName = binding.repoTv.text.toString()
            val data = service.instance.getApiData(ownerName, repoName)
            data.enqueue(object : Callback<ApiData> {
                override fun onResponse(call: Call<ApiData>, response: Response<ApiData>) {
                    val data = response.body()
                    if (data != null) {

                        binding.tv.text = data.description
                        binding.url.text = data.html_url
                        binding.url.visibility = View.INVISIBLE

                    }
                }

                override fun onFailure(call: Call<ApiData>, t: Throwable) {
                    Log.d("2nd Activity", "Data is not being fetched")
                }

            })
        }

        binding.donebtn.setOnClickListener {
            val ownerName = binding.ownerTv.text.toString()
            val repoName = binding.repoTv.text.toString()
            val desc = binding.tv.text.toString()
            val url = binding.url.text.toString()
            repos = Repos(null, ownerName, repoName, desc, url)

            if (ownerName.isNotEmpty() && repoName.isNotEmpty()) {
                val intent = Intent()
                intent.putExtra("repos", repos)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Please fill both the fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backbtn.setOnClickListener {
          onBackPressed()
        }


    }

}