package ru.tinkoff.favouritepersons.presentation.rv_helpers

import androidx.recyclerview.widget.DiffUtil
import ru.tinkoff.favouritepersons.domain.PersonItem

class PersonItemDiffCallback : DiffUtil.ItemCallback<PersonItem>() {
    override fun areItemsTheSame(oldItem: PersonItem, newItem: PersonItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PersonItem, newItem: PersonItem): Boolean {
        return oldItem == newItem

    }
}