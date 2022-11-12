package android.ptc.com.ptcflixing.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseFragment<DB : ViewDataBinding, STATE : UiState, VM : BaseViewModel<STATE>> :
    Fragment() {


    lateinit var viewModel: VM

    abstract fun getVM(): VM

    private var _binding: DB? = null
    protected val binding: DB
        get() {
            return _binding ?: throw IllegalStateException(
                "data binding should not be null"
            )
        }

    @LayoutRes
    abstract fun getLayoutRes(): Int


    protected fun bindViewModel() {}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getVM()
        subscribeEvents()
        onViewAttach()
        subscribeUiState()
    }

    private fun subscribeUiState() {
        viewModel.uiStateFlow
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach {
                renderState(it)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    abstract fun renderState(uiState: STATE)

    private fun subscribeEvents() {

        viewModel.errorMsgSingleEventFlow
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach {
                // applicationContext not activity context to avoid activity leak on back pressed while toast is still showing
                when (it) {
                    is BaseViewModel.ErrorMessage.StringErrorMessage -> Toast.makeText(
                        activity?.applicationContext,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    is BaseViewModel.ErrorMessage.StringResErrorMessage -> Toast.makeText(
                        activity?.applicationContext,
                        it.messageResId,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onResume() {
        super.onResume()
        onViewRefresh()
    }


    open fun onViewRefresh() {

    }


    open fun onViewAttach() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}