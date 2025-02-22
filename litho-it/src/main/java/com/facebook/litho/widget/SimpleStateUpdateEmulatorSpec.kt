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

package com.facebook.litho.widget

import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.StateValue
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateInitialState
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.OnUpdateState
import com.facebook.litho.annotations.Prop
import com.facebook.litho.annotations.State

@LayoutSpec
object SimpleStateUpdateEmulatorSpec {

  const val INITIAL_COUNT = 1

  @JvmStatic
  @OnCreateInitialState
  fun onCreateInitialState(
      c: ComponentContext,
      count: StateValue<Int>,
      @Prop(optional = true) initialCount: Int?,
  ) {
    count.set(initialCount ?: 1)
  }

  @JvmStatic
  @OnCreateLayout
  fun onCreateLayout(
      c: ComponentContext,
      @Prop caller: Caller,
      @Prop(optional = true) prefix: String?,
      @State count: Int
  ): Component? {
    caller.set(c)
    return if (count > 0) {
      Column.create(c).child(Text.create(c).text((prefix ?: "Text: ") + count)).build()
    } else {
      null
    }
  }

  @JvmStatic
  @OnUpdateState
  fun onIncrementCount(count: StateValue<Int>) {
    val counter = count.get() ?: throw RuntimeException("state value is null.")
    count.set(counter + 1)
  }

  @JvmStatic
  @OnUpdateState
  fun onDecrementCount(count: StateValue<Int>) {
    val counter = count.get() ?: throw RuntimeException("state value is null.")
    count.set(counter - 1)
  }

  class Caller {
    var c: ComponentContext? = null
    fun set(c: ComponentContext?) {
      this.c = c
    }

    fun increment() {
      SimpleStateUpdateEmulator.onIncrementCountSync(c)
    }

    fun decrement() {
      SimpleStateUpdateEmulator.onDecrementCountSync(c)
    }

    fun incrementAsync() {
      SimpleStateUpdateEmulator.onIncrementCount(c)
    }
  }
}
