package com.koonat.ouluhealth

import com.koonat.ouluhealth.domain.model.MatchedSymptom

interface SearchSymptomContract {
    interface Presenter {
        fun getSuggestions(query: String)
    }

    interface View {
        fun onShowProgressbar()

        fun onRemoveProgressbar()

        fun onSuggestionsLoaded(matchedSymptoms: List<MatchedSymptom>)
    }
}
