package com.sector.travelmanager.description

import android.os.AsyncTask
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.sector.travelmanager.R
import com.sector.travelmanager.`object`.State
import com.sector.travelmanager.databinding.FragmentDescriptionBinding
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.net.URL

class DescriptionFragment : Fragment() {
    private lateinit var binding: FragmentDescriptionBinding

    private val args by navArgs<DescriptionFragmentArgs>()

    private lateinit var city: String
    val apiKey = "214dc88e981d47bfaaf90939ee82d9b4"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescriptionBinding.inflate(layoutInflater, container, false)

        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move)

        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgs()
        WeatherTask().execute()
    }

    inner class WeatherTask(): AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {

            return URL("https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&appid=$apiKey").readText(
                Charsets.UTF_8
            )
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            val jsonObj = JSONObject(result!!)
            val main = jsonObj.getJSONObject("main")

            val temp = main.getString("temp")
            val newTemp = temp.substring(0, temp.length - 3) + "°C"

            binding.tvTemperature.text = newTemp
        }
    }

    private fun getArgs() {
        binding.apply {
            tvAttraction.text = args.currentAttraction.name
            tvBasicInfo.text = args.currentAttraction.basicInformation
            tvHistory.text = args.currentAttraction.history
            tvLocation.text = args.currentAttraction.location
            city = args.currentAttraction.city

            Picasso.with(requireContext())
                .load(args.currentAttraction.image)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(ivAttraction)
        }
    }
}