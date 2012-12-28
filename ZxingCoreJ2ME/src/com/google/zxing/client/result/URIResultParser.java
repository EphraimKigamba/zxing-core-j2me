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
import me.regexp.*;

/**
 * Tries to parse results that are a URI of some kind.
 *
 * @author Sean Owen
 */
public final class URIResultParser extends ResultParser {

    private static final String ALPHANUM_PART = "[a-zA-Z0-9\\-]";
//////  private static final Pattern URL_WITH_PROTOCOL_PATTERN = Pattern.compile("[a-zA-Z0-9]{2,}:");
//////  private static final Pattern URL_WITHOUT_PROTOCOL_PATTERN = Pattern.compile(
//////      '(' + ALPHANUM_PART + "+\\.)+" + ALPHANUM_PART + "{2,}" + // host name elements
//////      "(:\\d{1,5})?" + // maybe port
//////      "(/|\\?|$)"); // query, path or nothing
    private static final RE URL_WITH_PROTOCOL_PATTERN = new RE("[a-zA-Z0-9]{2,}:");
    private static final RE URL_WITHOUT_PROTOCOL_PATTERN = new RE(
            '(' + ALPHANUM_PART + "+\\.)+" + ALPHANUM_PART + "{2,}" + // host name elements
            "(:\\d{1,5})?" + // maybe port
            "(/|\\?|$)"); // query, path or nothing

//  @Override
    public ParsedResult parse(Result result) {
        String rawText = getMassagedText(result);
        // We specifically handle the odd "URL" scheme here for simplicity and add "URI" for fun
        // Assume anything starting this way really means to be a URI
        if (rawText.startsWith("URL:") || rawText.startsWith("URI:")) {
            return new URIParsedResult(rawText.substring(4).trim(), null);
        }
        rawText = rawText.trim();
        return isBasicallyValidURI(rawText) ? new URIParsedResult(rawText, null) : null;
    }

    static boolean isBasicallyValidURI(String uri) {
//////    Matcher m = URL_WITH_PROTOCOL_PATTERN.matcher(uri);
//////    if (m.find() && m.start() == 0) { // match at start only
//////      return true;
//////    }
//////    m = URL_WITHOUT_PROTOCOL_PATTERN.matcher(uri);
//////    return m.find() && m.start() == 0;
        if (URL_WITH_PROTOCOL_PATTERN.match(uri) && URL_WITH_PROTOCOL_PATTERN.getParenStart(1) == 0) {
            return true;
        }
        return (URL_WITH_PROTOCOL_PATTERN.match(uri) && URL_WITH_PROTOCOL_PATTERN.getParenStart(1) == 0);
    }
}