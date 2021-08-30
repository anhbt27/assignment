package com.tuananhbui.assignment.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import net.lingala.zip4j.ZipFile;

public class Service {
	
	private String apiUrl = "https://54.70.230.245:80/";
	
	// read the data from csv file and send the request
	public void readDataAndSendRequest() {
		try {
			new ZipFile("file.zip", "pass123".toCharArray()).extractAll("../assignment");
			
			BufferedReader reader = new BufferedReader(new FileReader("data.csv"));
			reader.readLine();
			String line = "";
			
			while ((line = reader.readLine()) != null) {
				line = line.replace("\"", "").replace("'", "\"");
				String[] splits = line.split(";");
				String email = splits[0] + "." + splits[1] + "@domain.com";
				if(email.equals(splits[2])) {
					String body = createJsonString(splits);
					sendPostRequest(body);
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	// create json string for each accepted row of data
    public String createJsonString(String[] data) {
    	StringBuilder result = new StringBuilder("");
    	result.append("{\"FirstName\":\"" + data[0] + "\",")
				.append("\"LastName\":\"" + data[1] + "\",")
				.append("\"Email\":\"" + data[2] + "\",")
				.append("\"param1\":" + data[4] + ",")
				.append("\"param2\":" + data[5] + ",")
				.append("\"param3\":" + data[6] + ",")
				.append("\"param4\":" + data[7] + ",")
				.append("\"param5\":" + data[8] + ",")
				.append("\"param6\":" + data[9] + ",")
				.append("\"param7\":" + data[10] + ",")
				.append("\"param8\":" + data[11] + ",")
				.append("\"param0\":" + data[3] + "}");
    	return result.toString();
    }
    
    
    // send post request with body is a json string to the proposed api
	public void sendPostRequest(String data) {
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Content-Type", "application/json");

			byte[] outBody = data.getBytes(StandardCharsets.UTF_8);

			OutputStream outputStream = http.getOutputStream();
			outputStream.write(outBody);

			System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
			http.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
