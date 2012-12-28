/*
 * Copyright 2010 ZXing authors
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
package com.google.zxing.client.result;

/**
 * Represents the type of data encoded by a barcode -- from plain text, to a
 * URI, to an e-mail address, etc.
 *
 * @author Sean Owen
 */
public class ParsedResultType {

    private static int value;

    /**
     * @return the value
     */
    public static int getValue() {
        return value;
    }

    public ParsedResultType(int value) {
        this.value = value;
    }
    public static ParsedResultType ADDRESSBOOK = new ParsedResultType(0);
    public static ParsedResultType EMAIL_ADDRESS = new ParsedResultType(1);
    public static ParsedResultType PRODUCT = new ParsedResultType(2);
    public static ParsedResultType URI = new ParsedResultType(3);
    public static ParsedResultType TEXT = new ParsedResultType(4);
    public static ParsedResultType GEO = new ParsedResultType(5);
    public static ParsedResultType TEL = new ParsedResultType(6);
    public static ParsedResultType SMS = new ParsedResultType(7);
    public static ParsedResultType CALENDAR = new ParsedResultType(8);
    public static ParsedResultType WIFI = new ParsedResultType(9);
    public static ParsedResultType ISBN = new ParsedResultType(10);
}
