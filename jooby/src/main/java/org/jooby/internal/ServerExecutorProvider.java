/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jooby.internal;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.jooby.spi.Server;

import java.util.concurrent.Executor;

import static java.util.Objects.requireNonNull;

public class ServerExecutorProvider implements Provider<Executor>
{

  private Executor executor;

  @Inject
  public ServerExecutorProvider(final ServerHolder serverHolder) {
    requireNonNull(serverHolder, "Server holder is required.");

    executor = (serverHolder.server != null) ?
               serverHolder.server.executor().orElse(MoreExecutors.directExecutor()) :
               MoreExecutors.directExecutor();
  }

  @Override
  public Executor get() {
    return executor;
  }

  static class ServerHolder {

    @Inject(optional = true) Server server = null;

  }

}
