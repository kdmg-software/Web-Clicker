package com.pcc.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class YouTubeShortPlayer extends Thread {
	private int currentCounter;
	private int videoLength;
	private int startDelay;
	private String videoPath;

	private final String youtube = "https://www.youtube.com/shorts/";

	public YouTubeShortPlayer(int currentCounter, int videoLength, int startDelay, String videoPath) {
		super();
		this.currentCounter = currentCounter;
		this.videoLength = videoLength;
		this.startDelay = startDelay;
		this.videoPath = videoPath;

	}

	public YouTubeShortPlayer() {

	}

	public void playVideo() {
		try {
			System.out.println(this.currentCounter + " Starting");
			WebDriver webDriver = getWebDriver();
			webDriver.get(this.videoPath);
			Thread.sleep(this.startDelay * 1000);
			WebElement playButton = webDriver.findElement(By.cssSelector("button.ytp-large-play-button"));
			playButton.click();
			Thread.sleep(this.videoLength * 1000 + 3000);
			if (webDriver != null) {
				webDriver.close();
			}
			System.out.println(this.currentCounter + " Completed");
		} catch (Exception e) {
			System.out.println(this.currentCounter + " Error : " + e.getMessage());

		}

	}

	public void run() {
		System.out.println("Thread is running...");
		playAllInSingleBrowser();
	}

	public void playAllInSingleBrowser() {
		try {
			System.out.println(this.currentCounter + " Starting");

			for (Map.Entry<String, Integer> entry : YouTubeEdge.videoMap.entrySet()) {
				WebDriver webDriver = getWebDriver();
				String key = entry.getKey();
				Integer val = entry.getValue();
				webDriver.get(youtube + key);
				Thread.sleep(3000);
				WebElement playButton = webDriver.findElement(By.cssSelector("button.ytp-large-play-button"));
				playButton.click();
				Thread.sleep(val * 1000);
				System.out.println(this.currentCounter + " Completed");
				if (webDriver != null) {
					webDriver.close();
				}
			}

		} catch (Exception e) {
			System.out.println(this.currentCounter + " Error : " + e.getMessage());

		}

	}

	public void playAllInSingleBrowserWithRandom() {

		System.out.println(this.currentCounter + " Starting");
		List<Map.Entry<String, Integer>> list = new ArrayList<>(YouTubeEdge.videoMap.entrySet());

		

		List<Map.Entry<String, Integer>> resultList = new ArrayList<>();

		for (int i = 0; i <= 100; i++) {
			Random random = new Random();
			int randomIndex = random.nextInt(list.size());

			System.out.println(i+">"+Thread.currentThread().getName()+">"+randomIndex);
			Map.Entry<String, Integer> randomEntry = list.get(randomIndex);
			resultList.add(randomEntry);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		 * for (Map.Entry<String, Integer> entry : resultList) { WebDriver webDriver =
		 * null; try { webDriver = getWebDriver(); String key = entry.getKey(); Integer
		 * val = entry.getValue(); webDriver.get(youtube + key); Thread.sleep(3000);
		 * WebElement playButton =
		 * webDriver.findElement(By.cssSelector("button.ytp-large-play-button"));
		 * playButton.click(); Thread.sleep(val * 1000);
		 * System.out.println(this.currentCounter + " Completed"); if (webDriver !=
		 * null) { webDriver.close(); } } catch (Exception e) {
		 * System.out.println(this.currentCounter + " Error : " + e.getMessage()); if
		 * (webDriver != null) { webDriver.close(); }
		 * 
		 * } }
		 */
	}

	private WebDriver getWebDriver() {
		EdgeOptions edgeOptions = new EdgeOptions();
		edgeOptions.addArguments("--remote-allow-origins=*");
		WebDriver webDriver = new EdgeDriver(edgeOptions);
		Point windowPosition = new Point(currentCounter * 10 + 20, currentCounter * 10 + 20); // X and Y coordinates
		webDriver.manage().window().setPosition(windowPosition);
		return webDriver;
	}

}
