/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package petsdb.sauron;

import io.micronaut.context.annotation.DefaultImplementation;
import io.micronaut.http.BasicAuth;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;

@DefaultImplementation(SauronDefaultClient.class)
public interface SauronOperations {

    @Get(value = "/_search", processes = MediaType.APPLICATION_JSON)
    String getLog(BasicAuth basicAuth);

    @Put(value = "/_bulk", processes = MediaType.APPLICATION_JSON)
    String addLog(BasicAuth basicAuth, @Body String log);
}
