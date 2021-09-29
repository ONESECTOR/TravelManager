package com.sector.travelmanager.fragments.states

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.sector.travelmanager.R
import com.sector.travelmanager.`object`.State
import com.sector.travelmanager.databinding.FragmentListBinding
import com.sector.travelmanager.preferences.ThemePreferences
import androidx.navigation.fragment.findNavController
import java.util.*
import kotlin.collections.ArrayList

class StatesFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private var databaseReferenceStates: DatabaseReference? = null
    private lateinit var adapter: StatesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        setupRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkTheme()
        initDatabase()
        readFromDatabase()

        binding.ibMenu.setOnClickListener {
            openMenu()
        }
    }

    private fun setupRecyclerView() {
        adapter = StatesAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun readFromDatabase() {
        databaseReferenceStates?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val list = ArrayList<State>()

                    for (stateSnapshot in snapshot.children) {
                        val state = stateSnapshot.getValue(State::class.java)

                        list.add(state!!)
                    }

                    adapter.submitList(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun initDatabase() {
        FirebaseApp.initializeApp(requireContext())

        when (Locale.getDefault().language) {
            "ru" -> databaseReferenceStates = FirebaseDatabase.getInstance().getReference("States").child("ru")
            "en" -> databaseReferenceStates = FirebaseDatabase.getInstance().getReference("States").child("en")
        }
    }

    private fun openMenu() {
        binding.drawerLayout.openDrawer(GravityCompat.START)

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.about_app -> {
                    findNavController().navigate(R.id.action_listFragment_to_aboutAppFragment)
                }

                R.id.change_theme -> {
                    chooseThemeDialog()
                }

                R.id.choose_language -> {
                    chooseLanguageDialog()
                }
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun checkTheme() {
        when (ThemePreferences(requireContext()).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                (activity as AppCompatActivity).delegate.applyDayNight()
            }

            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                (activity as AppCompatActivity).delegate.applyDayNight()
            }

            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                (activity as AppCompatActivity).delegate.applyDayNight()
            }
        }
    }

    private fun chooseThemeDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(resources.getString(R.string.choose_theme_dialog_title))

        val themes = arrayOf(
            resources.getString(R.string.light_theme),
            resources.getString(R.string.dark_theme),
            resources.getString(R.string.system_default_theme)
        )

        val checkedItem = ThemePreferences(requireContext()).darkMode

        builder.setSingleChoiceItems(themes, checkedItem) {dialog, which ->
            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    (activity as AppCompatActivity).delegate.applyDayNight()

                    ThemePreferences(requireContext()).darkMode = 0

                    dialog.dismiss()
                }

                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    (activity as AppCompatActivity).delegate.applyDayNight()

                    ThemePreferences(requireContext()).darkMode = 1

                    dialog.dismiss()
                }

                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    (activity as AppCompatActivity).delegate.applyDayNight()

                    ThemePreferences(requireContext()).darkMode = 2

                    dialog.dismiss()
                }
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun chooseLanguageDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(resources.getString(R.string.choose_language_dialog_title))

        val languages = arrayOf(
            resources.getString(R.string.english_language),
            resources.getString(R.string.russian_language)
        )

        val checkedItem = 0

        builder.setSingleChoiceItems(languages, checkedItem) {dialog, which ->
            when(which) {
                0 -> {
                    dialog.dismiss()
                }

                1 -> {
                    dialog.dismiss()
                }
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}