package com.levendoglu.newsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.levendoglu.newsapp.databinding.FragmentArticleBinding
import com.levendoglu.newsapp.model.Article
import com.squareup.picasso.Picasso

class ArticleFragment : Fragment() {
    private lateinit var binding:FragmentArticleBinding
    private lateinit var article:Article
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentArticleBinding.inflate(inflater,container,false)
        setInitialData()
        return binding.root
    }

    private fun setInitialData() {
        val jsonNote = arguments?.getString("title")
        if (jsonNote != null){
            article = Gson().fromJson(jsonNote,Article::class.java)
            article.let {
                binding.articleTitle.text = it.title
                binding.articleContent.text= it.content
                binding.articleAuthor.text = it.author
                binding.articlePublish.text = it.publishedAt
                Picasso.get()
                    .load(it.urlToImage)
                    .into(binding.articleImage)
            }
        }
    }
}