package com.sorted.news.clases

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sorted.news.R
import java.text.SimpleDateFormat
import java.util.*



@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Adapter(private val article: List<ArticleResponse>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item: ArticleResponse = article[position]
            val source = article[position].source?.name ?: "no name"
            holder.textViewName.text = item.author
            holder.textViewDesc.text = item.description
            holder.textViewTitle.text = item.title
            holder.sourceText.text = source
            Glide.with(holder.imgView.context).load(item.urlToImage).into(holder.imgView)

            val date: String = item.publishedAt

            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val formatterDate = SimpleDateFormat("dd MMM yyyy", Locale("ru"))
            val formatterTime = SimpleDateFormat("HH:mm")
            val pubDate: String = formatterDate.format(parser.parse(date))
            val pubTime: String = formatterTime.format(parser.parse(date))

            holder.publishedAt.text = pubDate
            holder.textViewTime.text = pubTime
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(v)
    }


    override fun getItemCount(): Int {
        return article.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val textViewName = itemView.findViewById(R.id.author) as TextView
        val textViewDesc = itemView.findViewById(R.id.desc) as TextView
        val textViewTitle = itemView.findViewById(R.id.title) as TextView
        val publishedAt = itemView.findViewById(R.id.publishedAt) as TextView
        val textViewTime = itemView.findViewById(R.id.time) as TextView
        val imgView = itemView.findViewById(R.id.img) as ImageView

        val sourceText = itemView.findViewById(R.id.source) as TextView

    }
}