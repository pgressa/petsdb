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

import io.micronaut.http.BasicAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

/**
 * Default imlementation of client, since I don't have access to sauron and
 * can't properly test.
 */
@Singleton
public class SauronDefaultClient implements SauronOperations{

    public static final Logger logger = LoggerFactory.getLogger(SauronDefaultClient.class);

    @Override
    public String getLog(BasicAuth basicAuth) {
        logger.info("Using default sauron client: getLog");
        return "undefined";
    }

    @Override
    public String addLog(BasicAuth basicAuth, String log) {
        logger.info("Using default sauron client: addLog");
        return log;
    }
}
