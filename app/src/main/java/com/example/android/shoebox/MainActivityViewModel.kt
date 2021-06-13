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


/** This class handles all data-related logic in the app */
class MainActivityViewModel : ViewModel() {

    val userEmail = MutableLiveData<String>()

    val userPassword = MutableLiveData<String>()

    private val _loginDetailIsNullOrEmpty = MutableLiveData<Boolean>()
    val loginDetailIsNullOrEmpty: LiveData<Boolean>
        get() = _loginDetailIsNullOrEmpty


    val shoeName = MutableLiveData<String>()


    val shoeBrand = MutableLiveData<String>()

    val shoeSize = MutableLiveData<String>()


    val shoeDescription = MutableLiveData<String>()

    private val _shoeDetailIsNullOrEmpty = MutableLiveData<Boolean>()
    val shoeDetailIsNullOrEmpty: LiveData<Boolean>
        get() = _shoeDetailIsNullOrEmpty

    private val _shoeDetailsIsNotNullOrEmpty = MutableLiveData<Boolean>()
    val shoeDetailsIsNotNullOrEmpty: LiveData<Boolean>
        get() = _shoeDetailsIsNotNullOrEmpty

    private val _shoesListLiveData = MutableLiveData<MutableList<Shoe>>()
    val shoesListLiveData: LiveData<MutableList<Shoe>>
        get() = _shoesListLiveData

    private val _shoeListIsEmpty = MutableLiveData<Boolean>()
    val shoeListIsEmpty: LiveData<Boolean>
        get() = _shoeListIsEmpty

    init {
        _shoesListLiveData.value = mutableListOf()
        _shoeListIsEmpty.value = true

    }

    /** Checks whether the user entered anything in the EditText fields in the shoe details
     * destination and saves the entry if the text is not null or an empty string*/
    fun saveShoeDetails() {

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
            _shoeDetailIsNullOrEmpty.value = false
            resetShoeDataFields()
            _shoeListIsEmpty.value = false
        }

    }

    /** Resets the values to introduce blank EditText fields the next time
    the user wants to add new shoe list item */
    fun resetShoeDataFields() {
        shoeName.value = null
        shoeBrand.value = null
        shoeSize.value = null
        shoeDescription.value = null
    }

    /** Checks whether the user entered anything in the EditText fields,
     * before navigating away from the shoe details destination*/
    fun checkShoeDetailsEntry() {

        _shoeDetailsIsNotNullOrEmpty.value =
            (!shoeName.value.isNullOrEmpty()) || (!shoeBrand.value.isNullOrEmpty())
                    || (!shoeSize.value.isNullOrEmpty()) || (!shoeDescription.value.isNullOrEmpty())
    }

    /** Checks whether the user entered all the required login information and sets the values
     * of the e-mail and password fields to null when the user navigates away from the login screen*/
    fun checkLoginDetailsEntry() {
        _loginDetailIsNullOrEmpty.value = (userEmail.value.isNullOrEmpty()) ||
                (userPassword.value.isNullOrEmpty())

        if (_loginDetailIsNullOrEmpty.value == false) {
            userEmail.value = null
            userPassword.value = null
        }

    }


}