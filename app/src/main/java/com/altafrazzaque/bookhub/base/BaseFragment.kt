package com.altafrazzaque.bookhub.base

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {
   abstract fun setObservers()

   fun showSnackBar(text:String){
      val view = requireView()
      Snackbar.make(view,text, Snackbar.LENGTH_LONG).show()
   }
}