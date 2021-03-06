package com.e.birdviewtest

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.grid_item.view.*

class GridViewAdapter(
    _context: Context,
    _layout: Int,
    _img: ArrayList<Bitmap>,
    _title: ArrayList<String>,
    _price: ArrayList<String>,
    _id: ArrayList<Int>
) : BaseAdapter() {
    lateinit var context: Context
    var layout: Int = 0
    lateinit var inf: LayoutInflater
    lateinit var img: ArrayList<Bitmap>
    lateinit var title: ArrayList<String>
    lateinit var price: ArrayList<String>
    lateinit var id: ArrayList<Int>


    init {
        context = _context
        layout = _layout
        inf = _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        img = _img
        title = _title
        price = _price
        id = _id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var myconvertView: View? = null
        if (myconvertView == null) {
            myconvertView = inf.inflate(layout, null)
        }
        myconvertView!!.gridView_item_img.setImageBitmap(img.get(position))
        myconvertView!!.gridView_item_title.text = title.get(position)
        myconvertView!!.gridView_item_price.text = price.get(position)

        return myconvertView
    }

    override fun getItem(position: Int): Any {
        return img[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return img.size
    }

}