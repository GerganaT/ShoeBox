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


        // Inflate the layout for this fragment
        return shoeDetailsBinding.root
    }

    fun onSaveClicked() {

        viewModel.saveDetailData()
        viewModel.shoeDetailIsNull.observe(this, { shoeDetailsIsNull ->

            if (shoeDetailsIsNull) {
                Toast.makeText(activity, "Please enter all details", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(activity, "Shoe entry saved", Toast.LENGTH_SHORT).show()
                if (navController.currentDestination?.id == R.id.shoe_details_destination) {
                    navController.navigate(shoeDetailsToShoeListAction)
                }

            }
            
        })

    }

    fun onCancelClicked() {
        navController.navigate(shoeDetailsToShoeListAction)
    }

}


