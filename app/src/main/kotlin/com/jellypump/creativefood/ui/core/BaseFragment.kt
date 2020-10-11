package com.jellypump.creativefood.ui.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.jellypump.creativefood.R
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment : Fragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract val layoutId: Int

    protected open fun <T : ViewModel> getViewModel(classType: KClass<T>) =
        ViewModelProvider(this, viewModelFactory)[classType.java]

    protected lateinit var navController: NavController

    protected val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = NavHostFragment.findNavController(this)
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        observeData()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    abstract fun initUi()

    open fun observeData() {
        // to override
    }

    fun Completable.simpleSubscribe(onComplete: () -> Unit) = this.subscribeBy(
        onError = {
            Toast.makeText(requireContext(), R.string.generic_error, Toast.LENGTH_SHORT).show()
            Timber.e(it)
        },
        onComplete = onComplete
    ).addTo(compositeDisposable)

    fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(this@BaseFragment, Observer(action))
    }
}