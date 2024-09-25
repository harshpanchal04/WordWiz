package com.example.wordwhiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wordwhiz.databinding.ActivityMainBinding
import com.example.wordwhiz.databinding.MeaningRecyclerRowBinding

class MeaningAdapter(private var meaningList: List<Meaning>): RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {


    class MeaningViewHolder(private val binding: MeaningRecyclerRowBinding): RecyclerView.ViewHolder(
        binding.root
    ){
        fun bind(meaning:Meaning){
            binding.partOfSpeechTextview.text=meaning.partOfSpeech
            binding.definitionsTextview.text=meaning.definitions.joinToString("\n\n") {
                var index=meaning.definitions.indexOf(it)
                (index+1).toString()+". "+   it.definition.toString()
            }
            if(meaning.synonyms.isEmpty()){
                binding.synonymsTitleTextview.visibility=View.GONE
                binding.synonymsTextview.visibility=View.GONE
            }
            else{
                binding.synonymsTitleTextview.visibility=View.VISIBLE
                binding.synonymsTextview.visibility=View.VISIBLE
                binding.synonymsTextview.text=meaning.synonyms.joinToString(", ")
            }

            if(meaning.synonyms.isEmpty()){
                binding.antonymsTitleTextview.visibility=View.GONE
                binding.antonymsTextview.visibility=View.GONE
            }
            else{
                binding.antonymsTextview.visibility=View.VISIBLE
                binding.antonymsTitleTextview.visibility=View.VISIBLE
                binding.antonymsTextview.text=meaning.synonyms.joinToString(", ")
            }
        }
    }

    fun updateNewData(newMeaningList:List<Meaning>){
        meaningList=newMeaningList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MeaningViewHolder {
        val binding=MeaningRecyclerRowBinding.inflate(LayoutInflater.from(p0.context),p0,false)
        return MeaningViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return meaningList.size
    }

    override fun onBindViewHolder(p0: MeaningViewHolder, p1: Int) {
        p0.bind(meaningList[p1])
    }
}

