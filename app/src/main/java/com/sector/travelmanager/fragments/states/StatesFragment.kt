package com.sector.travelmanager.fragments.states

import android.app.AlertDialog
import android.content.SharedPreferences
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
import com.sector.travelmanager.adapters.RvStatesAdapter
import com.sector.travelmanager.databinding.FragmentListBinding
import com.sector.travelmanager.preferences.ThemePreferences
import android.os.Parcelable
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.yariksoffice.lingver.Lingver
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*
import kotlin.collections.ArrayList

class StatesFragment : Fragment() {
    private lateinit var binding: FragmentListBinding

    private lateinit var rvAdapter: RvStatesAdapter
    private var statesList = ArrayList<State>()
    private var databaseReferenceStates: DatabaseReference? = null
    private lateinit var sharedPreferences: SharedPreferences
    private var lastPosition: Int? = null

    private lateinit var state: Parcelable
    private val mLayoutManager: LinearLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        checkTheme()
        initDatabase()
        getStates()
        initLayout()

        binding.ibMenu.setOnClickListener {
            openMenu()
        }

        // getLastPositionFromRecyclerView()

        /*binding.rvStates.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                lastPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
            }
        })*/

        return binding.root
    }

    private fun initLayout() {
        binding.rvStates.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getStates() {
        databaseReferenceStates?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (stateSnapshot in snapshot.children) {
                        val state = stateSnapshot.getValue(State::class.java)

                        statesList.add(state!!)
                    }

                    rvAdapter = RvStatesAdapter(statesList)
                    binding.rvStates.adapter = rvAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun initDatabase() {
        FirebaseApp.initializeApp(requireContext());
        databaseReferenceStates = FirebaseDatabase.getInstance().getReference("States")
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
                    //Lingver.getInstance().setLocale(requireContext(), language)
                    //setNewLocale(LANGUAGE_ENGLISH, LANGUAGE_ENGLISH_COUNTRY)
                    dialog.dismiss()
                }

                1 -> {
                    //Lingver.getInstance().setLocale(requireContext(), language)
                    //setNewLocale(LANGUAGE_RUSSIAN, LANGUAGE_RUSSIAN_COUNTRY)
                    dialog.dismiss()
                }
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    /*private fun setNewLocale(language: String, country: String) {
        Lingver.getInstance().setLocale(requireContext(), language, country)
    }*/

    /*private fun getLastPositionFromRecyclerView() {
        val statePrefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        lastPosition = statePrefs.getInt("lastPosition", 0)

        (binding.rvStates.layoutManager as LinearLayoutManager?)!!.scrollToPosition(lastPosition!!)
    }*/

    /*override fun onPause() {
        super.onPause()

        saveStateOfRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()

        val statePrefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val editor: SharedPreferences.Editor = statePrefs.edit()

        editor.putInt("lastPosition", 0)
        editor.apply()
    }*/

    //fun saveRecyclerViewState(parcelable: Parcelable) { state = parcelable }
    //fun restoreRecyclerViewState() : Parcelable = state
    //fun stateInitialized() : Boolean = ::state.isInitialized

    /*private fun saveStateOfRecyclerView() {
        val statePrefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val editor: SharedPreferences.Editor = statePrefs.edit()

        editor.putInt("lastPosition", lastPosition!!)
        editor.apply()
    }*/
}