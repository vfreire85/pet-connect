package com.senacgrupovinteecinco.pet_connect.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.senacgrupovinteecinco.pet_connect.R

class PetsAdapter : ListAdapter<Pet, PetsAdapter.PetViewHolder>(DiffCallback()) {

    class PetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Declare suas views aqui (ex: TextView, ImageView)
        // Exemplo:
        // val nameTextView: TextView = view.findViewById(R.id.pet_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pet, parent, false) // Crie um layout item_pet.xml
        return PetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pet = getItem(position)
        // Configure as views do ViewHolder com os dados do pet
        // Exemplo:
        // holder.nameTextView.text = pet.name
    }

    class DiffCallback : DiffUtil.ItemCallback<Pet>() {
        override fun areItemsTheSame(oldItem: Pet, newItem: Pet): Boolean {
            return oldItem.id == newItem.id // Assumindo que Pet tem um ID
        }

        override fun areContentsTheSame(oldItem: Pet, newItem: Pet): Boolean {
            return oldItem == newItem
        }
    }
}