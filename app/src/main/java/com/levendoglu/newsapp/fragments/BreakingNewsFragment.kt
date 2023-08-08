package com.levendoglu.newsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.levendoglu.newsapp.R
import com.levendoglu.newsapp.adapter.Adapter
import com.levendoglu.newsapp.api.NewsApiService
import com.levendoglu.newsapp.databinding.FragmentBreakingNewsBinding
import com.levendoglu.newsapp.model.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BreakingNewsFragment : Fragment() {
    private lateinit var binding: FragmentBreakingNewsBinding
    private val apiKey = "a4845d0f4b0845cb9c509e82928ff4c9"
    private lateinit var article: ArrayList<Article>
    private lateinit var adapter: Adapter
    private lateinit var job:Job
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBreakingNewsBinding.inflate(inflater,container,false)

        binding.rvBreakingNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBreakingNews.setHasFixedSize(true)
        getNews()

        return binding.root
    }
    private fun getNews(){
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = NewsApiService.api.getBreakingNews("us",apiKey)
            withContext(Dispatchers.Main){
                if (response.isSuccessful && response.body() != null){
                    response.body()?.let {
                        val data = response.body()!!.articles
                        article = ArrayList(data)
                        article.let {
                            adapter = Adapter(it,::onNewsClick)
                            binding.rvBreakingNews.adapter = adapter
                        }
                    }
                }
            }
        }


        /*val call:Call<NewsModel> = NewsApiService.api.getBreakingNews("us",apiKey)
        call.enqueue(object : Callback<NewsModel>{
            override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
                if (response.isSuccessful && response.body() != null){
                    response.body()?.let {
                        val data = response.body()!!.articles
                        article = ArrayList(data)
                        adapter = Adapter(article,::onNewsClick)
                        binding.rvBreakingNews.adapter = adapter
                    }
                }
            }
            override fun onFailure(call: Call<NewsModel>, t: Throwable) {
                Toast.makeText(requireContext(),t.localizedMessage,Toast.LENGTH_LONG).show()
            }

        })

         */
    }

    private fun onNewsClick(article: Article) {
        val bundle = Bundle()
        bundle.putString("title", Gson().toJson(article))
        findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment,bundle)
    }
    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}