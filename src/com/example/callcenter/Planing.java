package com.example.callcenter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

public class Planing extends Activity implements View.OnClickListener
{
	
	static String answer;
	

	@SuppressLint("SimpleDateFormat")
	void resultat()
	{
		Calendar date = (Calendar) getIntent().getSerializableExtra("date");
		date.add(Calendar.DATE, 1);

 	  	String answer = requeteCreneau(date);
 	  	    	
 	  	Intent intent = new Intent(Planing.this, Planing.class);
	    intent.putExtra("answer", answer);
	    intent.putExtra("date", date);
	    startActivity(intent);

		
	}
	
	@SuppressLint("SimpleDateFormat")
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_planing);
		
		Calendar date = (Calendar) getIntent().getSerializableExtra("date");
		Date date2 = date.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = format1.format(date2);
		TextView monTexte1 = (TextView)findViewById(R.id.textView1);
		monTexte1.setText(datestr);
		
		answer = (String) getIntent().getSerializableExtra("answer");
		
		GridView gridview = (GridView) findViewById(R.id.gridView1);
		gridview.setAdapter(new MyAdapter(this));
		Button b = (Button) findViewById(R.id.button1);
	    b.setOnClickListener((OnClickListener) this);
	}
	
	@SuppressLint("SimpleDateFormat")
	public String requeteCreneau(Calendar date)
	{
		
		Date date2 = date.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = format1.format(date2); 
		
		String answer = "";
		answer = httpRequest("http://192.168.176.25/checkdate.php?date="+datestr);
		
		return answer;

	}
	
	public static String getAnswer()
	{
		return answer;
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
}
