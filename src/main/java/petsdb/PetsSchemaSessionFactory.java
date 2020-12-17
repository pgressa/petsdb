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
package petsdb;


import com.mysql.cj.xdevapi.Schema;
import com.mysql.cj.xdevapi.Session;
import com.mysql.cj.xdevapi.SessionFactory;
import io.micronaut.context.annotation.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Map;

/**
 * Pet schema bean factory.
 *
 * Instead of creating the schema in controller constructor, we create a Factory for the schema,
 * that is basically a way how to create beans from third party objects.
 */
@Factory
public class PetsSchemaSessionFactory {

    public static final Logger log = LoggerFactory.getLogger(PetsSchemaSessionFactory.class);
    public static final String SCHEMA_NAME = "pets";
    private final MysqlConfiguration mysqlConfiguration;

    public PetsSchemaSessionFactory(MysqlConfiguration mysqlConfiguration) {
        this.mysqlConfiguration = mysqlConfiguration;
    }

    /**
     * this generates the Schema bean. Singleton means this is used just once, but other
     * bean scopes can be used as well.
     * @return
     */
    @Singleton
    Schema schema(){
        String connectionString = "mysqlx://" + mysqlConfiguration.getUsername() +
                ":" + mysqlConfiguration.getPassword() + "@" + mysqlConfiguration.getHostname() + ":3306/pets";
        log.trace("Creating session connection to: " + connectionString);
        System.getenv().forEach((k, v) -> System.out.println(("ENV: " + k + ":" + v)));
        Session session = new SessionFactory().getSession(connectionString);
        log.trace("Session successfully created");
        return session.getSchema(SCHEMA_NAME);
    }
}
