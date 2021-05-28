
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
import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.android.shoebox.databinding.ShoeListItemBinding

// This class inflates the shoe_list_item layout
//idea taken from:
// https://medium.com/google-developer-experts/exploring-kotlin-initialization
// -with-android-custom-views-cde06e915e8d

class ShoeListItemView (context: Context) : ConstraintLayout(context){
    private val shoeListItemBinding = ShoeListItemBinding.inflate(LayoutInflater.from(context))



    init {
        inflate(context, R.layout.shoe_list_item, this)
    }


}
