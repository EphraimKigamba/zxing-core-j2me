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

/**
 * Encapsulates a type of hint that a caller may pass to a barcode reader to
 * help it more quickly or accurately decode it. It is up to implementations to
 * decide what, if anything, to do with the information that is supplied.
 *
 * @author Sean Owen
 * @author dswitkin@google.com (Daniel Switkin)
 * @see Reader#decode(BinaryBitmap,java.util.Map)
 */

public class DecodeHintType {

    /**
     * Unspecified, application-specific hint. Maps to an unspecified
     * {@link Object}.
     */
    private int value;
    public DecodeHintType(int value){
        this.value=value;
    }
    
    public boolean equals(EncodeHintType type) {
        return getValue() == type.getValue();
    }
    
    public final static DecodeHintType OTHER = new DecodeHintType(1);
    /**
     * Image is a pure monochrome image of a barcode. Doesn't matter what it
     * maps to; use {@link Boolean#TRUE}.
     */
    public final static DecodeHintType PURE_BARCODE = new DecodeHintType(2);
    /**
     * Image is known to be of one of a few possible formats. Maps to a
     * {@link java.util.List} of {@link BarcodeFormat}s.
     */
    public final static DecodeHintType POSSIBLE_FORMATS = new DecodeHintType(3);
    /**
     * Spend more time to try to find a barcode; optimize for accuracy, not
     * speed. Doesn't matter what it maps to; use {@link Boolean#TRUE}.
     */
    public final static DecodeHintType TRY_HARDER = new DecodeHintType(4);
    /**
     * Specifies what character encoding to use when decoding, where applicable
     * (type String)
     */
    public final static DecodeHintType CHARACTER_SET = new DecodeHintType(5);
    /**
     * Allowed lengths of encoded data -- reject anything else. Maps to an
     * int[].
     */
    public final static DecodeHintType ALLOWED_LENGTHS = new DecodeHintType(6);
    /**
     * Assume Code 39 codes employ a check digit. Maps to {@link Boolean}.
     */
    public final static DecodeHintType ASSUME_CODE_39_CHECK_DIGIT = new DecodeHintType(7);
    /**
     * The caller needs to be notified via callback when a possible
     * {@link ResultPoint} is found. Maps to a {@link ResultPointCallback}.
     */
    public final static DecodeHintType NEED_RESULT_POINT_CALLBACK = new DecodeHintType(8);

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
