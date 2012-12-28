/*
 * Copyright 2011 ZXing authors
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
package com.google.zxing.pdf417.encoder;

public class Compaction {

    public static Compaction AUTO = new Compaction(0);
    public static Compaction TEXT = new Compaction(1);
    public static Compaction BYTE = new Compaction(2);
    public static Compaction NUMERIC = new Compaction(3);
    int value;

    public Compaction(int v) {
        this.value = v;
    }
}
