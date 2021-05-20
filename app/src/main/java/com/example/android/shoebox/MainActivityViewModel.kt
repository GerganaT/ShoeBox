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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// class that handles the shoe-data-related operations in the app
class MainActivityViewModel : ViewModel() {

    private lateinit var shoesList: MutableList<Shoes>

    private val _shoesListLiveData = MutableLiveData<MutableList<Shoes>>()
    val shoesListLiveData: LiveData<MutableList<Shoes>>
        get() = _shoesListLiveData

    init {
      initializeShoeList()
    }


  private  fun initializeShoeList() {
        shoesList = mutableListOf(
            Shoes(
                "ballerinas", "FancyBrand",
                "36,37", "lovely casual shoes"
            )
        )

        _shoesListLiveData.value = shoesList
    }


}