package com.example.famousactorsapp

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.famousactorsapp.databinding.FragmentActorCardBinding
import com.example.famousactorsapp.models.FamousActorsItem

private val defaultList = listOf(
    FamousActorsItem(
        "1963-02-17", "male", 1.98,
        "Michael Jordan", "us", 2200000000,
        listOf("basketball_player",
            "athlete", "spokesperson",
            "entrepreneur", "actor"), 61, true))

class ActorAdapter: RecyclerView.Adapter<ActorAdapter.ActorHolder>() {
    var list: List<FamousActorsItem> = defaultList

    class ActorHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = FragmentActorCardBinding.bind(item)

        fun bind(actor: FamousActorsItem) = with(binding) {
            if (actor.gender == "male")
                imageView.setImageResource(R.drawable.male_avatar)
            else if (actor.gender == "female")
                imageView.setImageResource(R.drawable.female_avatar)
            else
                imageView.setImageResource(R.drawable.unknown_person)
            val fullNameParts = actor.name.split(" ")
            val capitalizedFullName = fullNameParts.joinToString(" ") { it?.capitalize() ?: "" }

            actorName.text = "Name: ${capitalizedFullName}"
            actorAge.text = "Age: ${actor.age}"
            actorGender.text = "Gender: ${actor.gender?.capitalize()}"


            moreBtn.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, ActorDescriptionActivity::class.java)

                intent.putExtra("actor_name", actor.name)
                intent.putExtra("actor_age", actor.age)
                intent.putExtra("actor_gender", actor.gender)
                intent.putExtra("actor_height", actor.height)
                intent.putExtra("actor_nationality", actor.nationality)

                if (actor.occupation != null) intent.putStringArrayListExtra("actor_occupation", ArrayList(actor.occupation))
                else intent.putStringArrayListExtra("actor_occupation", ArrayList(listOf("Unknown")))

                if (actor.birthday != null) intent.putExtra("actor_birthday", actor.birthday)
                else intent.putExtra("actor", "Unknown")

                intent.putExtra("actor_is_alive", if (actor.isAlive) "yes" else "no")
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_actor_card, parent, false)
        return ActorHolder(view)
    }

    override fun getItemCount(): Int = list.count()


    override fun onBindViewHolder(holder: ActorHolder, position: Int) {
        holder.bind(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<FamousActorsItem>) {
        list = newList
        notifyDataSetChanged()
    }
}