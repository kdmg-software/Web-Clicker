package com.pcc.app;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YouTubeEdge {

	public static final AtomicInteger COUNTER = new AtomicInteger(0);
	private final String youtube = "https://www.youtube.com/shorts/";

	private static final int VIEWS = 20;

	private static final int THREAD = 5;
	public static final TreeMap<String, Integer> videoMap = new TreeMap<>();

	@BeforeClass
	public void setup() {
		// Set appropriate path
		///String edgeDriverPath = "/home/dell/software/edgedriver_linux64/msedgedriver";
		String edgeDriverPath = "/home/dell/software/edgedriver_linux64/msedgedriver";
		System.setProperty("webdriver.edge.driver", edgeDriverPath);
		//ideoMap.put("SHORT_CODE", seconds);

	}

	@Test(threadPoolSize = THREAD, invocationCount = VIEWS)
	public void play7() throws Exception {

		for (Map.Entry<String, Integer> entry : videoMap.entrySet()) {
			String key = entry.getKey();
			Integer val = entry.getValue();
			YouTubeShortPlayer player = new YouTubeShortPlayer(COUNTER.incrementAndGet(), val, 3, youtube + key);
			player.playVideo();
		}

	}

	@Test(threadPoolSize = 1, invocationCount = VIEWS)
	public void play2() throws Exception {

		for (Map.Entry<String, Integer> entry : videoMap.entrySet()) {
			String key = entry.getKey();
			Integer val = entry.getValue();
			YouTubeShortPlayer player = new YouTubeShortPlayer(COUNTER.incrementAndGet(), val, 3, youtube + key);
			player.playVideo();
		}

	}

	@Test(threadPoolSize = 3, invocationCount = VIEWS)
	public void playAllInSingleBrowser() throws Exception {
		YouTubeShortPlayer player = new YouTubeShortPlayer();
		player.playAllInSingleBrowser();
	}

	@Test(threadPoolSize = 1, invocationCount = 1)
	public void playAllInSingleBrowserWithRandom() throws Exception {
		YouTubeShortPlayer player = new YouTubeShortPlayer();
		player.playAllInSingleBrowserWithRandom();
	}

}
