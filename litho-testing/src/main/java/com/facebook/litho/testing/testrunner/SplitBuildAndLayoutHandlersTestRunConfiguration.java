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

package com.facebook.litho.testing.testrunner;

import com.facebook.litho.config.ComponentsConfiguration;
import org.junit.runners.model.FrameworkMethod;

public class SplitBuildAndLayoutHandlersTestRunConfiguration
    extends SplitBuildAndLayoutTestRunConfiguration {
  private final boolean defaultIsSplitHandlersEnabled =
      ComponentsConfiguration.useSeparateThreadHandlersForResolveAndLayout;

  @Override
  public void beforeTest(FrameworkMethod method) {
    super.beforeTest(method);
    ComponentsConfiguration.useSeparateThreadHandlersForResolveAndLayout =
        !defaultIsSplitHandlersEnabled;
  }

  @Override
  public void afterTest(FrameworkMethod method) {
    super.afterTest(method);
    ComponentsConfiguration.useSeparateThreadHandlersForResolveAndLayout =
        defaultIsSplitHandlersEnabled;
  }
}
