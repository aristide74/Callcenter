package com.example.callcenterpropre;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

public class Planing extends Activity
{
	
	static String answer;
	
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
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.affiche, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	//Selectionne une action en fonction de l'objet selectionner dans le menu
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.next:
	        	ajout();
	            onDestroy();
	            return true;
	        case R.id.undo:
	            onDestroy();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void ajout(){
		new Thread (new Runnable() 
	    {
	  	    @SuppressLint("SimpleDateFormat")
			public void run() 
	  	    {
	  	    	Intent intent = new Intent(Planing.this, creneauHoraire.class);
	  	    	String login2 = (String) getIntent().getSerializableExtra("login");
 			    String mdp2 = (String) getIntent().getSerializableExtra("mdp");
 	  	    	intent.putExtra("login", login2.toString());
	    		intent.putExtra("mdp", mdp2);
	    		startActivity(intent);
	  	    }
	  	}).start();
	}
	
	public static String getAnswer()
	{
		return answer;
	}
	
	protected void onDestroy() {
	    super.onDestroy();
	    finish();
	}
	

}
