package com.e.birdviewtest

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.grid_item.view.*

class GridViewAdapter(_context : Context, _layout : Int, _img : ArrayList<Bitmap>, _title : ArrayList<String>, _price : ArrayList<String>) : BaseAdapter() {
    lateinit var context : Context
    var layout : Int = 0
    lateinit var inf : LayoutInflater
    lateinit var img : ArrayList<Bitmap>
    lateinit var title : ArrayList<String>
    lateinit var price : ArrayList<String>


    init {
        context = _context
        layout = _layout
        inf = _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        img = _img
        title = _title
        price = _price
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //To change body of created functions use File | Settings | File Templates.
        var myconvertView : View? = null
        if (myconvertView == null){
            myconvertView = inf.inflate(layout,null)
        }
        myconvertView!!.gridView_item_img.setImageBitmap(img.get(position))
        myconvertView!!.gridView_item_title.text = title.get(position)
        myconvertView!!.gridView_item_price.text = price.get(position)

        return myconvertView
    }

    override fun getItem(position: Int): Any {
        //To change body of created functions use File | Settings | File Templates.
        return img[position]
    }

    override fun getItemId(position: Int): Long {
        //To change body of created functions use File | Settings | File Templates.
        return position.toLong()
    }

    override fun getCount(): Int {
        //To change body of created functions use File | Settings | File Templates.
        return img.size
    }

}