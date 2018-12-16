package com.damoy.unknown.utils;

import java.awt.Color;

public class Config {

	private int windowWidth;
	private int windowHeight;
	private boolean windowVSync;
	private String windowTitle;
	private Color backgroundColor;
	private int fpsCap;
	private int upsCap;
	
	public Config() {}
	
	public Config(int windowWidth, int windowHeight, boolean windowVSync, String windowTitle, Color backgroundColor, int fpsCap, int upsCap) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.windowVSync = windowVSync;
		this.windowTitle = windowTitle;
		this.backgroundColor = backgroundColor;
		this.fpsCap = fpsCap;
		this.upsCap = upsCap;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}

	public boolean getWindowVSync() {
		return windowVSync;
	}

	public void setWindowVSync(boolean windowVSync) {
		this.windowVSync = windowVSync;
	}

	public String getWindowTitle() {
		return windowTitle;
	}

	public void setWindowTitle(String windowTitle) {
		this.windowTitle = windowTitle;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getFpsCap() {
		return fpsCap;
	}

	public void setFpsCap(int fpsCap) {
		this.fpsCap = fpsCap;
	}

	public int getUpsCap() {
		return upsCap;
	}

	public void setUpsCap(int upsCap) {
		this.upsCap = upsCap;
	}
	
}