package ru.serg.mvi_core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.serg.mvi_core.models.IAction
import ru.serg.mvi_core.models.IEffect
import ru.serg.mvi_core.models.IState

abstract class CoreViewModel<State : IState, Action : IAction, Effect : IEffect>() : ViewModel() {

    private val _effectFlow = MutableSharedFlow<Effect>()
    val effects = _effectFlow.asSharedFlow()

    private val _actionFlow: MutableSharedFlow<Action> = MutableSharedFlow(extraBufferCapacity = 10)

    private val _state: StateFlow<State> = _actionFlow
        .map {
            processAction(it)
        }
        .onStart {
            initScreen()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = getInitialState()
        )

    val state = _state

    abstract fun getInitialState(): State

    abstract fun processAction(action: Action): State

    abstract fun initScreen()

    protected fun setState(reduce: State.() -> State): State {
        return reduce.invoke(_state.value)
    }

    fun sendAction(action: Action) {
        viewModelScope.launch {
            _actionFlow.emit(action)
        }
    }

    fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _effectFlow.emit(effect)
        }
    }
}