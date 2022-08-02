package com.example.cleanarchitectureexample.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {
    private var ARG_PARAM1 = "param1"
    private var ARG_PARAM2 = "param2"
    private var mParam1 = 0
    private var mParam2 = 0

    var mListener: OnFragmentInteractionListener? = null
    var container: ViewGroup? = null

    abstract fun init()
    abstract fun initListener()
    abstract fun initViewModelCallback()

    protected lateinit var binding: VB private set
    protected lateinit var viewModel: VM private set

    private val type = (javaClass.genericSuperclass as ParameterizedType)
    private val classVB = type.actualTypeArguments[0] as Class<VB>
    private val classVM = type.actualTypeArguments[1] as Class<VM>

    private val inflateMethod = classVB.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is OnFragmentInteractionListener) {
            context
        } else {
            throw RuntimeException(
                context.toString()
                        + " must implement OnFragmentInteractionListener"
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getInt(ARG_PARAM1)
            mParam2 = requireArguments().getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.container = container
        binding = inflateMethod.invoke(null, inflater, container, false) as VB
        viewModel = createViewModelLazy(classVM.kotlin, { viewModelStore }).value
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initListener()
        initViewModelCallback()
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    fun fragmentPush(fragment: Fragment) {
        if (mListener != null) {
            mListener!!.onFragmentPush(
                (fragment as BaseFragment<*, *>).newInstance(
                    mParam1,
                    mParam2 + 1
                )
            )
        }
    }

    fun fragmentPop() {
        if (mListener != null) {
            mListener!!.onFragmentPop()
        }
    }

    open fun newInstance(param1: Int, param2: Int): BaseFragment<VB, VM> {
        val args = Bundle()
        args.putInt(ARG_PARAM1, param1)
        args.putInt(ARG_PARAM2, param2)
        this.arguments = args
        return this
    }

    interface OnFragmentInteractionListener {
        fun onFragmentPush(fragment: Fragment)
        fun onFragmentPop()
    }
}