package examples;


import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.network.Network;
import org.openqa.selenium.devtools.network.model.Headers;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasicAuth {
	
	public static void main(String[] args) {
		
	
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();	
				
		String username = "admin"; // authentication username
		String password = "admin"; // authentication password
		
		// Get the devtools from the running driver and create a session
		DevTools devTools = driver.getDevTools();		
		devTools.createSession(); 
		
		// Enable the Network domain of devtools
		devTools.send(Network.enable(Optional.of(100000), Optional.of(100000), Optional.of(100000)));
		String auth = username +":"+ password;
		
		// Encoding the username and password using Base64 (java.util)
		String encodeToString = Base64.getEncoder().encodeToString(auth.getBytes());
		
		// Pass the network header -> Authorization : Basic <encoded String>
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Authorization", "Basic "+encodeToString);				
		devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));
		
		// Load the url
		driver.get("https://the-internet.herokuapp.com/basic_auth");
		
		String text = driver.findElementByXPath("//p").getText();
		System.out.println(text);
		
	}

}
