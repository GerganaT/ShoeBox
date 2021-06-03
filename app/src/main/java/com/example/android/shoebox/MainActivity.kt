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
import androidx.navigation.ui.NavigationUI
import com.example.android.shoebox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainBinding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        //  display custom label for each fragment in the app bar
        navController = findNavController(R.id.host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shoe_menu, menu)
        val currentDestinationId = navController.currentDestination?.id
        if (currentDestinationId == R.id.shoe_list_destination) {
            menu?.findItem(R.id.menu_item_logout)?.isVisible = true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val loginDestination = ShoeListFragmentDirections
            .actionShoeListDestinationToLoginDestination()
        navController.navigate(loginDestination)

        return super.onOptionsItemSelected(item)
    }
}

