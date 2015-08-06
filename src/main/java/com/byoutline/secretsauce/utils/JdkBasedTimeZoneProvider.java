/*
 * Copyright (C) 2013 Onavo Inc.
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
package com.byoutline.secretsauce.utils;

import org.joda.time.DateTimeZone;
import org.joda.time.tz.Provider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

/**
 * A Joda time zone provider based on the JVM's TimeZone implementation, instead of Joda's Olson database.
 * <p/>
 * This can reduce Joda's initialization time (during class loading) by several seconds on Android.
 * To wire it up, add this to your Application subclass's onCreate():
 * {@code System.setProperty("org.joda.time.DateTimeZone.Provider", JdkBasedTimeZoneProvider.class.getCanonicalName());}
 * <p/>
 * Inspired by http://stackoverflow.com/a/6298241/37020, but implemented from scratch.
 */
public class JdkBasedTimeZoneProvider implements Provider {
    private static final Set<String> AVAILABLE_IDS = new HashSet<>(Arrays.asList(TimeZone.getAvailableIDs()));

    public Set<String> getAvailableIDs() {
        return AVAILABLE_IDS;
    }

    public DateTimeZone getZone(final String id) {
        if (id == null) {
            return null;
        }

        return DateTimeZone.forOffsetMillis(TimeZone.getTimeZone(id).getOffset(System.currentTimeMillis()));
    }
}