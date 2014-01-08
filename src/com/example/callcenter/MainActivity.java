package com.example.callcenter;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;
import android.view.View.*;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnTouchListener, View.OnClickListener {
	
	static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }
	
	public String httpRequest (String adress)
	{
	  String answer = "", temp = "";
	  try
	  {
	    URL url = new URL(adress);
	    HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    InputStream in = con.getInputStream();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    while ((temp = reader.readLine()) != null)
	    {
	      answer = answer + temp;
	    }
	    reader.close();
	  }
	  catch (Exception e){}
	  return answer;
	}
	
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button) findViewById(R.id.button1);
        b.setOnTouchListener((OnTouchListener) this);
        b.setOnClickListener((OnClickListener) this);
    }
    
    public boolean onTouch(View v, MotionEvent event) {
    	     
        new Thread (new Runnable() 
        {
	  	    public void run() 
	  	    {
	  	    	TextView monTexte1 = (TextView)findViewById(R.id.editText1);
	  	        TextView monTexte2 = (TextView)findViewById(R.id.editText2);
	  	        String answer = "";
				try {
					answer = httpRequest("http://192.168.176.25/authentification.php?id="+monTexte1.getText()+"&mdp="+sha1(monTexte2.toString()));
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	  	    	System.out.println(answer);
	  	    }
	  	}).start();

    	
        return true;
      }
    
    public void onClick(View v) {
    	
        new Thread (new Runnable() 
        {
	  	    public void run() 
	  	    {
	  	    	TextView monTexte1 = (TextView)findViewById(R.id.editText1);
	  	        TextView monTexte2 = (TextView)findViewById(R.id.editText2);
	  	    	String answer = "";
				try {
					answer = httpRequest("http://192.168.176.25/authentification.php?id="+monTexte1.getText()+"&mdp="+sha1(monTexte2.toString()));
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	  	    	System.out.println(answer);
	  	    }
	  	}).start();
        
    }
}


