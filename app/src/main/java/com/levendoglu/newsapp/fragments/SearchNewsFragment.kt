package com.levendoglu.newsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.levendoglu.newsapp.R
import com.levendoglu.newsapp.adapter.Adapter
import com.levendoglu.newsapp.api.NewsApiService
import com.levendoglu.newsapp.databinding.FragmentSearchNewsBinding
import com.levendoglu.newsapp.model.Article
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchNewsFragment : Fragment() {
    private lateinit var binding:FragmentSearchNewsBinding
    private val apiKey = "a4845d0f4b0845cb9c509e82928ff4c9"
    private lateinit var article: ArrayList<Article>
    private lateinit var adapter: Adapter
    private lateinit var job: Job
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchNewsBinding.inflate(inflater,container,false)

        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearch.setHasFixedSize(true)
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
    private fun filterList(query:String?){
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Toast.makeText(requireContext(),throwable.localizedMessage, Toast.LENGTH_LONG).show()
        }
        if (query != null){
            job = CoroutineScope(Dispatchers.IO + handler).launch {
                val response = NewsApiService.api.searchNews(query,apiKey)
                withContext(Dispatchers.Main){
                    if (response.isSuccessful && response.body() != null){
                        response.body()?.let {
                            val data = response.body()!!.articles
                            article = ArrayList(data)
                            article.let {
                                adapter = Adapter(it,::onNewsClick)
                                binding.rvSearch.adapter = adapter
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onNewsClick(article: Article) {
        val bundle = Bundle()
        bundle.putString("title",Gson().toJson(article))
        findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment,bundle)
    }

}