package com.lhmzhou.databindingDemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.transaction
import com.lhmzhou.databindingDemo.R
import com.lhmzhou.databindingDemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.transaction {
                add(R.id.main_container, ShoeListFragment())
            }
        }
    }

    override fun onBackPressed() {
        /*//If back is clicked when AddShoeFragment is on the screen,
        check whether there are unsaved changes*/
       val currentFrag = supportFragmentManager.findFragmentById(R.id.main_container)
        if(currentFrag is AddShoeFragment){
            currentFrag.onBackClicked()
        } else {
            super.onBackPressed()
        }
    }
}
