package com.fazv.coinmaster.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fazv.data.model.StateError
import com.fazv.data.model.StateLoading
import com.fazv.data.model.StateResponse
import com.fazv.data.model.StateSuccess
import com.fazv.data.model.vo.CurrenciesListVO
import com.fazv.domain.usecase.GetCurrenciesListUseCase

class CoinListViewModel(
    private val currenciesListUseCase: GetCurrenciesListUseCase
) : BaseViewModel() {

    val currenciesListLiveData: LiveData<StateResponse<CurrenciesListVO>>
        get() = _currenciesListLiveData

    private val _currenciesListLiveData = MutableLiveData<StateResponse<CurrenciesListVO>>()

    var coinSelected: Pair<String, String>? = null

    fun fetchCurrenciesList() {
        currenciesListUseCase
            .execute(Unit)
            .doOnSubscribe { _currenciesListLiveData.postValue(StateLoading()) }
            .subscribe(
                {
                    _currenciesListLiveData.postValue(StateSuccess(it))
                },
                {
                    _currenciesListLiveData.postValue(StateError(it))
                }
            )
            .also { disposables.addAll(it) }
    }
}