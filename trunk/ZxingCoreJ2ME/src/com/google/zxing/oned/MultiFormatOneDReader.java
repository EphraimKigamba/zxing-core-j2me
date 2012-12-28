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

package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.RSS14Reader;
//import com.google.zxing.oned.rss.expanded.RSSExpandedReader;

import java.util.Hashtable;
import java.util.Vector;

/**
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class MultiFormatOneDReader extends OneDReader {

  private final OneDReader[] readers;

  public MultiFormatOneDReader(Hashtable hints) {
    Vector possibleFormats = hints == null ? null :
        (Vector) hints.get(DecodeHintType.POSSIBLE_FORMATS);
    boolean useCode39CheckDigit = hints != null &&
        hints.get(DecodeHintType.ASSUME_CODE_39_CHECK_DIGIT) != null;
    Vector readers = new Vector();
    if (possibleFormats != null) {
      if (possibleFormats.contains(BarcodeFormat.EAN_13) ||
          possibleFormats.contains(BarcodeFormat.UPC_A) ||
          possibleFormats.contains(BarcodeFormat.EAN_8) ||
          possibleFormats.contains(BarcodeFormat.UPC_E)) {
        readers.addElement(new MultiFormatUPCEANReader(hints));
      }
      if (possibleFormats.contains(BarcodeFormat.CODE_39)) {
        readers.addElement(new Code39Reader(useCode39CheckDigit));
      }
      if (possibleFormats.contains(BarcodeFormat.CODE_93)) {
        readers.addElement(new Code93Reader());
      }
      if (possibleFormats.contains(BarcodeFormat.CODE_128)) {
        readers.addElement(new Code128Reader());
      }
      if (possibleFormats.contains(BarcodeFormat.ITF)) {
         readers.addElement(new ITFReader());
      }
      if (possibleFormats.contains(BarcodeFormat.CODABAR)) {
         readers.addElement(new CodaBarReader());
      }
      if (possibleFormats.contains(BarcodeFormat.RSS_14)) {
         readers.addElement(new RSS14Reader());
      }
//      if (possibleFormats.contains(BarcodeFormat.RSS_EXPANDED)){
//        readers.addElement(new RSSExpandedReader());
//      }
    }
    if (readers.isEmpty()) {
      readers.addElement(new MultiFormatUPCEANReader(hints));
      readers.addElement(new Code39Reader());
      readers.addElement(new CodaBarReader());
      readers.addElement(new Code93Reader());
      readers.addElement(new Code128Reader());
      readers.addElement(new ITFReader());
      readers.addElement(new RSS14Reader());
      //readers.addElement(new RSSExpandedReader());
    }
    //this.readers = readers.toArray(new OneDReader[readers.size()]);
    this.readers=new OneDReader[readers.size()];
    for(int i=0;i<readers.size();i++){
        this.readers[i]=(OneDReader)readers.elementAt(i);
    }
  }

//  @Override
  public Result decodeRow(int rowNumber,
                          BitArray row,
                          Hashtable hints) throws NotFoundException {
    for (int i=0;i<readers.length;i++){
      try {
        return readers[i].decodeRow(rowNumber, row, hints);
      } catch (ReaderException re) {
        // continue
      }
    }

    throw NotFoundException.getNotFoundInstance();
  }

//  @Override
  public void reset() {
    for (int i=0;i<readers.length;i++){
      readers[i].reset();
    }
  }

}
