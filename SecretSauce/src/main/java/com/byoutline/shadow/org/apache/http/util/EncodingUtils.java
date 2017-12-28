/*
 * $HeadURL: http://svn.apache.org/repos/asf/httpcomponents/httpcore/trunk/module-main/src/main/java/org/apache/http/util/EncodingUtils.java $
 * $Revision: 503413 $
 * $Date: 2007-02-04 06:22:14 -0800 (Sun, 04 Feb 2007) $
 *
 * ====================================================================
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
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package com.byoutline.shadow.org.apache.http.util;

import java.io.UnsupportedEncodingException;


/**
 * The home for utility methods that handle various encoding tasks.
 *
 * @author Michael Becke
 * @author <a href="mailto:oleg at ural.ru">Oleg Kalnichevski</a>
 * @since 4.0
 * @deprecated Please use {@link java.net.URL#openConnection} instead.
 * Please visit <a href="http://android-developers.blogspot.com/2011/09/androids-http-clients.html">this webpage</a>
 * for further details.
 */
public final class EncodingUtils {


    /**
     * This class should not be instantiated.
     */
    private EncodingUtils() {
    }

    /**
     * Converts the specified string to a byte array.  If the charset is not supported the
     * default system charset is used.
     *
     * @param data    the string to be encoded
     * @param charset the desired character encoding
     * @return The resulting byte array.
     */
    public static byte[] getBytes(final String data, final String charset) {

        if (data == null) {
            throw new IllegalArgumentException("data may not be null");
        }

        if (charset == null || charset.length() == 0) {
            throw new IllegalArgumentException("charset may not be null or empty");
        }

        try {
            return data.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            return data.getBytes();
        }
    }
}
