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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


// class that handles the shoe-data-related operations in the app
class MainActivityViewModel : ViewModel() {

    val shoeName = MutableLiveData<String>()


    val shoeBrand = MutableLiveData<String>()

    val shoeSize = MutableLiveData<String>()


    val shoeDescription = MutableLiveData<String>()

    // should these remain Mutable????????

    val shoeListItemName = MutableLiveData<String>()


    val shoeListItemBrand = MutableLiveData<String>()

    val shoeListItemSize = MutableLiveData<String>()


    val shoeListItemDescription = MutableLiveData<String>()

    private val _shoeDetailIsNullOrEmpty = MutableLiveData<Boolean>()
    val shoeDetailIsNullOrEmpty: LiveData<Boolean>
        get() = _shoeDetailIsNullOrEmpty

    private val _shoeDetailsIsNotNull = MutableLiveData<Boolean>()
    val shoeDetailsIsNotNull: LiveData<Boolean>
        get() = _shoeDetailsIsNotNull

    private val _shoesListLiveData = MutableLiveData<MutableList<Shoe>>()
    val shoesListLiveData: LiveData<MutableList<Shoe>>
        get() = _shoesListLiveData



    init {
        _shoesListLiveData.value = mutableListOf()

    }

    /** Checks whether the user entered anything in the EditText fields in the shoe details
     * destination and saves the entry if the text is not null or an empty string*/
    fun saveDetailDataEntry() {

        if ((shoeName.value.isNullOrEmpty()) || (shoeBrand.value.isNullOrEmpty())
            || (shoeSize.value.isNullOrEmpty()) || (shoeDescription.value.isNullOrEmpty())
        ) {
            _shoeDetailIsNullOrEmpty.value = true
            return
        } else {

            _shoesListLiveData.value?.add(
                Shoe(
                    shoeName.value,
                    shoeBrand.value,
                    shoeSize.value,
                    shoeDescription.value
                )
            )
            Log.i("MainActivityViewModel", "list is ${_shoesListLiveData.value}")
            _shoeDetailIsNullOrEmpty.value = false
            resetShoeDataFields()
            Log.i(
                "MainActivityViewModel", "${shoeName.value} , ${shoeBrand.value}," +
                        "${shoeSize.value},${shoeDescription.value}"
            )
        }

    }

    /** Resets the values to introduce blank edit text fields the next time
    the user wants to add new shoe list item */
    fun resetShoeDataFields() {
        shoeName.value = null
        shoeBrand.value = null
        shoeSize.value = null
        shoeDescription.value = null
    }

    /** Checks whether the user entered anything in the EditText fields, before exiting
     * the shoe details destination*/
    fun checkDataEntry() {

        _shoeDetailsIsNotNull.value =
            (!shoeName.value.isNullOrEmpty()) || (!shoeBrand.value.isNullOrEmpty())
                    || (!shoeSize.value.isNullOrEmpty()) || (!shoeDescription.value.isNullOrEmpty())
    }

    /** Populates the shoe list item fields with data if anything was added to the shoeList*/
    fun updateShoeDataFields() {
        if (_shoesListLiveData.value?.isNotEmpty() == true) {
            _shoesListLiveData.value?.forEach {
                shoeListItemName.value = it.shoeName
                shoeListItemSize.value = it.shoeSize
                shoeListItemBrand.value = it.shoeBrand
                shoeListItemDescription.value = it.shoeDescription

            }

            Log.i(
                "MainActivityViewModel", "${shoeListItemName.value} , ${shoeListItemSize.value}," +
                        "${shoeListItemBrand.value},${shoeListItemDescription.value}"
            )

        }
    }
}