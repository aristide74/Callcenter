package com.example.callcenter;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.os.Bundle;
import android.os.Looper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.*;
import android.view.View.*;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
	
	public void alertdialog()
	{
		
		new AlertDialog.Builder(this)
	    .setTitle("Ups...")
	    .setMessage("Bad Login or Password")
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	dialog.cancel();
	        }
	     })
	     .show();
	}
	
	public void alertdialog2(final CharSequence login, final String mdp)
	{
		
		new AlertDialog.Builder(this)
	    .setTitle("OK!")
	    .setMessage("Choix du creneau horaire!")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int which) { 
	    		dialog.cancel();
	    		Intent intent = new Intent(MainActivity.this, creneauHoraire.class);
	    		intent.putExtra("login", login.toString());
	    		intent.putExtra("mdp", mdp);
	    		startActivity(intent);
        	}
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	dialog.cancel();
	        }
	     })
	     .show();
	}
	
	public void resultat()
	{
		TextView monTexte1 = (TextView)findViewById(R.id.editText1);
	    TextView monTexte2 = (TextView)findViewById(R.id.editText2);
	    String answer = "";
	    
		try {
			answer = httpRequest("http://192.168.176.25/authentification.php?id="+monTexte1.getText()+"&mdp="+sha1(monTexte2.getText().toString()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(answer.equals(" 1"))
		{
			System.out.println("OK");
			Looper.prepare();

	        alertdialog2(monTexte1.getText(),monTexte2.getText().toString());

	        Looper.loop();
		}
		else
		{
			System.out.println("BAD");
			Looper.prepare();

	        alertdialog();

	        Looper.loop();
		}
	}
	
	private String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }
	
	private String httpRequest (String adress)
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
        b.setOnClickListener((OnClickListener) this);
        
    }
    
    
    public void onClick(View v) {
    	
        new Thread (new Runnable() 
        {
	  	    public void run() 
	  	    {
	  	    	resultat();
	  	    }
	  	}).start();
        
    }
}


