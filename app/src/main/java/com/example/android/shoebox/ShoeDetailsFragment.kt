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
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.android.shoebox.databinding.FragmentShoeDetailsBinding

// This class shows detailed info for each shoe pair,stored in the warehouse
class ShoeDetailsFragment : Fragment() {

    lateinit var shoeDetailsToShoeListAction: NavDirections

    lateinit var viewModel: MainActivityViewModel

    lateinit var navController: NavController

    lateinit var alertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val shoeDetailsBinding: FragmentShoeDetailsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shoe_details, container, false
        )
        val parentActivityViewModel: MainActivityViewModel by activityViewModels()
        viewModel = parentActivityViewModel
        shoeDetailsBinding.viewModel = parentActivityViewModel
        shoeDetailsBinding.shoeDetailsFragment = this

        shoeDetailsBinding.lifecycleOwner = this

        shoeDetailsToShoeListAction =
            ShoeDetailsFragmentDirections.actionShoeDetailsDestinationToShoeListDestination()

        navController = findNavController()
        // this method instantiates the AlertDialog which pops up when the user tries
        //to exit the details screen and has some data typed in.This is initialized here
        //to avoid the overhead of creating objects of this type every time the cancel
        //button is clicked.
        alertDialog = setupAlertDialog(
            viewModel, navController,
            shoeDetailsToShoeListAction, R.id.shoe_details_destination
        )
        // Inflate the layout for this fragment
        return shoeDetailsBinding.root
    }

    fun onSaveClicked() {

        viewModel.saveDetailDataEntry()
        viewModel.shoeDetailIsNullOrEmpty.observe(this, { shoeDetailsIsNull ->

            if (shoeDetailsIsNull) {
                Toast.makeText(
                    activity,
                    getString(R.string.toast_enter_details),
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                Toast.makeText(
                    activity,
                    getString(R.string.toast_details_saved),
                    Toast.LENGTH_SHORT
                ).show()
                navigateToDestination(
                    navController, shoeDetailsToShoeListAction,
                    R.id.shoe_details_destination
                )
            }
        })
    }

    fun onCancelClicked() {
        validateUserInput(
            navController,
            viewModel,
            alertDialog,
            shoeDetailsToShoeListAction)
    }
    // show AlertDialog whenever the user attempts to navigate back
    // from the ShoeDetails destination but has some data entered in the EditText fields
    override fun onAttach(context: Context) {
        super.onAttach(context)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                validateUserInput(
                    navController,
                    viewModel,
                    alertDialog,
                    shoeDetailsToShoeListAction
                )
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }
}


