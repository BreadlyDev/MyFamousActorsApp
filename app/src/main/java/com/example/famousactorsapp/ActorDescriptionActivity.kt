package com.example.famousactorsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ActorDescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actor_description)

        val goBackBtn = findViewById<Button>(R.id.go_back_btn)
        goBackBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val fullNameParts = intent.extras!!.getString("actor_name")!!.split(" ")
        val capitalizedFullName = fullNameParts.joinToString(" ") { it?.capitalize() ?: "" }

        val birthday = intent.extras?.getString("actor_birthday")
        if (!birthday.isNullOrEmpty()) {
            val birthdayParts = birthday.split("-")
            if (birthdayParts.size == 3) {
                val normalizedBirthday = birthdayParts.reversed().joinToString(".")
                findViewById<TextView>(R.id.actor_birthday).text = "Birthday: $normalizedBirthday"
            } else
                findViewById<TextView>(R.id.actor_birthday).text = "Birthday: Unknown"

        }

        var gender = intent.extras?.getString("actor_gender")
        if(gender.isNullOrEmpty()) gender = "Unknown"
        else gender.capitalize()

        var nationality = intent.extras?.getString("actor_nationality")
        if(nationality.isNullOrEmpty()) nationality = "Unknown"
        else nationality.uppercase()

        var isAlive = intent.extras?.getString("actor_is_alive")
        if(isAlive.isNullOrEmpty()) isAlive = "Unknown"
        else isAlive.capitalize()

        val occupation = intent.extras!!.getStringArrayList("actor_occupation")!!.joinToString(", ").replace("_", " ")
        var image: Int?
        when (gender) {
            "male" -> image = R.drawable.male_avatar
            "female" -> image = R.drawable.female_avatar
            else -> image = R.drawable.unknown_person
        }

        findViewById<ImageView>(R.id.image_view).setImageResource(image)
        findViewById<TextView>(R.id.actor_name).text = capitalizedFullName
        findViewById<TextView>(R.id.actor_age).text = "Age: ${intent.extras!!.getInt("actor_age")}"
        findViewById<TextView>(R.id.actor_gender).text = "Gender: ${gender}"
        findViewById<TextView>(R.id.actor_height).text = "Height: ${intent.extras!!.getDouble("actor_height")}"
        findViewById<TextView>(R.id.actor_nationality).text = "Nationality: ${nationality}"
        findViewById<TextView>(R.id.actor_is_alive).text = "Is Alive: ${isAlive}"
        findViewById<TextView>(R.id.actor_occupation).text = "Occupation: ${occupation}"
    }
}