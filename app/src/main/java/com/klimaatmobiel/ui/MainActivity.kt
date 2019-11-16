package com.klimaatmobiel.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.projecten3android.R
import com.example.projecten3android.databinding.ActivityMainBinding
import com.klimaatmobiel.ui.fragments.ShoppingCartFragment
import com.klimaatmobiel.ui.fragments.WebshopFragment
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //setContentView(R.layout.activity_main)




    }

    fun setToolbarTitle(titleString : String){
        supportActionBar!!.title = titleString
    }

    fun triggerWebshopBottomNavigation(menuItem : MenuItem) {
        var fragment : Fragment = WebshopFragment()
        when(menuItem.itemId){
            R.id.nav_order -> {

                fragment = ShoppingCartFragment()
            }
            R.id.nav_webshop -> {
                fragment = WebshopFragment()
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.bottom_navigation_container, fragment).commit();
    }

    /**
     * Logout of the application and reset the activity.
     */
    override fun onBackPressed() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        applicationContext.startActivity(intent)
    }
}
