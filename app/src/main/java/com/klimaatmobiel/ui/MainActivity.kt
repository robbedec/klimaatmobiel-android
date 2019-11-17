package com.klimaatmobiel.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.projecten3android.R
import com.example.projecten3android.databinding.ActivityMainBinding
import com.klimaatmobiel.ui.fragments.ShoppingCartFragment
import com.klimaatmobiel.ui.fragments.WebshopFragment

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
}
