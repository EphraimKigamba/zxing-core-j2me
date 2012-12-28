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
package com.google.zxing;

/**
 * These are a set of hints that you may pass to Writers to specify their
 * behavior.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public class EncodeHintType {

    private int value;

    public EncodeHintType(int v) {
        this.value = v;
    }

    public boolean equals(EncodeHintType type) {
        return getValue() == type.getValue();
    }
    /**
     * Specifies what degree of error correction to use, for example in QR
     * Codes. Type depends on the encoder. For example for QR codes it's type
     * {@link com.google.zxing.qrcode.decoder.ErrorCorrectionLevel ErrorCorrectionLevel}.
     */
    public final static EncodeHintType ERROR_CORRECTION = new EncodeHintType(1);
    /**
     * Specifies what character encoding to use where applicable (type
     * {@link String})
     */
    public final static EncodeHintType CHARACTER_SET = new EncodeHintType(2);
    /**
     * Specifies margin, in pixels, to use when generating the barcode. The
     * meaning can vary by format; for example it controls margin before and
     * after the barcode horizontally for most 1D formats. (Type
     * {@link Integer}).
     */
    public final static EncodeHintType MARGIN = new EncodeHintType(3);
    /**
     * Specifies whether to use compact mode for PDF417 (type {@link Boolean}).
     */
    public final static EncodeHintType PDF417_COMPACT = new EncodeHintType(4);
    /**
     * Specifies what compaction mode to use for PDF417 (type
     * {@link com.google.zxing.pdf417.encoder.Compaction Compaction}).
     */
    public final static EncodeHintType PDF417_COMPACTION = new EncodeHintType(5);
    /**
     * Specifies the minimum and maximum number of rows and columns for PDF417
     * (type {@link com.google.zxing.pdf417.encoder.Dimensions Dimensions}).
     */
    public final static EncodeHintType PDF417_DIMENSIONS = new EncodeHintType(6);

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
}
