package com.levendoglu.newsapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.levendoglu.newsapp.databinding.BreakingNewsCardBinding
import com.levendoglu.newsapp.model.Article
import com.squareup.picasso.Picasso

class Adapter(private val newsList: List<Article>, private val onNewsClick: (Article) -> Unit):RecyclerView.Adapter<Adapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(private val binding:BreakingNewsCardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(article: Article){
            binding.textTitle.text = article.title
            binding.textPublished.text = article.publishedAt
            binding.textAuthor.text = article.author
            Picasso.get().load(article.urlToImage)
                .resize(800,400)
                .centerCrop()
                .into(binding.bnewsImage)
            binding.root.setOnClickListener {
                onNewsClick(article)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BreakingNewsCardBinding.inflate(inflater,parent,false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = newsList[position]
        article.let {
            holder.bind(it)
        }
    }

}