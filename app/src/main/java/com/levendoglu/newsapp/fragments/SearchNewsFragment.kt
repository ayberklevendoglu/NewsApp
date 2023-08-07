package com.levendoglu.newsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.levendoglu.newsapp.Adapter
import com.levendoglu.newsapp.api.NewsApiService
import com.levendoglu.newsapp.databinding.FragmentSearchNewsBinding
import com.levendoglu.newsapp.model.Article
import com.levendoglu.newsapp.model.NewsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchNewsFragment : Fragment() {
    private lateinit var binding:FragmentSearchNewsBinding
    private val apiKey = "a4845d0f4b0845cb9c509e82928ff4c9"
    private lateinit var article: ArrayList<Article>
    private lateinit var adapter: Adapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchNewsBinding.inflate(inflater,container,false)
        binding.search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearch.setHasFixedSize(true)
    }
    private fun filterList(query:String?){
        if (query != null){
            val call: Call<NewsModel> = NewsApiService.api.searchNews(query,apiKey)
            call.enqueue(object : Callback<NewsModel> {
                override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
                    if (response.isSuccessful && response.body() != null){
                        response.body()?.let {
                            val data = response.body()!!.articles
                            article = ArrayList(data)
                            adapter = Adapter(article)
                            binding.rvSearch.adapter = adapter
                        }
                    }
                }
                override fun onFailure(call: Call<NewsModel>, t: Throwable) {
                    Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}