package ru.tinkoff.favouritepersons.presentation.rv_helpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.tinkoff.favouritepersons.R

class PersonListViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val iwAvatar: ImageView = view.findViewById<ImageView>(R.id.person_avatar)
        val twName: TextView = view.findViewById<TextView>(R.id.person_name)
        val twEMail: TextView = view.findViewById<TextView>(R.id.person_email)
        val twPrivateInfo: TextView = view.findViewById<TextView>(R.id.person_private_info)
        val twPhone: TextView = view.findViewById<TextView>(R.id.person_phone)
        val twAddress: TextView = view.findViewById<TextView>(R.id.person_address)
        val twRating: TextView = view.findViewById<TextView>(R.id.person_rating)
}
