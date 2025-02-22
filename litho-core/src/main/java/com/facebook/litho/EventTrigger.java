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

package com.facebook.litho;

import com.facebook.infer.annotation.Nullsafe;
import javax.annotation.Nullable;

/**
 * Allows a top-down communication with a component and its immediate parent. The component must be
 * able to handle {@link com.facebook.litho.annotations.OnTrigger} events in order to accept an
 * EventTrigger.
 */
@Nullsafe(Nullsafe.Mode.LOCAL)
public class EventTrigger<E> {

  @Nullable public EventTriggerTarget mTriggerTarget;
  @Nullable public ComponentContext mComponentContext;
  public final int mId;
  private final String mKey;
  @Nullable private final Handle mHandle;

  public EventTrigger(String parentKey, int id, String childKey, @Nullable Handle handle) {
    mId = id;
    mKey = parentKey + id + childKey;
    mHandle = handle;
  }

  public String getKey() {
    return mKey;
  }

  @Nullable
  public Handle getHandle() {
    return mHandle;
  }

  public int getId() {
    return mId;
  }

  @Nullable
  public Object dispatchOnTrigger(E event) {
    return dispatchOnTrigger(event, new Object[] {});
  }

  @Nullable
  public Object dispatchOnTrigger(E event, Object[] params) {
    if (mTriggerTarget == null) {
      return null;
    }

    return mTriggerTarget.acceptTriggerEvent(this, event, params);
  }
}
