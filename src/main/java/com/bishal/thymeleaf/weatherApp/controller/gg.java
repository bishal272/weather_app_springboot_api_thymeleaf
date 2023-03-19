package com.bishal.thymeleaf.weatherApp.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class gg {

	public static void main(String[] args) throws Exception {
		String url="https://api.openweathermap.org/data/2.5/weather?q=london&units=metric&appid=2a755173bc4d15a8d13952732bc54c22";
        
        HttpRequest request=HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient client=HttpClient.newBuilder().build();
        
        HttpResponse<String> response=client.send(request,HttpResponse.BodyHandlers.ofString());
        JSONObject jobj=new JSONObject(response.body());
        JSONArray weatherArray= (JSONArray)jobj.get("weather");
        JSONObject mainObj = (JSONObject)jobj.get("main");
        JSONObject d=(JSONObject)weatherArray.get(0);
        Double temp=Double.parseDouble(mainObj.get("temp").toString());
        
        System.out.println(d);
	}

}
