import java.io.*;
import java.net.*;

public class WebReader{
    private URL url;
    public WebReader(String url){
	try{
	    this.url = new URL(url);
	}catch (MalformedURLException e){
	    //Stacktrace
	}
    }
    public String getLine(){
	String input = null;
	try{
	    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	    input = in.readLine();
	}catch (IOException e){
	    //Stacktrace
	}
	return input;
    }
}
