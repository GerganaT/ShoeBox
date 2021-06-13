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
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.shoebox.databinding.FragmentLoginBinding


/** LoginFragment is class, used to capture the user's login data. */

open class LoginFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var welcomeFragmentAction: NavDirections
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val loginBinding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        val parentActivityViewModel: MainActivityViewModel by activityViewModels()
        viewModel = parentActivityViewModel

        navController = findNavController(this)
        welcomeFragmentAction =
            LoginFragmentDirections.actionLoginFragmentToWelcomeFragment()
        loginBinding.loginFragment = this
        loginBinding.viewModel = viewModel
        loginBinding.lifecycleOwner = this

        return loginBinding.root
    }

    fun onLoginOrRegisterButtonClicked() {
        viewModel.checkLoginDetailsEntry()
        viewModel.loginDetailIsNullOrEmpty.observe(this) { loginDetailsNullOrEmpty ->
            if (loginDetailsNullOrEmpty) {
                Toast.makeText(activity, R.string.toast_enter_details, Toast.LENGTH_SHORT)
                    .show()
            } else {
                navigateToDestination(navController, welcomeFragmentAction, R.id.login_destination)
            }

        }
    }
}