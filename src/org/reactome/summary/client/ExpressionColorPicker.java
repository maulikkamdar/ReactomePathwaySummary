/* Copyright (c) 2012 European Bioinformatics Institute and Cold Spring Harbor Laboratory. */

package org.reactome.summary.client;

import java.util.ArrayList;
import java.util.List;

/**
 * Choose an appropriate color for an expression value.
 * 
 * @author David Croft
 *
 */
public class ExpressionColorPicker {
	private static final int BIN_COUNT = 6;
	private double binSize = -1.0;
	private double min;
	private double max;
	private static Color positiveBaseColor = new Color(15, 15, 0);
	private static Color negativeBaseColor = new Color(5, 6, 15);
	private static List<Color> positiveForegroundColors = null;
	private static List<Color> negativeForegroundColors = null;
	private static List<Color> positiveBackgroundColors = null;
	private static List<Color> negativeBackgroundColors = null;
	
	static {
		createColors();
	}
	
	public ExpressionColorPicker(double min, double max) {
		this.min = min;
		this.max = max;
		
		chooseBinSize();
	}

	public Color pickForegroundColor(double level) {
		if (binSize < 0.0)
			return null;
		int binNum = pickBinNum(level);
		Color color;
		if (binNum >= 0)
			color =  positiveForegroundColors.get(binNum);
		else
			color =  negativeForegroundColors.get((-1) * binNum);
		
		return color;
	}

	public Color pickBackgroundColor(double level) {
		if (binSize < 0.0)
			return null;
		int binNum = pickBinNum(level);
		Color color;
		if (binNum >= 0)
			color = positiveBackgroundColors.get(binNum);
		else
			color = negativeBackgroundColors.get((-1) * binNum);
		
		return color;
	}
	
	private void chooseBinSize() {
		// Problem cases
		if (min > max)
			return;
		if (min == max)
			return;
		
		double range;
		if (min < 0.0 && max <= 0.0)
			range = chooseRangeMinNegMaxNeg();
		else if (min <= 0.0 && max > 0.0)
			range = chooseRangeMinNegMaxPos();
		else
			range = chooseRangeMinPosMaxPos();
		binSize = range / (double) BIN_COUNT;
	}
	
	/**
	 * Also works for max == 0.0.
	 * 
	 */
	private double chooseRangeMinNegMaxNeg() {
		double range = max - min;
		return range;
	}
	
	/**
	 * Also works for min == 0.0.
	 * 
	 */
	private double chooseRangeMinNegMaxPos() {
		double absMin = Math.abs(min);
		double range = max;
		if (absMin > range)
			range = absMin;
		return range;
	}
	
	private double chooseRangeMinPosMaxPos() {
		double range = max - min;
		return range;
	}
	
	/**
	 * Given an expression level value, pick a corresponding color bin.  If the level
	 * is negative, returns a negative bin number, so take care when using this as
	 * an index!
	 * 
	 * @param level
	 * @return
	 */
	private int pickBinNum(double level) {
		int binNum;
		if (level >= 0.0)
			binNum = pickBinNumPositiveLevel(level);
		else
			binNum = pickBinNumNegativeLevel(level);
		
		return binNum;
	}
	
	private int pickBinNumPositiveLevel(double level) {
		double binNumDouble;
		if (min > 0.0)
			binNumDouble = Math.abs(level - min) / binSize;
		else
			binNumDouble = Math.abs(level) / binSize;
		int binNum = (int) Math.ceil(binNumDouble);
		if (binNum >= BIN_COUNT )
			binNum = BIN_COUNT - 1;
		if (binNum < 0)
			binNum = 0;
		
		return binNum;
	}
	
	private int pickBinNumNegativeLevel(double level) {
		double binNumDouble;
		if (max < 0.0)
			binNumDouble = Math.abs(max - level) / binSize;
		else
			binNumDouble = Math.abs((-1.0) * level) / binSize;
		int binNum = (int) Math.ceil(binNumDouble);
		if (binNum >= BIN_COUNT )
			binNum = BIN_COUNT - 1;
		if (binNum < 0)
			binNum = 0;
		
		return binNum;
	}
	
	private static void createColors() {
		createPositiveForegroundColors();
		createNegativeForegroundColors();
		createPositiveBackgroundColors();
		createNegativeBackgroundColors();
	}

	private static void createPositiveForegroundColors() {
		if (positiveForegroundColors == null)
			positiveForegroundColors = createColors(positiveBaseColor);
	}

	private static void createNegativeForegroundColors() {
		if (negativeForegroundColors == null)
			negativeForegroundColors = createColors(negativeBaseColor);
	}

	private static void createPositiveBackgroundColors() {
		positiveBackgroundColors = createBackgroundColors();
	}

	private static void createNegativeBackgroundColors() {
		negativeBackgroundColors = createBackgroundColors();
	}

	private static List<Color> createBackgroundColors() {
		List<Color> backgroundColors = new ArrayList<Color>();
		int colorSwitchBinCount = (BIN_COUNT/2) + 1;
		for (int i=0; i<BIN_COUNT; i++) {
			Color color = new Color(0, 0, 0);
			if (i < colorSwitchBinCount)
				color = new Color(255, 255, 255);
			backgroundColors.add(color);
		}
		
		return backgroundColors;
	}

//	private static void createPositiveBackgroundColors() {
//		createNegativeForegroundColors();
//		positiveBackgroundColors = new ArrayList<Color>();
//		for (int i=BIN_COUNT-1; i>=0; i--) {
//			Color color = negativeForegroundColors.get(i);
//			positiveBackgroundColors.add(color);
//		}
//	}
//
//	private static void createNegativeBackgroundColors() {
//		createPositiveForegroundColors();
//		negativeBackgroundColors = new ArrayList<Color>();
//		for (int i=BIN_COUNT-1; i>=0; i--) {
//			Color color = positiveForegroundColors.get(i);
//			negativeBackgroundColors.add(color);
//		}
//	}
//
	private static List<Color> createColors(Color color) {
		int blue = color.getBlue();
		int green = color.getGreen();
		int red = color.getRed();
		
		int mostSaturatedValue = blue;
		if (green > mostSaturatedValue)
			mostSaturatedValue = green;
		if (red > mostSaturatedValue)
			mostSaturatedValue = red;
		
		double mostSaturatedRange = 255.0 - (double)mostSaturatedValue;
		double mostSaturatedIncrement = mostSaturatedRange / (double)BIN_COUNT;
		
		double blueRatio = (double)blue / (double) mostSaturatedValue;
		double greenRatio = (double)green / (double) mostSaturatedValue;
		double redRatio = (double)red / (double) mostSaturatedValue;
		
		double blueIncrement = blueRatio * mostSaturatedIncrement;
		double greenIncrement = greenRatio * mostSaturatedIncrement;
		double redIncrement = redRatio * mostSaturatedIncrement;
		
		List<Color> colors = new ArrayList<Color>();
		for (int i=0; i<BIN_COUNT; i++) {
			int binBlue = blue + (int)(blueIncrement + ((double)i * blueIncrement));
			if (binBlue > 255)
				binBlue = 255;
			int binGreen = green + (int)(greenIncrement + ((double)i * greenIncrement));
			if (binGreen > 255)
				binGreen = 255;
			int binRed = red + (int)(redIncrement + ((double)i * redIncrement));
			if (binRed > 255)
				binRed = 255;
			Color binColor = new Color(binRed, binGreen, binBlue);
			colors.add(binColor);
		}
		
		return colors;
	}
}
