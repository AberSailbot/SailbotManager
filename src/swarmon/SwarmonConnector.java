package swarmon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

public class SwarmonConnector {

	public String API_URL_BASE = "@haggis.ensta-bretagne.fr:80";
	
	public SwarmonConnector(String username, String key){
		API_URL_BASE = "http://" + username + ":" + key + API_URL_BASE;
	}
	
	private String doRequest(HttpUriRequest request){
		HttpClient client = new DefaultHttpClient();
		try{
			BufferedReader rd = new BufferedReader(new InputStreamReader(client.execute(request).getEntity().getContent()));
			String line = "";
			StringBuilder ret = new StringBuilder();
		    while ((line = rd.readLine()) != null) {
		    	System.out.println(line);
		    	ret.append(line);
		    }
		    return ret.toString();
		}catch(Exception ex){
			ex.printStackTrace();
			return "";
		}
	}
	
	public void createRobot(String teamName, String robotName) throws UnsupportedEncodingException{
		HttpPost post = new HttpPost(API_URL_BASE + "/robot");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("teamName", teamName));
		params.add(new BasicNameValuePair("name", robotName));
		
		post.setEntity(new UrlEncodedFormEntity(params));
		
	} // AberST:api26test1@haggis.ensta-bretagne.fr:80
	
	public void createTeam(String teamName) throws UnsupportedEncodingException{
		HttpPost post = new HttpPost(API_URL_BASE + "/robot");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", teamName));
		post.setEntity(new UrlEncodedFormEntity(params));
		
	}
	
	public void createTeam(String teamName, String website) throws UnsupportedEncodingException{
		HttpPost post = new HttpPost(API_URL_BASE + "/robot");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("name", teamName));
		params.add(new BasicNameValuePair("website", website));
		post.setEntity(new UrlEncodedFormEntity(params));
		
	}
	

	public void registerRobot(String teamName, String robotName) throws IOException{
		HttpPost post = new HttpPost(API_URL_BASE + "/robot");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("teamName", teamName));
		params.add(new BasicNameValuePair("name", robotName));
		post.setEntity(new UrlEncodedFormEntity(params));
		doRequest(post);
		
	}
	
	public void getRealTimeData() throws IOException{
		HttpGet get = new HttpGet(API_URL_BASE + "/rt/0");
		get.addHeader("Accept", "application/json");
		doRequest(get);
		
	}
	
	public JSONArray getRobots() throws IOException{
		HttpGet get = new HttpGet(API_URL_BASE + "/robots");
		get.addHeader("Accept", "application/json");
	    return new JSONArray(doRequest(get));
	}
	
}
