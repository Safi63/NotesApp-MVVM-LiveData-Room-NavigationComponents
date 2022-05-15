package com.example.notesapp.ui.Fragments

import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.Model.Notes
import com.example.notesapp.R
import com.example.notesapp.ViewModel.NotesViewModel
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.ui.Adapter.NotesAdapter

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()
    var oldMyNotes = arrayListOf<Notes>()
    lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        val staggerGridLayoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rcvAllNotes.layoutManager = staggerGridLayoutManager
        viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
            oldMyNotes = notesList as ArrayList<Notes>
            adapter = NotesAdapter(requireContext(), notesList)
            binding.rcvAllNotes.adapter = adapter
        })

        binding.fitlerHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner, { notesList ->
                oldMyNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        binding.fitlerMedium.setOnClickListener {

            viewModel.getMediumNotes().observe(viewLifecycleOwner, { notesList ->
                oldMyNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        binding.fitlerLow.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner, { notesList ->
                oldMyNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        binding.allNotes.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
                oldMyNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        binding.btnAddNotes.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        val item = menu.findItem(R.id.app_bar_search)
        val searchView = item.actionView as SearchView
        searchView.queryHint = "Enter Notes Here..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                notesFiltering(p0)
                return true
            }
        })


        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun notesFiltering(p0: String?) {
        val newFilterdList = arrayListOf<Notes>()
        for(i in oldMyNotes){
            if (i.title.contains(p0!!) || i.subTitle.contains(p0!!)){
                newFilterdList.add(i)
                }
        }
        adapter.filtering(newFilterdList)
    }

}