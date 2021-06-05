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

import android.annotation.SuppressLint
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// utility methods for navigation and data validation


// data validation
@SuppressLint("NewApi")
fun Fragment.setupAlertDialog(
    viewModel: MainActivityViewModel,
    navController: NavController,
    action: NavDirections,
    targetDestinationId: Int
): AlertDialog {

    val alertDialog = MaterialAlertDialogBuilder(requireContext())
        .setMessage(resources.getString(R.string.alert_dialog_question))

        .setNegativeButton(resources.getString(R.string.alert_dialog_action_negative)) { dialog, _ ->
            dialog.dismiss()
        }
        .setPositiveButton(resources.getString(R.string.alert_dialog_action_positive)) { _, _ ->
            viewModel.resetShoeDataFields()
            navigateToDestination(navController, action, targetDestinationId)
        }
        .create()
    alertDialog.setOnShowListener {
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(resources.getColor(R.color.primaryTextColor, null))
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(resources.getColor(R.color.primaryTextColor, null))
    }

    return alertDialog
}


// navigation methods

fun Fragment.navigateToDestination(
    navController: NavController,
    action: NavDirections,
    destinationIdProvided: Int
) {
    val currentDestinationId = navController.currentDestination?.id
    if (currentDestinationId == destinationIdProvided) {
        navController.navigate(action)
    }

}
/** Check whether the user entered anything,which he didn't save and show an alert dialog if
 * that's the case.Else - navigate to the shoe list destination*/
fun ShoeDetailsFragment.validateUserInput(navController: NavController,viewModel: MainActivityViewModel,
alertDialog: AlertDialog, action: NavDirections){
    val currentDestinationId = navController.currentDestination?.id
    if (currentDestinationId == R.id.shoe_details_destination){
        viewModel.checkDataEntry()
        viewModel.shoeDetailsIsNotNull.observe(this, { shoeDetailsIsNotNull ->

            if (shoeDetailsIsNotNull) {
                alertDialog.show()
            } else {
                navigateToDestination(
                    navController, action,
                    R.id.shoe_details_destination
                )
            }
        })
    }
}