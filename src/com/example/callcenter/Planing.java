package com.example.callcenter;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.Activity;

public class Planing extends Activity 
{

	static String answer;
	String format = "yyyy-MM-dd"; 

	@SuppressLint("SimpleDateFormat")
	java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format ); 
	java.util.Date date = new java.util.Date(); 

	String datedujour = formater.format( date ); 
	
	protected void onCreate(Bundle savedInstanceState) {
		

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_planing);
		
		TextView monTexte1 = (TextView)findViewById(R.id.textView1);
		monTexte1.setText(datedujour);
		
		answer = (String) getIntent().getSerializableExtra("answer");
		System.out.println(answer);
		
		 GridView gridview = (GridView) findViewById(R.id.gridView1);
		 gridview.setAdapter(new MyAdapter(this));
		}
	
	public static String getAnswer()
	{
		return answer;
	}
}
