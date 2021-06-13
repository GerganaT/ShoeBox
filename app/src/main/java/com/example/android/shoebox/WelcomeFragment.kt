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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.android.shoebox.databinding.FragmentWelcomeBinding

/** This class contains the welcome screen logic */
class WelcomeFragment : Fragment() {

    private lateinit var instructionsFragmentAction: NavDirections
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navController = findNavController()
        val welcomeBinding: FragmentWelcomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_welcome, container, false
        )

        instructionsFragmentAction =
            WelcomeFragmentDirections.actionWelcomeFragmentToInstructionsFragment()



        welcomeBinding.welcomeFragment = this

        return welcomeBinding.root
    }

    fun onInstructionsButtonClicked() {
        navigateToDestination(navController, instructionsFragmentAction, R.id.welcome_destination)
    }
}