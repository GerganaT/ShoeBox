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

/** This class captures the new shoe item details,
 *  which the user enters in the shoe details destination*/
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

        alertDialog = setupCustomAlertDialog(
            viewModel, navController,
            shoeDetailsToShoeListAction, R.id.shoe_details_destination
        )

        return shoeDetailsBinding.root
    }

    fun onSaveClicked() {

        viewModel.saveShoeDetails()
        viewModel.shoeDetailIsNullOrEmpty.observe(this) { detailIsNullOrEmpty ->

            if (detailIsNullOrEmpty) {
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
        }
    }

    fun onCancelClicked() {
        validateUserInput(
            navController,
            viewModel,
            alertDialog,
            shoeDetailsToShoeListAction
        )
    }

    // display an AlertDialog whenever the user attempts to back - navigate
    // from the shoe details destination but has some unsaved data entered in the EditText fields
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


