package com.levendoglu.newsapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.levendoglu.newsapp.model.Article
import com.squareup.picasso.Picasso

class Adapter(private val newsList: List<Article>):RecyclerView.Adapter<Adapter.articleViewHolder>() {
    inner class articleViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.textTitle)
        val publish:TextView = itemView.findViewById(R.id.textPublished)
        val author:TextView = itemView.findViewById(R.id.textAuthor)
        val image:ImageView = itemView.findViewById(R.id.bnewsImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): articleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.breaking_news_card, parent, false)
        return articleViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: articleViewHolder, position: Int) {
        val article = newsList[position]

        holder.apply {
            title.text = article.title
            publish.text = article.publishedAt
            author.text = article.author
            Picasso.get().load(article.urlToImage)
                .resize(800,400)
                .centerCrop()
                .into(image)
        }
    }

}