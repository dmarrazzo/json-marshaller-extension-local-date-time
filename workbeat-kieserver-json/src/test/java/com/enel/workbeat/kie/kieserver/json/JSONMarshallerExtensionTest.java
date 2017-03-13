/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package com.enel.workbeat.kie.kieserver.json;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONMarshallerExtensionTest {
    private static final Logger logger = LoggerFactory.getLogger( MarshallerFactory.class );

    @Test
    public void testCustomExtensionMarshaller() {
        logger.debug("test");
        Set<Class<?>> extraClasses = new HashSet<Class<?>>();
        Marshaller marshaller = MarshallerFactory.getMarshaller(extraClasses, MarshallingFormat.JSON, this.getClass().getClassLoader());

        LocalDateTime dateTime = LocalDateTime.now();

        String marshall = marshaller.marshall(dateTime);
        
        System.out.println(marshall);
        assertEquals(marshall, "\""+ dateTime.toString() +"\"" );

        LocalDateTime unmarshall = marshaller.unmarshall(marshall, LocalDateTime.class);
        assertEquals(unmarshall, dateTime);
    }

}
