package com.aad.alc4.team10.animatedweatherapp.ui.main.country_screen


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.aad.alc4.team10.animatedweatherapp.R
import com.aad.alc4.team10.animatedweatherapp.model.Country
import com.aad.alc4.team10.animatedweatherapp.model.getCountryPhotoRec
import com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen.createPaletteSync

class MyCountryRecyclerViewAdapter(
    private val mValues: List<Country>
) : RecyclerView.Adapter<MyCountryRecyclerViewAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val item = mValues[position]

        holder.bind(item)
    }


    override fun getItemCount(): Int = mValues.size

    inner class CountryViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        private val photo by lazy { itemView.findViewById<ImageView>(R.id.country_photo_image_view) }
        private val nameTextView by lazy { itemView.findViewById<TextView>(R.id.country_name_text_view) }

        private val card by lazy { mView.findViewById<CardView>(R.id.item_country_card_view) }

        fun bind(co: Country) = co
            .also { photo.setImageResource(it.getCountryPhotoRec()) }
            .apply { nameTextView.text = name }
            .let { createPaletteSync(itemView.context.resources.getDrawable(it.getCountryPhotoRec()).toBitmap()) }
            .run { card.setCardBackgroundColor(getDominantColor(itemView.resources.getColor(R.color.off_white))) }
            .run {

                with(itemView) {
                    setOnClickListener {
                        val action =
                            CountryFragmentDirections.actionCountryFragmentToCityFragment(co)
                        val extras = androidx.navigation.fragment.FragmentNavigatorExtras(
                            photo to "country_photo",
                            nameTextView to "country_name"
                        )
                        findNavController()
                            .navigate(action.actionId, action.arguments, null, extras)

                        //onclick.onClick(position, country_photo_image_view, country_name_text_view)


                    }
                }
            }
    }
}