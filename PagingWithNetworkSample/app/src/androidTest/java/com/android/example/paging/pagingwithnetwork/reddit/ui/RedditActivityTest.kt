/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.paging.pagingwithnetwork.reddit.ui

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.android.example.paging.pagingwithnetwork.R
import com.android.example.paging.pagingwithnetwork.reddit.DefaultServiceLocator
import com.android.example.paging.pagingwithnetwork.reddit.ServiceLocator
import com.android.example.paging.pagingwithnetwork.reddit.api.RedditApi
import com.android.example.paging.pagingwithnetwork.reddit.repository.RedditPostRepository
import com.android.example.paging.pagingwithnetwork.reddit.repository.RedditPostRepository.Type.IN_MEMORY_BY_ITEM
import com.android.example.paging.pagingwithnetwork.reddit.repository.RedditPostRepository.Type.IN_MEMORY_BY_PAGE
import com.android.example.paging.pagingwithnetwork.reddit.ui.SubRedditViewModel.Companion.DEFAULT_SUBREDDIT
import com.android.example.paging.pagingwithnetwork.repository.FakeRedditApi
import com.android.example.paging.pagingwithnetwork.repository.PostFactory
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * Simple sanity test to ensure data is displayed
 */
@RunWith(Parameterized::class)
class RedditActivityTest(private val type: RedditPostRepository.Type) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun params() = arrayOf(IN_MEMORY_BY_ITEM, IN_MEMORY_BY_PAGE)
    }

    private val appContext = ApplicationProvider.getApplicationContext<Application>()
    private val postFactory = PostFactory()

    @Before
    fun init() {
        val fakeApi = FakeRedditApi()
        fakeApi.addPost(postFactory.createRedditPost(DEFAULT_SUBREDDIT))
        fakeApi.addPost(postFactory.createRedditPost(DEFAULT_SUBREDDIT))
        fakeApi.addPost(postFactory.createRedditPost(DEFAULT_SUBREDDIT))

        // use a controlled service locator w/ fake API
        ServiceLocator.swap(
            object : DefaultServiceLocator(app = appContext, useInMemoryDb = true) {
                override fun getRedditApi(): RedditApi = fakeApi
            }
        )
    }

    @Test
    fun showSomeResults() {
        val scenario = ActivityScenario.launch<RedditActivity>(
            RedditActivity.intentFor(context = appContext, type = type)
        )
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { activity ->
            val recyclerView = activity.findViewById<RecyclerView>(R.id.list)
            assertThat(recyclerView.adapter?.itemCount, `is`(3))
        }
    }
}