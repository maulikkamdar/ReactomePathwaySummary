/*
 * Copyright 2006 Robert Hanson <iamroberthanson AT gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * Changed 2012 by David Croft to remove unneeded functionality and add
 * a String constructor.
 */

package org.reactome.summary.client;

public class Color {
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    
    public Color (String colorText) {
        if (colorText.matches("^#[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]$")) {
        	red = Integer.decode("0x" + colorText.substring(1, 2)).intValue();
        	green = Integer.decode("0x" + colorText.substring(3, 4)).intValue();
        	red = Integer.decode("0x" + colorText.substring(5, 6)).intValue();
        }
    }

    public Color (int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public String getHexValue() {
        return "#" + pad(Integer.toHexString(red)) + pad(Integer.toHexString(green)) + pad(Integer.toHexString(blue));
    }

    private String pad (String in) {
        if (in.length() == 0)
            return "00";
        if (in.length() == 1)
            return "0" + in;
        return in;
    }
}
