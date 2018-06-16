package com.koonat.ouluhealth

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.custom_action_bar.*

class CustomActionBar : Fragment() {
    private lateinit var mView: View

    companion object {
        private val EXTRA_TITLE = "EXTRA_TITLE"
        private val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"

        fun getInstance(title: String, description: String): CustomActionBar {
            val fragment = CustomActionBar()
            val args = Bundle()
            args.putString(EXTRA_TITLE, title)
            args.putString(EXTRA_DESCRIPTION, description)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.custom_action_bar, container, false)
        return mView
    }

    /**
     * Fragment is visible now
     */
    override fun onResume() {
        super.onResume()
        val args = arguments
        titleTextView?.text = args?.getString(EXTRA_TITLE)
        descriptionTextView?.text = args?.getString(EXTRA_DESCRIPTION)
    }

    fun setDescription(description: String) {
        descriptionTextView.text = description
    }

    fun setTitle(title: String) {
        titleTextView.text = title
    }
}
