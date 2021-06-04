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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.android.shoebox.databinding.FragmentShoeDetailsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
        setupAlertDialog()
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
                navigateToShoeList()
            }
        })
    }

    fun onCancelClicked() {
        viewModel.checkDataEntry()
        viewModel.shoeDetailsIsNotNull.observe(this, { shoeDetailsIsNotNull ->

            if (shoeDetailsIsNotNull) {
                alertDialog.show()
            } else {
                navigateToShoeList()
            }
        })
    }

    private fun setupAlertDialog() {

        alertDialog = MaterialAlertDialogBuilder(requireContext())
            .setMessage(resources.getString(R.string.alert_dialog_question))

            .setNegativeButton(resources.getString(R.string.alert_dialog_action_negative)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.alert_dialog_action_positive)) { _, _ ->
                viewModel.resetShoeDataFields()
                navigateToShoeList()
            }
            .create()
        alertDialog.setOnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.primaryTextColor, null))
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.primaryTextColor, null))
        }
    }

    private fun navigateToShoeList() {
        val currentDestinationId = navController.currentDestination?.id
        if (currentDestinationId == R.id.shoe_details_destination) {
            navController.navigate(shoeDetailsToShoeListAction)
        }

    }


}


