package com.randomanimals.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.randomanimals.R
import com.randomanimals.data.model.Animal
import com.randomanimals.databinding.AnimalListRowBinding

class AnimalsAdapter(private var animalList: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalsAdapter.AnimalViewHolder>() {

    private var listItemClickListener: ListItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): AnimalViewHolder {
        val binding =
            AnimalListRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: AnimalViewHolder, position: Int) {
        viewHolder.bind(animalList[position])
        viewHolder.itemView.setOnClickListener {
            listItemClickListener?.onItemClick(animalList[position])
        }
    }

    override fun getItemCount(): Int {
        return animalList.size
    }

    fun setAnimalList(animalList: ArrayList<Animal>) {
        this.animalList = animalList
        notifyDataSetChanged()
    }

    fun setListItemClickListener(listener: ListItemClickListener) {
        listItemClickListener = listener
    }

    inner class AnimalViewHolder(private val binding: AnimalListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(animal: Animal) {
            Glide.with(itemView)
                .applyDefaultRequestOptions(RequestOptions().placeholder(android.R.drawable.ic_menu_gallery))
                .load(animal.imageLink).fitCenter().error(android.R.drawable.ic_menu_report_image)
                .into(binding.ivAnimal)

            binding.tvName.text = animal.name
            binding.tvLatinName.text = animal.latinName
            binding.tvDiet.text = itemView.context.getString(R.string.diet_details, animal.diet)
            binding.tvLifeSpan.text = itemView.context.getString(R.string.lifespan_details,
                getLifeSpanComment(animal.lifespan))
        }

        private fun getLifeSpanComment(lifespan: Int): String {
            return when (lifespan) {
                1 -> {
                    itemView.context.getString(R.string.lifespan_comment_yr_not_very_long, lifespan)
                }
                in 2..10 -> {
                    itemView.context.getString(R.string.lifespan_comment_yrs_not_very_long,
                        lifespan)
                }
                in 11..20 -> {
                    itemView.context.getString(R.string.lifespan_comment_yrs_kind_of_average,
                        lifespan)
                }
                else -> {
                    itemView.context.getString(R.string.lifespan_comment_yrs_a_long_time, lifespan)
                }
            }
        }
    }

    interface ListItemClickListener {
        fun onItemClick(animal: Animal)
    }
}