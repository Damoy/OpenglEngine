package com.damoy.unknown.app;

import java.awt.Color;

import com.damoy.unknown.core.Core;
import com.damoy.unknown.utils.Config;

public class App {
	public static void main(String[] args) {
		// Config appConfig = new Config(240, 160, true, "", Color.WHITE, 120, 60);
		Config appConfig = new Config(1280, 720, true, "", Color.WHITE, 120, 60);
		// Config appConfig = new Config(48, 32, true, "", Color.WHITE, 120, 60);
		new Core(appConfig).start();
	}
}