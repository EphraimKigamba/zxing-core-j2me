/*
 * Copyright 2008 ZXing authors
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
 * Parses a "geo:" URI result, which specifies a location on the surface of
 * the Earth as well as an optional altitude above the surface. See
 * <a href="http://tools.ietf.org/html/draft-mayrhofer-geo-uri-00">
 * http://tools.ietf.org/html/draft-mayrhofer-geo-uri-00</a>.
 *
 * @author Sean Owen
 */
public final class GeoResultParser extends ResultParser {

//  private static final Pattern GEO_URL_PATTERN = 
//      Pattern.compile("geo:([\\-0-9.]+),([\\-0-9.]+)(?:,([\\-0-9.]+))?(?:\\?(.*))?", Pattern.CASE_INSENSITIVE);
  
    private static final RE GEO_URL_PATTERN=new RE("geo:([\\\\-0-9.]+),([\\\\-0-9.]+)(?:,([\\\\-0-9.]+))?(?:\\\\?(.*))?",RE.MATCH_CASEINDEPENDENT);
    
//  @Override
  public ParsedResult parse(Result result) {
    String rawText = getMassagedText(result);
//    Matcher matcher = GEO_URL_PATTERN.matcher(rawText);
//    if (!matcher.matches()) {
//      return null;
//    }
    if(!GEO_URL_PATTERN.match(rawText)){
        return null;
    }

    String query = GEO_URL_PATTERN.getParen(4);

    double latitude;
    double longitude;
    double altitude;
    try {
      latitude = Double.parseDouble(GEO_URL_PATTERN.getParen(1));
      if (latitude > 90.0 || latitude < -90.0) {
        return null;
      }
      longitude = Double.parseDouble(GEO_URL_PATTERN.getParen(2));
      if (longitude > 180.0 || longitude < -180.0) {
        return null;
      }
      if (GEO_URL_PATTERN.getParen(3) == null) {
        altitude = 0.0;
      } else {
        altitude = Double.parseDouble(GEO_URL_PATTERN.getParen(3));
        if (altitude < 0.0) {
          return null;
        }
      }
    } catch (NumberFormatException nfe) {
      return null;
    }
    return new GeoParsedResult(latitude, longitude, altitude, query);
  }

}