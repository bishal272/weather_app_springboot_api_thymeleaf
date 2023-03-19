package com.bishal.thymeleaf.weatherApp.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class WeatherController {
	@Value("${api.key}")
	private String key;
	
	@GetMapping("/findweather")
	public String findWeather(@ModelAttribute("city")String loc, Model theModel) {
		theModel.addAttribute("city",loc);
		return "weather/findweather";
	}
	
	@GetMapping("/getweather")
	public String getweather(@ModelAttribute ("city")String city,Model theModel)throws Exception{
		
		String url=String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s", city,key);
        
        HttpRequest request=HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient client=HttpClient.newBuilder().build();
        
        HttpResponse<String> response=client.send(request,HttpResponse.BodyHandlers.ofString());
        JSONObject jobj=new JSONObject(response.body());
        
        //getting data according to the structure of the JSON
        //getting the weather description
        JSONArray weatherArray= (JSONArray)jobj.get("weather");
        JSONObject insideObj=(JSONObject)weatherArray.get(0);
        Object weatherDesc = insideObj.get("description");
        
        //getting the temperature
        JSONObject mainObj = (JSONObject)jobj.get("main");
        Double temp=Double.parseDouble(mainObj.get("temp").toString());
     
       
		theModel.addAttribute("city",city);
		theModel.addAttribute("weatherDesc", weatherDesc);
		theModel.addAttribute("temp",temp);
		
		return "weather/getWeather";
	}
}
