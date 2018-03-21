import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class API {

	
       public double GetCurrentStockPriceOfSymbol(String symbol) throws JSONException, IOException {
		
    	   BufferedReader br = null;
  		 try 
  		   {
  			URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol="+symbol+"&interval=1min&apikey=DD3IC9IWE25V1G6Q");

  	        URLConnection con = url.openConnection();
  	        InputStream is =con.getInputStream();
  		      br = new BufferedReader(new InputStreamReader(is));
  		      
  		   }
  		 catch (MalformedURLException e) {
  				//Auto-generated catch block
  				e.printStackTrace();
  	        } catch (IOException e) {
  				//Auto-generated catch block
  				e.printStackTrace();
  	        }
  			
  			StringBuffer strng_buffer = new StringBuffer();
  		      String line="";
  		      
  		      /* Concatenating JSON data to StringBuffer  */
  		      while((line=br.readLine())!=null)
  		      {
  		    	  strng_buffer.append(line);
  		      }
  		      JSONObject jsonData = new JSONObject(strng_buffer.toString());
	       
  		      JSONObject first_key = jsonData.getJSONObject("Time Series (1min)");
			Iterator x = first_key.sortedKeys();
			JSONArray jsonArray = new JSONArray();

			while (x.hasNext()){
			    String key1 =  (String) x.next();
			    jsonArray.put(first_key.get(key1));
			}
	       
	      JSONObject keys_Object = new JSONObject(jsonArray.get(jsonArray.length()-1).toString());
	    double symbol_value = Double.parseDouble((String) keys_Object.get("4. close"));
		
		return symbol_value;
		
	}
}
