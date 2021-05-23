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

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


// class that handles the shoe-data-related operations in the app
class MainActivityViewModel : ViewModel() {


    var shoeName: String = ""
    var shoeBrand: String = ""
    var shoeSize: String = ""
    var shoeDescription: String = ""
    private val _shoesListLiveData = MutableLiveData<MutableList<Shoes>>()
    val shoesListLiveData: LiveData<MutableList<Shoes>>
        get() = _shoesListLiveData


    private val shoesList: MutableList<Shoes> = mutableListOf()


    init {
        //  populateShoeData()
    }

    //TODO later replace index literal by the specific item index, selected by the user in the list
    //TODO also this function should be called from the clicker for the individual list item
    private fun populateShoeData() {


        _shoesListLiveData.value = shoesList
        shoeName = shoesList[0].shoeName
        shoeBrand = shoesList[0].shoeBrand
        shoeSize = shoesList[0].shoeSize
        shoeDescription = shoesList[0].shoeDescription


    }

    //TODO make this populate the list when clicked away and remove log
    fun onSaveClicked() {

        shoesList.add(Shoes(shoeName, shoeBrand, shoeSize, shoeDescription))

        Log.i("MainActivityViewModel" ,"$shoeName , $shoeBrand , $shoeSize , $shoesList")


    }




}