/*
 * Copyright 2007 ZXing authors
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

import com.google.zxing.Result;
import makeit.zxing.common.Library;
import java.util.Hashtable;
import java.util.Vector;
import me.regexp.RE;

/**
 * <p>Abstract class representing the result of decoding a barcode, as more than
 * a String -- as some type of structured data. This might be a subclass which
 * represents a URL, or an e-mail address. {@link #parseResult(Result)} will
 * turn a raw decoded string into the most appropriate type of structured
 * representation.</p>
 *
 * <p>Thanks to Jeff Griffin for proposing rewrite of these classes that relies
 * less on exception-based mechanisms during parsing.</p>
 *
 * @author Sean Owen
 */
public abstract class ResultParser {

    private static final ResultParser[] PARSERS = {
        new BookmarkDoCoMoResultParser(),
        new AddressBookDoCoMoResultParser(),
        new EmailDoCoMoResultParser(),
        new AddressBookAUResultParser(),
        new VCardResultParser(),
        new BizcardResultParser(),
        //            new VEventResultParser(),
        new EmailAddressResultParser(),
        new SMTPResultParser(),
        new TelResultParser(),
        new SMSMMSResultParser(),
        new SMSTOMMSTOResultParser(),
        new GeoResultParser(),
        new WifiResultParser(),
        new URLTOResultParser(),
        new URIResultParser(),
        new ISBNResultParser(),
        new ProductResultParser(),
        new ExpandedProductResultParser(),};
//    private static final Pattern DIGITS = Pattern.compile("\\d*");
//    private static final Pattern ALPHANUM = Pattern.compile("[a-zA-Z0-9]*");
//    private static final Pattern AMPERSAND = Pattern.compile("&");
//    private static final Pattern EQUALS = Pattern.compile("=");
    private static final RE DIGITS = new RE("\\d*");
    private static final RE ALPHANUM = new RE("[a-zA-Z0-9]*");
    private static final RE AMPERSAND = new RE("&");
    private static final RE EQUALS = new RE("=");
    private static final String BYTE_ORDER_MARK = "\ufeff";

    /**
     * Attempts to parse the raw {@link Result}'s contents as a particular type
     * of information (email, URL, etc.) and return a {@link ParsedResult}
     * encapsulating the result of parsing.
     */
    public abstract ParsedResult parse(Result theResult);

    protected static String getMassagedText(Result result) {
        String text = result.getText();
        if (text.startsWith(BYTE_ORDER_MARK)) {
            text = text.substring(1);
        }
        return text;
    }

    public static ParsedResult parseResult(Result theResult) {
        for (int i = 0; i < PARSERS.length; i++) {
            ParsedResult result = PARSERS[i].parse(theResult);
            if (result != null) {
                return result;
            }
        }
        return new TextParsedResult(theResult.getText(), null);
    }

    protected static void maybeAppend(String value, StringBuffer result) {
        if (value != null) {
            result.append('\n');
            result.append(value);
        }
    }

    protected static void maybeAppend(String[] value, StringBuffer result) {
        if (value != null) {
            // for (String s : value) {
            for (int i = 0; i < value.length; i++) {
                result.append('\n');
                result.append(value[i]);
            }
        }
    }

    protected static String[] maybeWrap(String value) {
        return value == null ? null : new String[]{value};
    }

    protected static String unescapeBackslash(String escaped) {
        int backslash = escaped.indexOf((int) '\\');
        if (backslash < 0) {
            return escaped;
        }
        int max = escaped.length();
        StringBuffer unescaped = new StringBuffer(max - 1);
        unescaped.append(escaped.toCharArray(), 0, backslash);
        boolean nextIsEscaped = false;
        for (int i = backslash; i < max; i++) {
            char c = escaped.charAt(i);
            if (nextIsEscaped || c != '\\') {
                unescaped.append(c);
                nextIsEscaped = false;
            } else {
                nextIsEscaped = true;
            }
        }
        return unescaped.toString();
    }

    protected static int parseHexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'a' && c <= 'f') {
            return 10 + (c - 'a');
        }
        if (c >= 'A' && c <= 'F') {
            return 10 + (c - 'A');
        }
        return -1;
    }

    protected static boolean isStringOfDigits(String value, int length) {
        // return value != null && length == value.length() && DIGITS.matcher(value).matches();
        return false;
    }

    protected static boolean isSubstringOfDigits(String value, int offset, int length) {
        if (value == null) {
            return false;
        }
        int max = offset + length;
        //return value.length() >= max && DIGITS.matcher(value.subSequence(offset, max)).matches();
        return value.length() >= max && DIGITS.match(value.substring(offset, max));
    }

    protected static boolean isSubstringOfAlphaNumeric(String value, int offset, int length) {
        if (value == null) {
            return false;
        }
        int max = offset + length;
        //return value.length() >= max && ALPHANUM.matcher(value.subSequence(offset, max)).matches();
        return value.length() >= max && ALPHANUM.match(value.substring(offset, max));
    }

    //static Map<String, String> parseNameValuePairs(String uri) {
    static Hashtable parseNameValuePairs(String uri) {
        int paramStart = uri.indexOf('?');
        if (paramStart < 0) {
            return null;
        }
        Hashtable result = new Hashtable();
        String[] strs = AMPERSAND.split(uri.substring(paramStart + 1));
        //for (String keyValue : AMPERSAND.split(uri.substring(paramStart + 1))) {
        for (int i = 0; i < strs.length; i++) {
            appendKeyValue(strs[i], result);
        }
        return result;
    }
    //private static void appendKeyValue(String keyValue, Map<String, String> result) {

    private static void appendKeyValue(String keyValue, Hashtable result) {
//        //String[] keyValueTokens = EQUALS.split(keyValue, 2);
//        String[] keyValueTokens = EQUALS.split(keyValue);
//        if (keyValueTokens.length == 2) {
//            String key = keyValueTokens[0];
//            String value = keyValueTokens[1];
//            try {
//                value = URLDecoder.decode(value, "UTF-8");
//                result.put(key, value);
//            } catch (UnsupportedEncodingException uee) {
//                throw new IllegalStateException(uee); // can't happen
//            } catch (IllegalArgumentException iae) {
//                // continue; invalid data such as an escape like %0t
//            }
//        }
    }

    static String[] matchPrefixedField(String prefix, String rawText, char endChar, boolean trim) {
        //List<String> matches = null;
        Vector matches = null;
        int i = 0;
        int max = rawText.length();
        while (i < max) {
            i = rawText.indexOf(prefix, i);
            if (i < 0) {
                break;
            }
            i += prefix.length(); // Skip past this prefix we found to start
            int start = i; // Found the start of a match here
            boolean more = true;
            while (more) {
                i = rawText.indexOf((int) endChar, i);
                if (i < 0) {
                    // No terminating end character? uh, done. Set i such that loop terminates and break
                    i = rawText.length();
                    more = false;
                } else if (rawText.charAt(i - 1) == '\\') {
                    // semicolon was escaped so continue
                    i++;
                } else {
                    // found a match
                    if (matches == null) {
                        matches = new Vector(3); // lazy init
                    }
                    String element = unescapeBackslash(rawText.substring(start, i));
                    if (trim) {
                        element = element.trim();
                    }
                    matches.addElement(element);
                    i++;
                    more = false;
                }
            }
        }
        if (matches == null || matches.isEmpty()) {
            return null;
        }
        //return matches.toArray(new String[matches.size()]);
        return (String[]) Library.toArray(matches);
    }

    static String matchSinglePrefixedField(String prefix, String rawText, char endChar, boolean trim) {
        String[] matches = matchPrefixedField(prefix, rawText, endChar, trim);
        return matches == null ? null : matches[0];
    }
}
