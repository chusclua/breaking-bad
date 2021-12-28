package com.chus.clua.breakingbad.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

// Parent class that implements the necessary Rules for LiveData and Coroutines to deal with the JVM
open class ViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()
}