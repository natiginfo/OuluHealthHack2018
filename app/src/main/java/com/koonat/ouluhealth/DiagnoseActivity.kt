package com.koonat.ouluhealth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class DiagnoseActivity : AppCompatActivity() {
    companion object {
        val BACK_USER_DETAILS = "BACK_USER_DETAILS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnose)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction
                .add(R.id.customActionBarHolder,
                        CustomActionBar.getInstance(title = "Easy diagnosis",
                                description = "For any health problems you encounter"))
                .add(R.id.contentHolder, UserDetailsFragment())
                // we do not want to go white view, so, backstack is empty for first fragments
                .commit()
    }
}