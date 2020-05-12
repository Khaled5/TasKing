package com.easyinc.tasking.common.extentions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.easyinc.tasking.R

// Default transaction
inline fun FragmentManager.doTransaction(
        func: FragmentTransaction.() ->
        FragmentTransaction
    ) {
        beginTransaction().func().commit()
    }


// Animated Transaction
inline fun FragmentManager.doAnimatedTransaction(
    func: FragmentTransaction.() ->
    FragmentTransaction
) {
    beginTransaction().setCustomAnimations(
            R.animator.slide_in_left,
            R.animator.slide_out_right,
            0, 0)
        .func().commit()
}


                      /*   From Activity to Fragment */

// Move to fragment from activity (Not animated)
    fun AppCompatActivity.addFragment(frameId: Int, fragment: Fragment) {
        supportFragmentManager.doTransaction { add(frameId, fragment) }
    }


    fun AppCompatActivity.replaceFragment(frameId: Int, fragment: Fragment) {
        supportFragmentManager.doTransaction { replace(frameId, fragment) }
    }

    fun AppCompatActivity.removeFragment(fragment: Fragment) {
        supportFragmentManager.doTransaction { remove(fragment) }
    }


// Move to fragment from activity (Animated)
fun AppCompatActivity.addAnimatedFragment(frameId: Int, fragment: Fragment, addToBS: Boolean = false) {
    supportFragmentManager.doAnimatedTransaction { add(frameId, fragment).also {
        if (addToBS){
            addToBackStack(this@addAnimatedFragment.javaClass.name)
        }
    } }
}


fun AppCompatActivity.replaceAnimatedFragment(frameId: Int, fragment: Fragment, addToBS: Boolean = false) {
    supportFragmentManager.doAnimatedTransaction { replace(frameId, fragment).also {
        if (addToBS){
            addToBackStack(this@replaceAnimatedFragment.javaClass.name)
        }
    } }
}

fun AppCompatActivity.removeAnimatedFragment(fragment: Fragment) {
    supportFragmentManager.doAnimatedTransaction { remove(fragment) }
}


                                 /*   From Fragment to Fragment */

// Move to fragment from fragment (Not animated)
fun Fragment.addFragment(frameId: Int, fragment: Fragment, addToBS: Boolean = false) {
    activity?.supportFragmentManager?.doTransaction { add(frameId, fragment).also {
        if (addToBS){
            addToBackStack(this@addFragment.javaClass.name)
        }
    } }
}


fun Fragment.replaceFragment(frameId: Int, fragment: Fragment, addToBS: Boolean = false) {
    activity?.supportFragmentManager?.doTransaction { replace(frameId, fragment).also {
        if (addToBS){
            addToBackStack(this@replaceFragment.javaClass.name)
        }
    } }
}

fun Fragment.removeFragment(fragment: Fragment) {
    activity?.supportFragmentManager?.doTransaction { remove(fragment) }
}


// Move to fragment from fragment (Animated)
fun Fragment.addAnimatedFragment(frameId: Int, fragment: Fragment, addToBS: Boolean = false) {
    activity?.supportFragmentManager?.doAnimatedTransaction { add(frameId, fragment).also {
        if (addToBS){
            addToBackStack(this@addAnimatedFragment.javaClass.name)
        }
    } }
}


fun Fragment.replaceAnimatedFragment(frameId: Int, fragment: Fragment, addToBS: Boolean = false) {
    activity?.supportFragmentManager?.doAnimatedTransaction { replace(frameId, fragment).also {
        if (addToBS){
            addToBackStack(this@replaceAnimatedFragment.javaClass.name)
        }
    } }
}

fun Fragment.removeAnimatedFragment(fragment: Fragment) {
    activity?.supportFragmentManager?.doAnimatedTransaction { remove(fragment) }
}