package com.example.fragmentbesetpractice

import android.os.Bundle
import android.os.RecoverySystem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.news_title_frag.*

class NewsTitleFragment : Fragment() {
    private var isTwoPlane = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_title_frag, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isTwoPlane = activity?.findViewById<View>(R.id.newsContentLayout) != null

        val layoutManager = LinearLayoutManager(activity)
        newsTitleRecyclerView.layoutManager = layoutManager
        val adapter = NewsAdapter(getNews())
        newsTitleRecyclerView.adapter = adapter
    }

    private fun getNews(): List<News> {
        val newsList = ArrayList<News>()
        for (i in 1..50) {
//            val news = News("This is news title $i", getRandomLengthString("This is news content $i."))
            val news = News("This is news title $i", ("This is news content $i.") * (1..20).random())
            newsList.add(news)
        }
        return newsList
    }

//    2.基于扩展函数和运算符重载写法
//    注意：运算符重载必须添加operator关键字
    operator fun String.times(n: Int) : String = repeat(n)
//    operator fun String.times(n: Int) : String {
//        var builder = StringBuilder()
//        repeat(n) {
//            builder.append(this)
//        }
//        return builder.toString()
//    }

//    1.传统写法
//    private fun getRandomLengthString(str: String) : String{
//        val n = (1..20).random()
//        val builder = StringBuilder()
//        repeat(n) {
//            builder.append(str)
//        }
//        return builder.toString()
//    }

    inner class NewsAdapter(val newsList: List<News>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val newsTitle: TextView = view.findViewById(R.id.newsTitle)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)

            val holder = ViewHolder(view)
            holder.itemView.setOnClickListener {
                val news = newsList[holder.bindingAdapterPosition]
                if (isTwoPlane) {
                    val fragment = newsContentFrag as NewsContentFragment
                    fragment.refresh(news.title, news.content)
                } else {
                    NewsContentActivity.actionStart(parent.context, news.title, news.content)
                }
            }
            return holder
        }

        override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
            val news = newsList[position]
            holder.newsTitle.text = news.title
        }

        override fun getItemCount(): Int = newsList.size

    }

}