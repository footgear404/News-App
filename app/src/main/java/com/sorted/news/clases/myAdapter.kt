package com.sorted.news.clases
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sorted.news.R


class MyAdapter(val article: List<Article>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val author: Article = article[position]
        val source = article[0].source?.name
        holder.textViewName.text = author.author
        holder.textViewDesc.text = author.description
        holder.textViewTitle.text = author.title

        holder.sourceText.text = source


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(v)
    }


    override fun getItemCount(): Int {
        println(article.size.toString())
        return article.size

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName = itemView.findViewById(R.id.author) as TextView
        val textViewDesc = itemView.findViewById(R.id.desc) as TextView
        val textViewTitle = itemView.findViewById(R.id.title) as TextView
        val imgView = itemView.findViewById(R.id.img) as ImageView
        val sourceText = itemView.findViewById(R.id.source) as TextView
    }
}