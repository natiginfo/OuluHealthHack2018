package com.koonat.ouluhealth

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DiagnoseActivity : AppCompatActivity() {
    companion object {
        val BACK_USER_DETAILS = "BACK_USER_DETAILS"
        val BACK_ADD_SYMPTOMS = "BACK_ADD_SYMPTOMS"
        val PICK_SYMPTOM_REQUEST = 111
    }

    private var age: Int = 0
    private var sex: Int = 0

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

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showSymptomSelectorFragment(event: ContinueButtonClickedEvent) {
        age = event.age
        sex = event.sex
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction
                .add(R.id.customActionBarHolder,
                        CustomActionBar.getInstance(title = "What are your symptoms?",
                                description = "Give details about your problem"))
                .add(R.id.contentHolder, AddSymptomsFragment())
                .addToBackStack(BACK_ADD_SYMPTOMS)
                // we do not want to go white view, so, backstack is empty for first fragments
                .commit()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showSearchSymptomActivity(symptomEditClicked: SymptomEditClicked) {
        val i = Intent(this, SearchSymptomActivity::class.java)
        startActivityForResult(i, PICK_SYMPTOM_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}