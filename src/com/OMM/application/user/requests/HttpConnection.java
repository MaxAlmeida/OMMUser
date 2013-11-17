package com.OMM.application.user.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public abstract class HttpConnection {

	public final static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

		public String handleResponse(HttpResponse response) throws IOException {

			HttpEntity entity = response.getEntity();
			String result = null;

			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					entity.getContent()));
			
			StringBuilder builder = new StringBuilder();
			String line = null;

			while ((line = buffer.readLine()) != null) {
				
				builder.append(line + "\n");
			}

			buffer.close();
			result = builder.toString();

			return result;
		}
	};

	public static ResponseHandler<String> getResponseHandler() {
		return responseHandler;
	}

	public static String requestParlamentar(ResponseHandler<String> response,
			String url) {

		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet http = new HttpGet(url);

			String jsonParlamentar = client.execute(http, response);

			return jsonParlamentar;

		} catch (ClientProtocolException e) {
			// TODO: Exception Handling

		} catch (IOException e) {
			// TODO: Exception Handling
		}

		return null;
	}

	public static String requestCota(ResponseHandler<String> response,
			String url) {

		try {
			
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet http = new HttpGet(url);

			String jsonCotaParlamentar = client.execute(http, response);

			return jsonCotaParlamentar;

		} catch (ClientProtocolException e) {
			// TODO: Exception Handling

		} catch (IOException e) {
			// TODO: Exception Handling
		}

		return null;
	}
}
