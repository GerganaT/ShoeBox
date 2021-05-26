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

    private val _shoeDetailIsNull = MutableLiveData<Boolean>()
    val shoeDetailIsNull: LiveData<Boolean>
        get() = _shoeDetailIsNull

    private val _shoesListLiveData = MutableLiveData<MutableList<Shoe>>()
    val shoesListLiveData: LiveData<MutableList<Shoe>>
        get() = _shoesListLiveData


    init {
        _shoesListLiveData.value = mutableListOf()


    }
   //TODO how to reset data and fire alert box only when text available in the cancel button -  add another if here?
    fun saveDetailData() {

        if ((shoeName.value.isNullOrEmpty()) || (shoeBrand.value.isNullOrEmpty())
            || (shoeSize.value.isNullOrEmpty()) || (shoeDescription.value.isNullOrEmpty())
        ) {
            _shoeDetailIsNull.value = true
            Log.i("MainActivityViewModel", "shoeDetailsIsNull is ${_shoeDetailIsNull.value}")

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
            _shoeDetailIsNull.value = false
            // reset the values on save to introduce blank edit text fields the next time
            // the user wants to add new shoe list item
            shoeName.value = null
            shoeBrand.value = null
            shoeSize.value = null
            shoeDescription.value = null


            Log.i("MainActivityViewModel", "value of shoeList is ${shoesListLiveData.value}")
        }
    }
/** Sets the value of shoeDetailsIsNull to null*/
fun resetShoeDetailsIsNull(){
    _shoeDetailIsNull.value = null
}
}