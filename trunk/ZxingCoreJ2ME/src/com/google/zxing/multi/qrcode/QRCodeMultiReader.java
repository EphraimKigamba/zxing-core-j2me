/*
 * Copyright 2009 ZXing authors
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
package com.google.zxing.multi.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.multi.MultipleBarcodeReader;
import com.google.zxing.multi.qrcode.detector.MultiDetector;
import com.google.zxing.qrcode.QRCodeReader;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This implementation can detect and decode multiple QR Codes in an image.
 *
 * @author Sean Owen
 * @author Hannes Erven
 */
public final class QRCodeMultiReader extends QRCodeReader implements MultipleBarcodeReader {

    // private static final Result[] EMPTY_RESULT_ARRAY = new Result[0];
    private static final Vector EMPTY_RESULT_ARRAY = new Vector();

//  @Override
    public Vector decodeMultiple(BinaryBitmap image) throws NotFoundException {
        return decodeMultiple(image, null);
    }

//  @Override
    public Vector decodeMultiple(BinaryBitmap image, Hashtable hints) throws NotFoundException {
        //List<Result> results = new ArrayList<Result>();
        Vector results = new Vector();
        //DetectorResult[] detectorResults = new MultiDetector(image.getBlackMatrix()).detectMulti(hints);
        Vector detectorResults = new MultiDetector(image.getBlackMatrix()).detectMulti(hints);
        //for (DetectorResult detectorResult : detectorResults) {
        for (int i = 0; i < detectorResults.size(); i++) {
            DetectorResult detectorResult = (DetectorResult) detectorResults.elementAt(i);
            try {
                DecoderResult decoderResult = getDecoder().decode(detectorResult.getBits(), hints);
                ResultPoint[] points = detectorResult.getPoints();
                Result result = new Result(decoderResult.getText(), decoderResult.getRawBytes(), points,
                        BarcodeFormat.QR_CODE);
                //List<byte[]> byteSegments = decoderResult.getByteSegments();
                Vector byteSegments = decoderResult.getByteSegments();
                if (byteSegments != null) {
                    result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
                }
                String ecLevel = decoderResult.getECLevel();
                if (ecLevel != null) {
                    result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, ecLevel);
                }
                results.addElement(result);
            } catch (ReaderException re) {
                // ignore and continue 
            }
        }
        if (results.isEmpty()) {
            return EMPTY_RESULT_ARRAY;
        } else {
            //return results.toArray(new Result[results.size()]);
            return results;
        }
    }
}
