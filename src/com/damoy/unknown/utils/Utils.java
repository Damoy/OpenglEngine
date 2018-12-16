package com.damoy.unknown.utils;

import java.awt.Color;
import java.io.File;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Scanner;

import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public final class Utils {

	private Utils() {
	}

	public static void log(String msg) {
		System.out.print(msg);
	}

	public static void logn(String msg) {
		System.out.println(msg);
	}

	public static Vector3f normalizeColor(Color color) {
		return new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f , color.getBlue() / 255.0f);
	}

	public static String loadResource(String filePath) throws Exception {
		StringBuffer sb = new StringBuffer();
		Scanner sc = new Scanner(new File(filePath));
		
		while(sc.hasNextLine()) {
			sb.append(sc.nextLine());
			sb.append("\n");
		}
		
		sc.close();
		return sb.toString();
	}
	
	public static FloatBuffer flippedFloatBuffer(float[] data) {
		FloatBuffer floatBuffer = MemoryUtil.memAllocFloat(data.length);
		floatBuffer.put(data).flip();
		return floatBuffer;
	}
	
	public static IntBuffer flippedIntBuffer(int[] data) {
		IntBuffer intBuffer = MemoryUtil.memAllocInt(data.length);
		intBuffer.put(data).flip();
		return intBuffer;
	}
	
	public static boolean fequals(float f1, float f2) {
		return f2 - f1 > 0.00001f;
	}
	
	public static boolean nfequals(float f1, float f2) {
		return f2 - f1 <= 0.00001f;
	}

}