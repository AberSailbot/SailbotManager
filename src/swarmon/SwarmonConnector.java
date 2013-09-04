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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class SwarmonConnector {

	public String API_URL_BASE = "@haggis.ensta-bretagne.fr:80";
	
//	public String username, key;
	
	private HttpClient client;
	
	public SwarmonConnector(String username, String key){
		client = new DefaultHttpClient();
		API_URL_BASE = "http://" + username + ":" + key + API_URL_BASE;
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
	
	public void getTeams(){
		HttpPost post = new HttpPost(API_URL_BASE + "/teams");
		try{
			HttpResponse resp = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
			  String line = "";
			  while ((line = rd.readLine()) != null) {
			   System.out.println(line);
			  }

		}catch(IOException ex){
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
	}
}
