package com.example.wordwhiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import com.example.wordwhiz.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var adapter: MeaningAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(view.paddingLeft, view.paddingTop, view.paddingRight, systemInsets.bottom)
            insets
        }

        binding.searchButton.setOnClickListener{
            val word=binding.searchInput.text.toString()
            getMeaning(word)
        }

        adapter= MeaningAdapter(emptyList())
        binding.meaningRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.meaningRecyclerView.adapter=adapter

    }

    private fun getMeaning(word: String) {

        setInProgress(true)
        GlobalScope.launch {

            try {val response=RetrofitInstance.dictionaryApi.getMeaning(word)
                if(response.body()==null){
                    throw (Exception())
                }
                runOnUiThread{

                    setInProgress(false)

                    response.body()?.first()?.let{
                        setUI(it)
                    }
                }

            }
            catch (e:Exception) {
                runOnUiThread{
                    setInProgress(false)
                    Toast.makeText(applicationContext,"Something went wrong",Toast.LENGTH_SHORT).show()
                }

                }
            }
        }



    private fun setUI(response:WordResult){
        binding.wordTextView.text=response.word
        binding.phoneticTextView.text=response.phonetic
        adapter.updateNewData(response.meanings)
    }

    private fun setInProgress(inProgress: Boolean){
        if(inProgress){
            binding.progressBar.visibility= View.VISIBLE
            binding.searchButton.visibility=View.INVISIBLE
        }
        else{
            binding.progressBar.visibility=View.INVISIBLE
            binding.searchButton.visibility=View.VISIBLE
        }
    }

}