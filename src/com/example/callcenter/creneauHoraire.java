package com.example.callcenter;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.os.Looper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.*;
import android.view.View.*;
import android.widget.Button;
import android.widget.TextView;

public class creneauHoraire extends Activity implements View.OnTouchListener, View.OnClickListener {
		
	public static String encode(String input) {
		// On recherche tous les caractères sauf lettres, chiffres, point et tirets : 
		Matcher m = Pattern.compile("[^a-zA-Z0-9._-]").matcher(input);
		if (m.find()) {
			// Si on en trouve au moins un, on utilise un StringBuffer
			// pour générer la nouvelle chaine modifié :
			StringBuffer sb = new StringBuffer(input.length());
			do {
				// On récupère la valeur entière du caractère :
				int c = m.group().charAt(0);
				// Puis on effectue le remplacement :
				m.appendReplacement(sb, String.format("%%%02X", c));
			} while (m.find());
			// Enfin on complète la chaine :
			m.appendTail(sb);
			// Et on renvoi la nouvelle chaine :
			return sb.toString();
		}
		// Sinon il n'y a aucune modif à faire :
		return input;
	}
 
	public static String decode(String input) {
		// On recherche tous les % suivi d'une valeur hexa :
		Matcher m = Pattern.compile("%([0-9A-Fa-f]{2})").matcher(input);
		if (m.find()) {
			// Si on en trouve au moins un, on utilise un StringBuffer
			// pour générer la nouvelle chaine modifié :
			StringBuffer sb = new StringBuffer(input.length());
			do {
				// On récupère la valeur entière du caractère :
				char c = (char) Integer.parseInt(m.group(1), 16);
				// Puis on effectue le remplacement :
				m.appendReplacement(sb, Character.toString(c));
			} while (m.find());
			// Enfin on complète la chaine :
			m.appendTail(sb);
			// Et on renvoi la nouvelle chaine :
			return sb.toString();
		}
		// Sinon il n'y a aucune modif à faire :
		return input;
	}
	
		public void alertdialog()
		{
			
			new AlertDialog.Builder(this)
		    .setTitle("Ups...")
		    .setMessage("Bad Login or Password or date formating")
		    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	dialog.cancel();
		        }
		     })
		     .show();
		}
		
		public void alertdialog2()
		{
			
			new AlertDialog.Builder(this)
		    .setTitle("OK")
		    .setMessage("Vous avez un RDV! :-)")
		    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
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
		
		private String sha1(String input) throws NoSuchAlgorithmException {
	        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
	        byte[] result = mDigest.digest(input.getBytes());
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < result.length; i++) {
	            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
	        }
	         
	        return sb.toString();
	    }
		
		public void resultat()
		{
			TextView monTexte1 = (TextView)findViewById(R.id.editText1);
		    TextView monTexte2 = (TextView)findViewById(R.id.editText2);
		    TextView monTexte3 = (TextView)findViewById(R.id.editText3);
		    String login = (String) getIntent().getSerializableExtra("login");
		    String mdp = (String) getIntent().getSerializableExtra("mdp");

		    String answer = "";
		    
			try {
				answer = httpRequest("http://192.168.176.25/addRdv.php?id="+login+"&mdp="+sha1(mdp)+"&date="+monTexte1.getText()+"%20"+monTexte2.getText()+"&sujet="+encode(monTexte3.getText().toString()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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

				alertdialog();

		        Looper.loop();
			}
		}
	
		protected void onCreate(Bundle savedInstanceState) {
	    	
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.creneau);
	        Button b = (Button) findViewById(R.id.button1);
	        b.setOnTouchListener((OnTouchListener) this);
	        b.setOnClickListener((OnClickListener) this);
	        System.out.println(encode("eéeèe bordel!"));
	        
	    }

		@Override
		 public boolean onTouch(View v, MotionEvent event) {
   	     
	        new Thread (new Runnable() 
	        {
		  	    public void run() 
		  	    {
		  	    	resultat();
		  	    }
		  	}).start();

	    	
	        return true;
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