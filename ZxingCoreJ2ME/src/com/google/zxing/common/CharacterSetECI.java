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
package com.google.zxing.common;

import com.google.zxing.FormatException;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Encapsulates a Character Set ECI, according to "Extended Channel
 * Interpretations" 5.3.1.1 of ISO 18004.
 *
 * @author Sean Owen
 */
public class CharacterSetECI {
// Enum name is a Java encoding valid for java.lang and java.io

    private static final Hashtable VALUE_TO_ECI = new Hashtable();
    private static final Hashtable NAME_TO_ECI = new Hashtable();
    private static final Vector vecValues = new Vector();
    public static CharacterSetECI Cp437 = new CharacterSetECI(new int[]{0, 2}, new String[]{"Cp437"});
    public static CharacterSetECI ISO8859_1 = new CharacterSetECI(new int[]{1, 3}, new String[]{"ISO-8859-1", "ISO8859_1"});
    public static CharacterSetECI ISO8859_2 = new CharacterSetECI(4, new String[]{"ISO-8859-2", "ISO8859_2"});
    public static CharacterSetECI ISO8859_3 = new CharacterSetECI(5, new String[]{"ISO-8859-3", "ISO8859_3"});
    public static CharacterSetECI ISO8859_4 = new CharacterSetECI(6, new String[]{"ISO-8859-4", "ISO8859_4"});
    public static CharacterSetECI ISO8859_5 = new CharacterSetECI(7, new String[]{"ISO-8859-5", "ISO8859_5"});
    public static CharacterSetECI ISO8859_6 = new CharacterSetECI(8, new String[]{"ISO-8859-6", "ISO8859_6"});
    public static CharacterSetECI ISO8859_7 = new CharacterSetECI(9, new String[]{"ISO-8859-7", "ISO8859_7"});
    public static CharacterSetECI ISO8859_8 = new CharacterSetECI(10, new String[]{"ISO-8859-8", "ISO8859_8"});
    public static CharacterSetECI ISO8859_9 = new CharacterSetECI(11, new String[]{"ISO-8859-9", "ISO8859_9"});
    public static CharacterSetECI ISO8859_10 = new CharacterSetECI(12, new String[]{"ISO-8859-10", "ISO8859_10"});
    public static CharacterSetECI ISO8859_11 = new CharacterSetECI(13, new String[]{"ISO-8859-11", "ISO8859_11"});
    public static CharacterSetECI ISO8859_13 = new CharacterSetECI(15, new String[]{"ISO-8859-13", "ISO8859_13"});
    public static CharacterSetECI ISO8859_14 = new CharacterSetECI(16, new String[]{"ISO-8859-14", "ISO8859_14"});
    public static CharacterSetECI ISO8859_15 = new CharacterSetECI(17, new String[]{"ISO-8859-15", "ISO8859_15"});
    public static CharacterSetECI ISO8859_16 = new CharacterSetECI(18, new String[]{"ISO-8859-16", "ISO8859_16"});
    public static CharacterSetECI SJIS = new CharacterSetECI(20, new String[]{"Shift_JIS", "SJIS"});
    public static CharacterSetECI Cp1250 = new CharacterSetECI(21, new String[]{"windows-1250", "Cp1250"});
    public static CharacterSetECI Cp1251 = new CharacterSetECI(22, new String[]{"windows-1251", "Cp1251"});
    public static CharacterSetECI Cp1252 = new CharacterSetECI(23, new String[]{"windows-1252", "Cp1252"});
    public static CharacterSetECI Cp1256 = new CharacterSetECI(24, new String[]{"windows-1256", "Cp1256"});
    public static CharacterSetECI UnicodeBigUnmarked = new CharacterSetECI(25, new String[]{"UTF-16BE", "UnicodeBig", "UnicodeBigUnmarked"});
    public static CharacterSetECI UTF8 = new CharacterSetECI(26, new String[]{"UTF-8", "UTF8"});
    public static CharacterSetECI ASCII = new CharacterSetECI(new int[]{27, 170}, new String[]{"US-ASCII", "ASCII"});
    public static CharacterSetECI Big5 = new CharacterSetECI(28, new String[]{"Big5"});
    public static CharacterSetECI GB18030 = new CharacterSetECI(29, new String[]{"GB2312", "EUC_CN", "GBK", "GB18030"});
    public static CharacterSetECI EUC_KR = new CharacterSetECI(30, new String[]{"EUC-KR", "EUC_KR"});
//  private static final Map<Integer,CharacterSetECI> VALUE_TO_ECI = new HashMap<Integer,CharacterSetECI>();
//  private static final Map<String,CharacterSetECI> NAME_TO_ECI = new HashMap<String,CharacterSetECI>();

    static {
        for (int j = 0; j < vecValues.size(); j++) {
            CharacterSetECI eci = (CharacterSetECI) vecValues.elementAt(j);
            for (int i = 0; i < eci.values.length; i++) {
                VALUE_TO_ECI.put(new Integer(eci.values[i]), eci);
            }
            NAME_TO_ECI.put(eci.name, eci);
            for (int i = 0; i < eci.otherEncodingNames.length; i++) {
                NAME_TO_ECI.put(eci.otherEncodingNames[i], eci);
            }
        }
    }
    /**
     * @return the vecValues
     */
//    private static Vector getVecValues() {
//        if (vecValues == null) {
//            vecValues = new Vector();
//        }
//        return vecValues;
//    }
    private final int[] values;
    private final String[] otherEncodingNames;
    private final String name;

    public CharacterSetECI(int value) {
        this(new int[]{value}, new String[]{""});
    }

    public CharacterSetECI(int[] value) {
        this(value, new String[]{""});
    }

    public CharacterSetECI(int[] value, String otherEncodingName) {
        this(value, new String[]{otherEncodingName});
    }

    public CharacterSetECI(int value, String otherEncodingName) {
        this(new int[]{value}, new String[]{otherEncodingName});
    }

    public CharacterSetECI(int value, String[] otherEncodingNames) {
        this(new int[]{value}, otherEncodingNames);
    }

    public CharacterSetECI(int[] values, String[] otherEncodingNames) {
        this.values = values;
        this.otherEncodingNames = otherEncodingNames;
        this.name = otherEncodingNames[otherEncodingNames.length - 1];

        vecValues.addElement(this);

//        for (int i = 0; i < this.values.length; i++) {
//            VALUE_TO_ECI.put(new Integer(this.values[i]), this);
//        }
//        NAME_TO_ECI.put(this.name, this);
//        for (int i = 0; i < this.otherEncodingNames.length; i++) {
//            NAME_TO_ECI.put(otherEncodingNames[i], this);
//        }
    }

    public int getValue() {
        return values[0];
    }

    /**
     * @param value character set ECI value
     * @return CharacterSetECI representing ECI of given value, or null if it is
     * legal but unsupported
     * @throws IllegalArgumentException if ECI value is invalid
     */
    public static CharacterSetECI getCharacterSetECIByValue(int value) throws FormatException {
        if (value < 0 || value >= 900) {
            throw FormatException.getFormatInstance();
        }
        return (CharacterSetECI) VALUE_TO_ECI.get(new Integer(value));
    }

    /**
     * @param name character set ECI encoding name
     * @return CharacterSetECI representing ECI for character encoding, or null
     * if it is legal but unsupported
     */
    public static CharacterSetECI getCharacterSetECIByName(String name) {
        return (CharacterSetECI) NAME_TO_ECI.get(name);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
}
