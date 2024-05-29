package ru.tinkoff.favouritepersons.presentation.rv_helpers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import ru.tinkoff.favouritepersons.domain.PersonItem
import ru.tinkoff.favouritepersons.R
import ru.tinkoff.favouritepersons.domain.SCORES

class PersonListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<PersonItem, PersonListViewHolder>(PersonItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false)
        return PersonListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonListViewHolder, position: Int) {
        val res = holder.itemView.context.resources
        val personItem = getItem(position)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(personItem)
        }

        Glide.with(holder.itemView.context)
            .load(personItem.imageLink)
            .centerCrop()
            .into(holder.iwAvatar)

        holder.twName.text = "${personItem.name} ${personItem.surname}"
        holder.twPrivateInfo.text = "${personItem.gender}, ${personItem.age}"
        holder.twEMail.text = personItem.email
        holder.twPhone.text = personItem.phone
        holder.twAddress.text = personItem.address
        holder.twRating.text = personItem.rating.toString()
        when (personItem.rating) {
            in SCORES.FAIL.range -> holder.twRating.setTextColor( res.getColor(R.color.rating_fail))
            in SCORES.BAD.range -> holder.twRating.setTextColor( res.getColor(R.color.rating_bad))
            in SCORES.NORMAL.range -> holder.twRating.setTextColor( res.getColor(R.color.rating_normal))
            in SCORES.PERFECT.range -> holder.twRating.setTextColor( res.getColor(R.color.rating_perfect))
        }
    }

    class OnClickListener(val clickListener: (personItem: PersonItem) -> Unit) {
        fun onClick(personItem: PersonItem) = clickListener(personItem)
    }

}