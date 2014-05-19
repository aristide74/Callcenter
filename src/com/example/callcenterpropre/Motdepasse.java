package com.example.callcenterpropre;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Motdepasse extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_motdepasse);
		Button b = (Button) findViewById(R.id.button1);
        b.setOnClickListener((OnClickListener) OnClick);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.mdpoublie, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	//Selectionne une action en fonction de l'objet selectionner dans le menu
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.undo:
	            onDestroy();
	            return true;
	       
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	protected void onDestroy() {
	    super.onDestroy();
	    finish();
	}
	 
	 public void resultat()
		{
		    TextView monTexte1 = (TextView)findViewById(R.id.editText1);

		    String answer = "";
		    
		    
				answer = httpRequest("http://192.168.176.25/mail/lightLostPass.php?lst=1&mail="+monTexte1.getText().toString());
				
				if(answer.equals(" 1"))
				{
					System.out.println("OK");
					Looper.prepare();
	
					alertdialog2();
	
			        Looper.loop();
				}
				else
				{
					System.out.println("BAD");
					Looper.prepare();
	
					alertdialog1();
	
			        Looper.loop();
				}
		}

	 
/**********************************************************************************************/
//	Ouvre une boite de dialogue d'erreur 	 
/**********************************************************************************************/
	 public void alertdialog1()
		{
			
			new AlertDialog.Builder(this)
		    .setTitle("Erreur")
		    .setMessage("Votre adresse mail n'est pas reconnu...")
		    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	dialog.cancel();
		        }
		     })
		     .show();
		}
	 
	 
	 
	 
/**********************************************************************************************/
//		Ouvre une boite de dialogue d'erreur 	 
/**********************************************************************************************/
	 
	 public void alertdialog2()
		{
			
			new AlertDialog.Builder(this)
		    .setTitle("Done!")
		    .setMessage("Un mail de recupération vous à été envoyé!")
		    .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	dialog.cancel();
		        }
		     })
		     .show();
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
	 
	 View.OnClickListener OnClick = new View.OnClickListener() 
		{
		 public void onClick(View v) {
		    	
		        new Thread (new Runnable() 
		        {
			  	    public void run() 
			  	    {
			  	    	resultat();
			  	    }
			  	}).start();
		        
		    }
		};

}
