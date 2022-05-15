package com.example.notesapp.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.constraintlayout.helper.widget.MotionEffect
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI.navigateUp
import com.example.notesapp.Model.Notes
import com.example.notesapp.R
import com.example.notesapp.ViewModel.NotesViewModel
import com.example.notesapp.databinding.FragmentEditNotesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class EditNotesFragment : Fragment() {

    val oldNotes by navArgs<EditNotesFragmentArgs>()

    lateinit var binding: FragmentEditNotesBinding
    var priority: String = "1"
    val viewModel : NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = FragmentEditNotesBinding.inflate(layoutInflater,container,false)

        binding.editTitle.setText(oldNotes.data.title)
        binding.editSubtitle.setText(oldNotes.data.subTitle)
//        binding.editNotes.setText(oldNotes.data.notes)

        when(oldNotes.data.priority){
            "1"->{
                priority = "1"
                binding.pGreen.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pRed.setImageResource(0)
                binding.pYellow.setImageResource(0)            }

            "2"->{
                priority = "2"
                binding.pYellow.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pRed.setImageResource(0)
                binding.pGreen.setImageResource(0)            }

            "3"->{
                priority = "3"
                binding.pRed.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pYellow.setImageResource(0)
                binding.pGreen.setImageResource(0)            }
        }


            binding.pGreen.setOnClickListener {
                priority = "1"
                binding.pGreen.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pRed.setImageResource(0)
                binding.pYellow.setImageResource(0)
            }
            binding.pYellow.setOnClickListener {
                priority = "2"
                binding.pYellow.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pRed.setImageResource(0)
                binding.pGreen.setImageResource(0)
            }
            binding.pRed.setOnClickListener {
                priority = "3"
                binding.pRed.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pYellow.setImageResource(0)
                binding.pGreen.setImageResource(0)
            }

        binding.btnEditSaveNotes.setOnClickListener {
            updateNotes(it)
        }

        return binding.root
    }

    private fun updateNotes(it: View?) {

        val title = binding.editTitle.text.toString()
        val subTitle = binding.editSubtitle.text.toString()
//        val notes = binding.editNotes.text.toString()

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy", d.time)
        Log.e(MotionEffect.TAG, "updateNotes: " + notesDate)

        val data = Notes(
            oldNotes.data.id,
            title = title,
            subTitle = subTitle,
//            notes = notes,
            date = notesDate.toString(),
            priority
        )
        viewModel.updateNotes(data)
        Toast.makeText(requireContext(),"Note Updated Successfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment)

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_menu){
            val bottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheet.setContentView(R.layout.dialog_delete)

            val textViewYes = bottomSheet.findViewById<TextView>(R.id.noteDialogYes)
            val textViewNo = bottomSheet.findViewById<TextView>(R.id.noteDialogNo)

            textViewYes?.setOnClickListener {
                viewModel.deleteNotes(oldNotes.data.id!!)
                bottomSheet.dismiss()
                findNavController().navigate(R.id.homeFragment)
                Toast.makeText(requireContext(),"Note Deleted Successfully", Toast.LENGTH_SHORT).show()
            }

            textViewNo?.setOnClickListener {
                bottomSheet.dismiss()
            }
            bottomSheet.show()

        }
        return super.onOptionsItemSelected(item)
    }


}