package com.koonat.ouluhealth.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.gson.Gson
import com.jakewharton.rxbinding2.widget.RxTextView
import com.koonat.ouluhealth.R
import com.koonat.ouluhealth.domain.model.MatchedSymptom
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_search_symptom.*
import java.util.concurrent.TimeUnit


class SearchSymptomActivity : AppCompatActivity(), SearchSymptomContract.View {
    companion object {
        val TAG = "SearchSymptomActivity"
    }

    private lateinit var viewAdapter: SearchSymptomAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var presenter: SearchSymptomContract.Presenter
    private var matchedSymptoms: List<MatchedSymptom> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_symptom)
        presenter = SearchSymptomPresenter(this)

        RxTextView.textChangeEvents(symptomEditText)
                .skip(1)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val query = it.text().toString()
                    presenter.getSuggestions(query)
                }
    }

    override fun onShowProgressbar() {
        symptomsRecyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun onRemoveProgressbar() {
        symptomsRecyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun onSuggestionsLoaded(matchedSymptoms: List<MatchedSymptom>) {
        this.matchedSymptoms = matchedSymptoms
        viewManager = LinearLayoutManager(this)
        viewAdapter = SearchSymptomAdapter(searchResults = matchedSymptoms,
                onClickListener = object : SearchSymptomAdapter.OnClickListener {
                    override fun onItemClicked(matchedSymptom: MatchedSymptom) {
                        val i = Intent()
                        val symptomJson = Gson().toJson(matchedSymptom)
                        i.putExtra("symptom", symptomJson)
                        setResult(Activity.RESULT_OK, i)
                        finish()
                    }

                })

        symptomsRecyclerView.layoutManager = viewManager
        symptomsRecyclerView.adapter = viewAdapter

        viewAdapter.notifyDataSetChanged()

    }
}