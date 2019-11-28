package com.klimaatmobiel.ui

import android.content.Intent
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
    }

    /**
     * Set the toolbar title from the current activity.
     *
     * @param titleString The title you want to set.
     */
    fun setToolbarTitle(titleString : String){
        supportActionBar!!.title = titleString
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    /**
     * Handle clicks on the bottomNavigation
     *
     * @param menuItem The navigation item that is clicked.
     */
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
        supportFragmentManager.beginTransaction().replace(R.id.bottom_navigation_container, fragment).commit()
    }

    /**
     * Decide if the activity should relaunch (logout current user and reset the app) or to navigate the backstack.
     */
    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment)!!.childFragmentManager.backStackEntryCount
        if(fragment == 1) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            applicationContext.startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }
}
