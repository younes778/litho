/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.litho.stateupdates

import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.StateValue
import com.facebook.litho.annotations.FromEvent
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateInitialState
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.OnUpdateState
import com.facebook.litho.annotations.Prop
import com.facebook.litho.annotations.State
import com.facebook.litho.widget.Text

@LayoutSpec
object ComponentWithCounterLazyStateLayoutSpec {

  const val INITIAL_COUNT_VALUE = 0

  @JvmStatic
  @OnCreateInitialState
  fun OnCreateInitialState(c: ComponentContext, count: StateValue<Int?>) {
    count.set(INITIAL_COUNT_VALUE)
  }

  @JvmStatic
  @OnCreateLayout
  fun onCreateLayout(
      c: ComponentContext,
      @Prop(optional = true) caller: Caller?,
      @State(canUpdateLazily = true) count: Int
  ): Component {
    caller?.set(c)
    return Column.create(c).child(Text.create(c).text("Count: $count")).build()
  }

  @JvmStatic
  @OnEvent(TestEvent::class)
  fun onTestEvent(
      c: ComponentContext,
      @Prop listener: TestEventListener,
      @FromEvent expectedValue: Int,
      @State(canUpdateLazily = true) count: Int
  ) {
    listener.onTestEventCalled(count, expectedValue)
  }

  @JvmStatic
  @OnUpdateState
  fun incrementCount(count: StateValue<Int>) {
    count.set(count.get()!! + 1)
  }

  class Caller {
    private var c: ComponentContext? = null
    fun set(c: ComponentContext) {
      this.c = c
    }

    fun increment() {
      ComponentWithCounterLazyStateLayout.incrementCountSync(c)
    }

    fun incrementAsync() {
      ComponentWithCounterLazyStateLayout.incrementCountAsync(c)
    }

    fun lazyUpdate(count: Int) {
      ComponentWithCounterLazyStateLayout.lazyUpdateCount(c, count)
    }

    fun dispatchTestEvent(testEvent: TestEvent?) {
      ComponentWithCounterLazyStateLayout.onTestEvent(c).call(testEvent)
    }
  }
}
