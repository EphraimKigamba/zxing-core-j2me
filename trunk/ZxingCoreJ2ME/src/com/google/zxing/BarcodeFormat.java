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
package com.google.zxing;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Enumerates barcode formats known to this package. Please keep alphabetized.
 *
 * @author Sean Owen
 */
public class BarcodeFormat {

    private static final String names[] = new String[]{"", "AZTEC", "CODABAR", "CODE_39", "CODE_93", "CODE_128", "DATA_MATRIX", "EAN_8", "EAN_13", "ITF", "MAXICODE", "PDF_417", "QR_CODE", "RSS_14", "RSS_EXPANDED", "UPC_A", "UPC_E", "UPC_EAN_EXTENSION"};
    private static final Hashtable VALUE_TO_BarcodeFormat = new Hashtable();
    private static final Hashtable NAME_TO_BarcodeFormat = new Hashtable();
    /**
     * Aztec 2D barcode format.
     */
    public final static BarcodeFormat AZTEC = new BarcodeFormat(1);
    /**
     * CODABAR 1D format.
     */
    public final static BarcodeFormat CODABAR = new BarcodeFormat(2);
    /**
     * Code 39 1D format.
     */
    public final static BarcodeFormat CODE_39 = new BarcodeFormat(3);
    /**
     * Code 93 1D format.
     */
    public final static BarcodeFormat CODE_93 = new BarcodeFormat(4);
    /**
     * Code 128 1D format.
     */
    public final static BarcodeFormat CODE_128 = new BarcodeFormat(5);
    /**
     * Data Matrix 2D barcode format.
     */
    public final static BarcodeFormat DATA_MATRIX = new BarcodeFormat(6);
    /**
     * EAN-8 1D format.
     */
    public final static BarcodeFormat EAN_8 = new BarcodeFormat(7);
    /**
     * EAN-13 1D format.
     */
    public final static BarcodeFormat EAN_13 = new BarcodeFormat(8);
    /**
     * ITF (Interleaved Two of Five) 1D format.
     */
    public final static BarcodeFormat ITF = new BarcodeFormat(9);
    /**
     * MaxiCode 2D barcode format.
     */
    public final static BarcodeFormat MAXICODE = new BarcodeFormat(10);
    /**
     * PDF417 format.
     */
    public final static BarcodeFormat PDF_417 = new BarcodeFormat(11);
    /**
     * QR Code 2D barcode format.
     */
    public final static BarcodeFormat QR_CODE = new BarcodeFormat(12);
    /**
     * RSS 14
     */
    public final static BarcodeFormat RSS_14 = new BarcodeFormat(13);
    /**
     * RSS EXPANDED
     */
    public final static BarcodeFormat RSS_EXPANDED = new BarcodeFormat(14);
    /**
     * UPC-A 1D format.
     */
    public final static BarcodeFormat UPC_A = new BarcodeFormat(15);
    /**
     * UPC-E 1D format.
     */
    public final static BarcodeFormat UPC_E = new BarcodeFormat(16);
    /**
     * UPC/EAN extension format. Not a stand-alone format.
     */
    // public final static BarcodeFormat UPC_EAN_EXTENSION = 17;    
    public final static BarcodeFormat UPC_EAN_EXTENSION = new BarcodeFormat(17);

    public BarcodeFormat(int value) {
        this.bitValue = value;
        VALUE_TO_BarcodeFormat.put(new Integer(value), this);
        if (value > 0 && value <= names.length) {
            NAME_TO_BarcodeFormat.put(names[value], this);
        }
    }
    public int bitValue;

    public boolean equals(BarcodeFormat format) {
        return bitValue == format.getBitValue();
    }

    /**
     * @return the bitValue
     */
    public int getBitValue() {
        return bitValue;
    }

    /**
     * @param aBitValue the bitValue to set
     */
    public void setBitValue(int aBitValue) {
        bitValue = aBitValue;
    }

    public static BarcodeFormat getBarcodeFormatByName(String name) {
        return (BarcodeFormat) NAME_TO_BarcodeFormat.get(name);
    }

    public static BarcodeFormat getBarcodeFormatByValue(int value) {
        return (BarcodeFormat) VALUE_TO_BarcodeFormat.get(new Integer(value));
    }

    public String toString() {
        return names[bitValue];
    }
}
