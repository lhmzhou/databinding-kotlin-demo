package com.lhmzhou.databindingDemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.*
import com.lhmzhou.databindingDemo.R
import com.lhmzhou.databindingDemo.data.ShoeEntry
import com.lhmzhou.databindingDemo.data.UIState
import com.lhmzhou.databindingDemo.databinding.FragmentListBinding
import com.lhmzhou.databindingDemo.viewmodel.MainViewModel
import org.jetbrains.anko.design.longSnackbar

const val CHOSEN_SHOE = "chosenShoe"

class ShoeListFragment : androidx.fragment.app.Fragment(), ShoeAdapter.ShoeClickListener {

    private val mViewModel: MainViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
    }
    private lateinit var mAdapter: ShoeAdapter
    private lateinit var binding: FragmentListBinding
    private var mShoeList: List<ShoeEntry>? = null

    init {
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        //Set adapter, divider and default animator to the recycler view
        mAdapter = ShoeAdapter(this)
        val dividerItemDecoration = DividerItemDecoration(
            requireActivity(), LinearLayoutManager.VERTICAL
        )
        with(binding.recycler) {
            addItemDecoration(dividerItemDecoration)
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }

        //When fab clicked, open AddShoeFragment
        binding.fab.setOnClickListener { openAddShoeFrag(AddShoeFragment()) }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        //Get the view model instance and pass it to the binding implementation
        binding.uiState = mViewModel.uiState

        //Claim list of shoes from view model
        mViewModel.shoeList?.observe(this, Observer { shoeEntries ->
            if (shoeEntries.isNullOrEmpty()) {
                mViewModel.uiState.set(UIState.EMPTY)
            } else {
                mViewModel.uiState.set(UIState.HAS_DATA)
                mAdapter.shoeList = shoeEntries
                mShoeList = shoeEntries
            }
        })

        //Attach an ItemTouchHelper for swipe-to-delete functionality
        val coordinator: CoordinatorLayout? = activity?.findViewById(R.id.main_coordinator)
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                //First take a backup of the shoe to erase
                //If user is swiping an item, we can assume that list is not null
                val shoeToErase = mShoeList!![position]

                //Then delete the shoe
                mViewModel.deleteShoe(shoeToErase)

                //Show a snack bar for undoing delete
                coordinator?.longSnackbar(R.string.shoe_is_deleted, R.string.undo){
                    mViewModel.insertShoe(shoeToErase)
                }
            }
        }).attachToRecyclerView(binding.recycler)
    }

    override fun onShoeClicked(chosenShoe: ShoeEntry) {
        //Pass chosen shoe id to the AddShoeFragment
        val args = Bundle()
        args.putParcelable(CHOSEN_SHOE, chosenShoe)
        val frag = AddShoeFragment()
        frag.arguments = args

        //Open AddShoeFragment in edit form
        openAddShoeFrag(frag)
    }

    private fun openAddShoeFrag(frag: AddShoeFragment) {
        fragmentManager?.transaction {
            replace(R.id.main_container, frag)
            addToBackStack(null)
        }
    }
}