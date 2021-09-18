package com.lhmzhou.databindingDemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.lhmzhou.databindingDemo.R
import com.lhmzhou.databindingDemo.data.ShoeEntry
import com.lhmzhou.databindingDemo.databinding.AddShoeBinding
import com.lhmzhou.databindingDemo.utils.provideRepository
import com.lhmzhou.databindingDemo.viewmodel.AddShoeViewModel
import com.lhmzhou.databindingDemo.viewmodel.AddShoeViewModelFactory
import org.jetbrains.anko.toast

class AddShoeFragment : androidx.fragment.app.Fragment() {

    private lateinit var binding: AddShoeBinding
    private lateinit var mViewModel : AddShoeViewModel

    init {
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_shoe, container, false
        )
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //If there is no id specified in the arguments, then it should be a new shoe
        val chosenShoe : ShoeEntry? = arguments?.getParcelable(CHOSEN_SHOE)

        //Get the view model instance and pass it to the binding implementation
        val factory = AddShoeViewModelFactory(provideRepository(requireContext()), chosenShoe)
        mViewModel = ViewModelProviders.of(this, factory).get(AddShoeViewModel::class.java)

        binding.viewModel = mViewModel

        binding.fab.setOnClickListener {
            saveShoe()
        }
    }

    private fun saveShoe() {
        // Check if shoe name is not empty
        if(mViewModel.shoeBeingModified.shoeName.isNullOrBlank()){
            context?.toast(R.string.shoe_empty_warning)
            return
        }
        mViewModel.saveShoe()
        fragmentManager?.popBackStack()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //If up button is clicked, check for changes before popping the fragment off
        if (item.itemId == android.R.id.home) {
            onBackClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    /*This can be triggered either by up or both buttons. In both cases,
    we first need to check whether there are unsaved changes and warn the user if necessary*/
    fun onBackClicked(){
        if(mViewModel.isChanged){
            openAlertDialog()
        } else {
            fragmentManager?.popBackStack()
        }
    }

    private fun openAlertDialog(){
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.unsaved_changes_warning_title))
            .setMessage(getString(R.string.unsaved_changes_warning_message))
            // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                // Continue with back operation
                fragmentManager?.popBackStack()
            }
            // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

}