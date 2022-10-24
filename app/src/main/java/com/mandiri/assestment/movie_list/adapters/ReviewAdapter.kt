package com.mandiri.assestment.movie_list.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mandiri.assestment.movie_list.R
import com.mandiri.assestment.movie_list.global.URL
import com.mandiri.assestment.movie_list.models.ReviewModels.Review
import java.util.*

class ReviewAdapter (private val onReviewClick: OnReviewClick): RecyclerView.Adapter<ReviewAdapter.ViewHolder>(){
    private var reviewsData : MutableList<Review> = Collections.emptyList<Review?>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_review, parent, false)

        return ViewHolder(view, onReviewClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = reviewsData[position]

        holder.authorName.text = data.author
        holder.score.text = data.author_details.rating.toString()
        holder.content.text = data.content

        val avatarPath = data.author_details.avatar_path
        var url = "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?f=y"
        if(avatarPath != null){
            url = if(avatarPath.contains("https")){
                val arr = avatarPath.split("/")

                URL.BASE_AVATAR_IRL + arr[arr.size - 1]
            } else{
                URL.BASE_AVATAR_IRL + avatarPath
            }
        }

        Glide.with(holder.itemView)
            .load(url)
            .into(holder.avatar)

        if(data.isClicked){
            holder.content.maxLines = Integer.MAX_VALUE
        } else{
            holder.content.maxLines = 3
        }
    }

    override fun getItemCount(): Int {
        return reviewsData.size
    }

    fun setReviewData(reviewsList: List<Review>){
        val startSize = this.reviewsData.size
        this.reviewsData.addAll(reviewsList)
        val newSize = this.reviewsData.size

        notifyItemRangeInserted(startSize, newSize)
    }

    fun getReviewData(position: Int) : Review{
        return reviewsData[position]
    }

    class ViewHolder(itemView: View, onReviewClick: OnReviewClick) : RecyclerView.ViewHolder(itemView) {
        val authorName : TextView = itemView.findViewById(R.id.tv_author_name)
        val score : TextView = itemView.findViewById(R.id.tv_star_score)
        val content : TextView = itemView.findViewById(R.id.tv_review_content)
        val avatar : ImageView = itemView.findViewById(R.id.iv_avatar)
        private val reviewLayout : View = itemView.findViewById(R.id.layout_user_review)

        init {
            reviewLayout.setOnClickListener(View.OnClickListener {
                onReviewClick.onReviewClick(absoluteAdapterPosition)
            })
        }
    }

    fun interface OnReviewClick {
        fun onReviewClick(position: Int)
    }
}