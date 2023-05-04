package com.example.assignment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.PopupMenu.OnMenuItemClickListener
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.adapter.ReposAdapter
import com.example.assignment.database.MainDb
import com.example.assignment.databinding.ActivityMainBinding
import com.example.assignment.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), ReposAdapter.click {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: viewModel
    private lateinit var Adapter: ReposAdapter
    private lateinit var database: MainDb
     var Name:String = ""
    var Url:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ui()

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(viewModel::class.java)

        mainViewModel.allData.observe(this) { list ->

            list?.let {
                Adapter.getLiveData(list)
            }
        }

        database = MainDb.getdb(this)

    }

    private fun ui() {

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        Adapter = ReposAdapter(this, this)
        binding.recyclerView.adapter = Adapter

        val getData = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data?.getSerializableExtra("repos") as? Repos
                if (data != null) {
                    mainViewModel.insert(data)
                }
            }
        }

        binding.addbtn.setOnClickListener {
            val intent = Intent(this, Addition::class.java)
            getData.launch(intent)
        }
    }

    override fun onRepoClicked(repos: Repos) {
        val data = service.instance.getApiData(repos.name, repos.owner)
        data.enqueue(object : Callback<ApiData> {
            override fun onResponse(call: Call<ApiData>, response: Response<ApiData>){
                val data = response.body()
                if (data != null) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(data.html_url)
                    startActivity(intent)
                   // dataReq(data.html_url,data.name)
                }
                //  Log.d("data", data.toString())
            }

            override fun onFailure(call: Call<ApiData>, t: Throwable) {
                Log.d("data", "Data is not being fetched")
            }
        })
    }

//    private fun dataReq(htmlUrl: String, name: String) {
//        Name  = name
//        Url = htmlUrl
//    }

    override fun onBtnClicked(repos: Repos) {
        Name = repos.name
        Url = repos.url
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,Name)
        intent.putExtra(Intent.EXTRA_TEXT,Url)
        startActivity(Intent.createChooser(intent,"Share to :"))
    }

    override fun onLongClicked(repos: Repos, cardView: CardView) {
        val popUp = PopupMenu(this,cardView)
        popUp.setOnMenuItemClickListener(object : OnMenuItemClickListener{
            override fun onMenuItemClick(p0: MenuItem?): Boolean {
                if (p0?.itemId == R.id.delete_menu){
                    mainViewModel.delete(repos)
                    return true
                }
                return false
            }

        })
        popUp.inflate(R.menu.deletemenu)
        popUp.show()
    }


}
