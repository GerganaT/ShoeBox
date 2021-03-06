/* Copyright 2021,  Gergana Kirilova

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/


package com.example.android.shoebox

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.android.shoebox.databinding.FragmentShoelistBinding
import com.example.android.shoebox.databinding.ShoeListItemBinding

/** This class handles the shoe item list logic */
class ShoeListFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var shoeDetailsAction: NavDirections
    private lateinit var shoeListBinding: FragmentShoelistBinding
    lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        shoeListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shoelist, container, false
        )

        val parentActivityViewModel: MainActivityViewModel by activityViewModels()
        viewModel = parentActivityViewModel

        shoeDetailsAction =
            ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailsFragment()

        shoeListBinding.shoeListFragment = this

        shoeListBinding.lifecycleOwner = this

        navController = findNavController()

        observeShoeList(inflater, container)
        // show the logout-menu only in this fragment as per project requirements
        setHasOptionsMenu(true)

        return shoeListBinding.root
    }

    private fun observeShoeList(inflater: LayoutInflater, container: ViewGroup?) {
        val shoesList = viewModel.shoesListLiveData
        val linearLayout = shoeListBinding.inScrollLinearLayout
        shoesList.observe(this) { shoeList ->
            shoeList.forEach { shoe ->
                val shoeListItem = ShoeListItem(inflater, container, requireContext())
                val shoeListItemRootView = shoeListItem.shoeListItemBinding.root
                shoeListItem.shoeListItemBinding.shoe = shoe
                linearLayout.addView(shoeListItemRootView)
            }

        }
    }

    //Navigate to the shoe details destination whenever the fab is clicked
    fun onFabClicked() {
        navigateToDestination(navController, shoeDetailsAction, R.id.shoe_list_destination)
    }


    inner class ShoeListItem(
        inflater: LayoutInflater, container: ViewGroup?,
        context: Context
    ) : ConstraintLayout(context) {

        val shoeListItemBinding: ShoeListItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.shoe_list_item, container, false)


    }

    /** Show instructions on screen,regarding how to add the first shoe item,
     *  when the shoe list is empty */
    fun setVisibility(): Int {
        var emptyListTextViewVisibility = 0
        val shoeListIsEmpty = viewModel.shoeListIsEmpty
        shoeListIsEmpty.observe(this) { listIsEmpty ->
            emptyListTextViewVisibility = if (listIsEmpty) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        return emptyListTextViewVisibility
    }

}