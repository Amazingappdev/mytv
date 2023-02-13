package com.dct.androidexam.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.android.volley.VolleyError
import com.dct.androidexam.R
import com.dct.androidexam.adapter.MoviesListAdapter
import com.dct.androidexam.adapter.ViewPagerAdapter
import com.dct.androidexam.model.MoviesListModel
import com.dct.androidexam.network.VolleyRestApi
import com.dct.util.Constant
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class TrendingMoviesList : AppCompatActivity() {
    // on below line we are creating variable for view pager,
    // viewpager adapter and the image list.
    lateinit var imgbackbtn: ImageView
    var txtheader: TextView? = null
    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var imageList: List<Int>

    // creating variables for recyclerView
    lateinit var movieListrecyclerView: RecyclerView

    lateinit var moviesListAdapter: MoviesListAdapter
    lateinit var moviesListModelArrayList: ArrayList<MoviesListModel>

    // creating variable for searchView
    lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_list)
        // finding id
        viewPager = findViewById(R.id.idViewPager)
        movieListrecyclerView = findViewById(R.id.recyclerView)
        imgbackbtn = findViewById(R.id.imgbackbtn)
        txtheader = findViewById(R.id.txtheader)
        searchView = findViewById(R.id.idSV)

        // on back press

        imgbackbtn.setOnClickListener(View.OnClickListener { view: View? -> finish() })
        // our image list and adding data to it.
        // here also we can set image from server to list change variable according to requirement
        imageList = ArrayList<Int>()
        imageList = imageList + R.drawable.imgindflag
        imageList = imageList + R.drawable.imgindflag
        imageList = imageList + R.drawable.imgindflag
        imageList = imageList + R.drawable.imgindflag
        imageList = imageList + R.drawable.imgindflag
        // use Picasso to set image


        // on below line we are initializing our view
        // pager adapter and adding image list to it.
        viewPagerAdapter = ViewPagerAdapter(this@TrendingMoviesList, imageList)

        // on below line we are setting
        // adapter to our view pager.
        viewPager.adapter = viewPagerAdapter

        moviesListModelArrayList = ArrayList()
        dataFromServer()
        movieListrecyclerView.layoutManager = GridLayoutManager(this, 3)
        moviesListAdapter = MoviesListAdapter(this@TrendingMoviesList, moviesListModelArrayList)
        movieListrecyclerView.adapter = moviesListAdapter
        // on below line we are adding data to our list
        // note  to add data from server  call the method dataFromServer()

        // on below line we are initializing our adapter


        moviesListAdapter.notifyDataSetChanged()


        // on below line we are adding on query
        // listener for our search view.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // on below line we are checking
                // if query exist or not.
                // filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // if query text is change in that case we
                // are filtering our adapter with
                // new text on below line.
                filter(newText)
                return false
            }
        })
    }

    fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<MoviesListModel> = ArrayList()

        // running a for loop to compare elements.
        for (item in moviesListModelArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.backdrop_path.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            moviesListAdapter.filterList(filteredlist)
        }
    }

    // data from serve
    fun dataFromServer() {
        val uri = java.lang.String.format(
            "https://api.themoviedb.org/3/trending/movie/week?api_key=%1\$s",
            Constant.apiKey
        )
        val volleyRestApi = VolleyRestApi()
        volleyRestApi.volleyWebservice(
            this,
            uri,
            Constant.apiKey,
            object : VolleyRestApi.VolleyCallback {
                override fun onSuccess(result: Any) {
                    // get  details
                    try {
                        val movieslist = JSONObject(result.toString())
                        val movieListArrays: JSONArray = movieslist.getJSONArray("results")
                        for (i in 0 until movieListArrays.length()) {
                            val moviesJson = movieListArrays.getJSONObject(i)
                            val title = moviesJson.getString("title")
                            val movieId = moviesJson.getInt("id")
                            val backdrop_path = moviesJson.getString("backdrop_path")
                            val posterPath = moviesJson.getString("poster_path")
                            moviesListModelArrayList.add(
                                MoviesListModel(
                                    backdrop_path,
                                    posterPath,
                                    title,
                                    movieId
                                )
                            )

                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()

                    }

                }

                override fun onFailure(error: VolleyError?) {

                    // show error details
                }
            })
    }

}
