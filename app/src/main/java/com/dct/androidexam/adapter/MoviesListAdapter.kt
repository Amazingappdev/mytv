package com.dct.androidexam.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.dct.androidexam.R
import com.dct.androidexam.activity.MoviesDetailsPage
import com.dct.androidexam.model.MoviesListModel
import com.dct.util.Constant
import com.squareup.picasso.Picasso

class MoviesListAdapter (val context: Context,private var moviesListModelArrayList: ArrayList<MoviesListModel>): RecyclerView.Adapter<MoviesListAdapter.ItemViewHolder>() {
    // on below line we are passing variables as course list and context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_adapter, parent, false)
        return ItemViewHolder(view)
    }
    // method for filtering our recyclerview items.
    fun filterList(filterlist: ArrayList<MoviesListModel>) {
        // below line is to add our filtered
        // list in our course array list.
        moviesListModelArrayList = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // on below line we are setting data to our text view and our image view.
        holder.itemNameTv.text = moviesListModelArrayList.get(position).title

        val path =Constant.URL+moviesListModelArrayList.get(position).strPoster

        Picasso.with(context).load(path).into(holder.itemIV)
        holder.cardView.setOnClickListener(View.OnClickListener {

            val intent = Intent(context, MoviesDetailsPage::class.java)
            intent.putExtra("movieID", moviesListModelArrayList.get(position).movie_id)
            intent.putExtra("title", moviesListModelArrayList.get(position).title)
           context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        // on below line we are returning
        // our size of our list
        return moviesListModelArrayList.size
    }
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // on below line we are initializing our course name text view and our image view.
        val itemNameTv: TextView = itemView.findViewById(R.id.itemNameTv)
        val itemIV: ImageView = itemView.findViewById(R.id.itemIV)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }
}