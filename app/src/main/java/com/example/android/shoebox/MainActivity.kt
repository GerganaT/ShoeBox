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
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.android.shoebox.databinding.ActivityMainBinding

/** This class is the entry point of the app and holds some app bar and menu-related logic */

class MainActivity : AppCompatActivity() {


    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // this variable is not referenced to anywhere but necessary as the Binding type cannot
        // be inferred
        val mainBinding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        // hide the up arrow in some destinations, where it doesn't make sense to be present
        val appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.login_destination,
                R.id.shoe_details_destination,
                R.id.shoe_list_destination
            )
            .build()

        navController = findNavController(R.id.host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        val currentDestinationId = navController.currentDestination?.id
        if (currentDestinationId == R.id.shoe_list_destination) {
            menu?.findItem(R.id.menu_item_logout)?.isVisible = true
        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_logout -> {
                val loginDestination = ShoeListFragmentDirections
                    .actionShoeListDestinationToLoginDestination()
                navController.navigate(loginDestination)
            }
            // ensure proper backstack navigation via the up-arrow button by adopting the same
            //behaviour as the back-button
            // idea taken from here :
            //https://stackoverflow.com/questions/28438030/
            // how-to-make-back-icon-to-behave-same-as-physical-back-button-in-android
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}

