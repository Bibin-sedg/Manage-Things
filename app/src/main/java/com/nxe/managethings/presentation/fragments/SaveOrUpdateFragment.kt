package com.nxe.managethings.presentation.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.MaterialContainerTransform
import com.nxe.managethings.R
import com.nxe.managethings.data.model.Note
import com.nxe.managethings.data.util.hideKeyboard
import com.nxe.managethings.data.viewModel.NoteActivityViewModel
import com.nxe.managethings.databinding.BottomSheetLayoutBinding
import com.nxe.managethings.databinding.FragmentSaveOrUpdateBinding
import com.nxe.managethings.presentation.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date


class SaveOrUpdateFragment : Fragment(R.layout.fragment_save_or_update) {

    private lateinit var navController: NavController
    private lateinit var contentBinding: FragmentSaveOrUpdateBinding
    private var note: Note?= null
    private var color = -1
    private lateinit var result: String
    private val noteActivityViewModel: NoteActivityViewModel by activityViewModels()
    private val currentDate = SimpleDateFormat.getInstance().format(Date())
    private val job = CoroutineScope(Dispatchers.Main)
    private val args: SaveOrUpdateFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = MaterialContainerTransform().apply {
            drawingViewId= R.id.nav_host_fragment_content_main
            scrimColor = Color.TRANSPARENT
            duration= 300L
        }
        sharedElementEnterTransition = animation
        sharedElementReturnTransition= animation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentBinding= FragmentSaveOrUpdateBinding.bind(view)

        navController= Navigation.findNavController(view)
        val activity = activity as MainActivity

        ViewCompat.setTransitionName(
            contentBinding.noteContentFragmentParent,
            "recyclerView_${args.note?.id}"
        )

        contentBinding.lastEdited.text=getString(R.string.edited_on,SimpleDateFormat.getDateInstance().format(Date()))

        contentBinding.saveNote.setOnClickListener{
            saveNote()

        }

        try {
            contentBinding.etNoteContent.setOnFocusChangeListener{_,hasFocus->
                if(hasFocus)
                {
                    contentBinding.bottomBar.visibility= View.VISIBLE
                    contentBinding.etNoteContent.setStylesBar(contentBinding.styleBar)
                }else contentBinding.bottomBar.visibility=View.GONE

            }
        }
        catch (e: Throwable )
        {
            Log.d("TAG",e.stackTraceToString())
        }

        contentBinding.fabColorPick.setOnClickListener{
            val bottomSheetDialog = BottomSheetDialog(
                requireContext(),
                R.style.BottomSheetDialogTheme
            )
            val bottomSheetView: View = layoutInflater.inflate(R.layout.bottom_sheet_layout,null)
            with(bottomSheetDialog)
            {
                setContentView(bottomSheetView)
                show()
            }
            val bottomSheetBinding = BottomSheetLayoutBinding.bind(bottomSheetView  )
            bottomSheetBinding.apply {
                colorPicker.apply {
                    setSelectedColor(color)
                    setOnColorSelectedListener {
                            value->
                        color = value
                        contentBinding.apply {
                            noteContentFragmentParent.setBackgroundColor(color)
                            toolbarFragmentNoteContent.setBackgroundColor(color)
                            bottomBar.setBackgroundColor(color )
                            activity.window.statusBarColor= color
                        }
                        bottomSheetBinding.bottomSheetParent.setCardBackgroundColor(color)

                    }
                }
                bottomSheetParent.setCardBackgroundColor(color)

            }
            bottomSheetView.post{bottomSheetDialog.behavior.state= BottomSheetBehavior.STATE_EXPANDED}
        }

        setUpNote()

    }

    private fun setUpNote() {
        val note=args.note
        val title=contentBinding.etTitle
        val content= contentBinding.etNoteContent
        val lastEdited=contentBinding.lastEdited

        if(note==null)
        {
            contentBinding.lastEdited.text=getString(R.string.edited_on,SimpleDateFormat.getDateInstance().format(Date()))
        }
        if(note!=null)
        {
            title.setText(note.title)
            content.renderMD(note.content)
            lastEdited.text= getString(R.string.edited_on,note.date)
            color=note.color
            contentBinding.apply {
                job.launch {
                    delay(10)
                    noteContentFragmentParent.setBackgroundColor(color)
                }
                toolbarFragmentNoteContent.setBackgroundColor(color)
                bottomBar.setBackgroundColor(color)
            }
            activity?.window?.statusBarColor=note.color
        }
    }

    private fun saveNote() {
        if(contentBinding.etTitle.text.toString().isEmpty())
        {
            Toast.makeText(activity,"Title is Empty",Toast.LENGTH_SHORT).show()
        }
        else
        {
            note= args.note
            when(note)
            {
                null-> {
                    noteActivityViewModel.saveNote(
                        Note(
                            0,
                            contentBinding.etTitle.text.toString(),
                            contentBinding.etNoteContent.getMD(),
                            currentDate,
                            color
                        )
                    )
                    result = "Note Saved"
                    setFragmentResult("key", bundleOf("bundleKey" to result))
                    navController.navigate(SaveOrUpdateFragmentDirections.actionSaveOrUpdateFragmentToNoteFragment())
                }
                else ->{
                        updateNote()
                    navController.popBackStack()
                }
            }

        }
    }

    private fun updateNote() {
        if(note!=null)
        {
            noteActivityViewModel.updateNote(
                Note(
                    note!!.id,
                    contentBinding.etTitle.text.toString(),
                    contentBinding.etNoteContent.getMD(),
                    currentDate,
                    color
                )
            )
        }
    }
}