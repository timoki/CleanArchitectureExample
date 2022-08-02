package com.example.cleanarchitectureexample.utils

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class FragmentNavigation(rootFragmentListener: RootFragmentListener?, fragmentManager: FragmentManager?, containerId: Int) {

    var mRootFragmentListener = rootFragmentListener
    var mFragmentManager = fragmentManager
    var mContainerId = containerId
    var mFragmentStacks: SparseArray<Stack<Fragment>> = SparseArray()
    var mCounter = AtomicInteger(0)
    var mSelectedTabIndex = 0

    fun switchTab(index: Int) {
        mFragmentManager?.beginTransaction()?.let {
            detachCurrentFragment(it)
            if (mFragmentStacks[index] != null && !mFragmentStacks[index].isEmpty()) {
                val attachFragment = mFragmentManager?.findFragmentByTag(
                    mFragmentStacks[index].peek().tag
                )
                attachFragment?.let { it1 -> it.attach(it1) }
            } else {
                val attachFragment = mRootFragmentListener?.getRootFragment(index)
                if (mFragmentStacks[index] != null) {
                    mFragmentStacks[index].add(attachFragment)
                } else {
                    val stack = Stack<Fragment>()
                    stack.add(attachFragment)
                    mFragmentStacks.put(index, stack)
                }
                attachFragment?.let { it1 ->
                    it.add(mContainerId,
                        it1, generateTag(attachFragment))
                }
            }
            it.commit()
            mSelectedTabIndex = index
        }
    }

    private fun isRootFragment(): Boolean {
        return !(mFragmentStacks[mSelectedTabIndex] != null && mFragmentStacks[mSelectedTabIndex].size > 1)
    }

    fun getCurrentFragment(): Fragment? {
        return mFragmentManager?.findFragmentByTag(mFragmentStacks[mSelectedTabIndex].peek().tag)
    }

    private fun generateTag(fragment: Fragment): String {
        return fragment.javaClass.simpleName + mCounter.incrementAndGet()
    }

    private fun detachCurrentFragment(ft: FragmentTransaction) {
        if (mFragmentStacks[mSelectedTabIndex] != null && !mFragmentStacks[mSelectedTabIndex].isEmpty()) {
            val currentFragment = mFragmentManager?.findFragmentByTag(
                mFragmentStacks[mSelectedTabIndex].peek().tag
            )
            currentFragment?.let { ft.detach(it) }
        }
    }

    fun pushFragment(fragment: Fragment) {
        mFragmentManager?.beginTransaction()?.let {
            detachCurrentFragment(it)
            if (mFragmentStacks[mSelectedTabIndex] != null) {
                mFragmentStacks[mSelectedTabIndex].add(fragment)
            } else {
                val stack = Stack<Fragment>()
                stack.add(fragment)
                mFragmentStacks.put(mSelectedTabIndex, stack)
            }
            it.add(mContainerId, fragment, generateTag(fragment))
            it.commit()
        }
    }

    fun popFragment() {
        mFragmentManager?.beginTransaction()?.let {
            if (mFragmentStacks[mSelectedTabIndex] != null && !mFragmentStacks[mSelectedTabIndex].isEmpty()) {
                val removeFragment = mFragmentManager?.findFragmentByTag(mFragmentStacks[mSelectedTabIndex].pop().tag)
                removeFragment?.let { it1 -> it.remove(it1) }
                val newFragment = mFragmentManager?.findFragmentByTag(mFragmentStacks[mSelectedTabIndex].peek().tag)
                newFragment?.let { it1 -> it.attach(it1) }
                it.commit()
            }
        }
    }

    fun goRootFragment() {
        while (true) {
            mFragmentManager?.beginTransaction()?.let {
                if (mFragmentStacks[mSelectedTabIndex] != null && !mFragmentStacks[mSelectedTabIndex].isEmpty()) {
                    val removeFragment = mFragmentManager?.findFragmentByTag(mFragmentStacks[mSelectedTabIndex].pop().tag)
                    removeFragment?.let { it1 -> it.remove(it1) }
                    val newFragment = mFragmentManager?.findFragmentByTag(mFragmentStacks[mSelectedTabIndex].peek().tag)
                    newFragment?.let { it1 -> it.attach(it1) }
                    it.commit()
                }
            }

            if (isRootFragment()) return
        }
    }

    interface RootFragmentListener {
        fun getRootFragment(index: Int): Fragment
    }
}