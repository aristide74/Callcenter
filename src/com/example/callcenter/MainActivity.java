package com.example.callcenter;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.os.Looper;
import android.annotation.SuppressLint;
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
	    .setTitle("Erreur")
	    .setMessage("Connexion impossible")
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	dialog.cancel();
	        }
	     })
	     .show();
	}
	
	public void alertdialog3()
	{
		
		new AlertDialog.Builder(this)
	    .setTitle("Compte inactif")
	    .setMessage("Vous avez reçu un Mail")
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
	    .setTitle("Bienvenue!")
	    .setMessage("Séléctionnez votre créneau horaire")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int which) { 
	    		dialog.cancel();
	    		Intent intent = new Intent(MainActivity.this, creneauHoraire.class);
	    		intent.putExtra("login", login.toString());
	    		intent.putExtra("mdp", mdp);
	    		startActivity(intent);
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
			answer = httpRequest("http://192.168.176.25/authentification.php?mail="+monTexte1.getText()+"&mdp="+sha1(monTexte2.getText().toString()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(answer.equals(" 0"))
		{
			System.out.println("BAD");
			Looper.prepare();

	        alertdialog();

	        Looper.loop();
			
		}else if(answer.equals(" -1"))
		{
			System.out.println("NOT");
			Looper.prepare();

	        alertdialog3();

	        Looper.loop();
			
		}
		else
		{
			System.out.println("OK");
			String id = answer.substring(1);
			Looper.prepare();

	        alertdialog2(id,monTexte2.getText().toString());

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
        Button c = (Button) findViewById(R.id.button2);
        c.setOnClickListener(OnClick2);
        
    }
    
    View.OnClickListener OnClick2 = new View.OnClickListener() 
	{
	    public void onClick(View v) 
	    {
	    	 new Thread (new Runnable() 
	         {
				public void run() 
	 	  	    {
	 	  	    	Intent intent = new Intent(MainActivity.this, Motdepasse.class);
		    		startActivity(intent);
	 	  	    }
	 	  	}).start();
	    }
	};
    
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


