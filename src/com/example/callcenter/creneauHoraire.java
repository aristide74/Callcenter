package com.example.callcenter;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.os.Looper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.*;
import android.view.View.*;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class creneauHoraire extends Activity implements View.OnClickListener {
	
	private Spinner heure = null;
	private Spinner minute = null;
	private Spinner annee = null;
	private Spinner jour = null;
	private Spinner mois = null;
	
	public static String encode(String input) {
		Matcher m = Pattern.compile("[^a-zA-Z0-9._-]").matcher(input);
		if (m.find()) {
			StringBuffer sb = new StringBuffer(input.length());
			do {
				int c = m.group().charAt(0);
				m.appendReplacement(sb, String.format("%%%02X", c));
			} while (m.find());
			m.appendTail(sb);
			return sb.toString();
		}
		return input;
	}
 
	public static String decode(String input) {
		Matcher m = Pattern.compile("%([0-9A-Fa-f]{2})").matcher(input);
		if (m.find()) {
			StringBuffer sb = new StringBuffer(input.length());
			do {
				char c = (char) Integer.parseInt(m.group(1), 16);
				m.appendReplacement(sb, Character.toString(c));
			} while (m.find());
			m.appendTail(sb);
			return sb.toString();
		}
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
		    TextView monTexte3 = (TextView)findViewById(R.id.editText3);
		    TextView monTexte4 = (TextView)findViewById(R.id.editText4);
		    TextView monTexte5 = (TextView)findViewById(R.id.editText5);
		    String login = (String) getIntent().getSerializableExtra("login");
		    String mdp = (String) getIntent().getSerializableExtra("mdp");

		    String answer = "";
		    
			try {
				answer = httpRequest("http://192.168.176.25/addRdv.php?id="+login+"&mdp="+sha1(mdp)+"&date="+annee.getSelectedItem().toString()+"-"+monTexte4.getText()+"-"+monTexte5.getText()+"%20"+heure.getSelectedItem().toString()+":"+minute.getSelectedItem().toString()+":00&sujet="+encode(monTexte3.getText().toString()));
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
	        b.setOnClickListener((OnClickListener) this);
	        
	       heure = (Spinner) findViewById(R.id.spinner1);
	        List<String> exemple = new ArrayList<String>();
	        exemple.add("00");
	        exemple.add("01");
	        exemple.add("02");
	        exemple.add("03");
	        exemple.add("04");
	        exemple.add("05");
	        exemple.add("06");
	        exemple.add("07");
	        exemple.add("08");
	        exemple.add("09");
	        exemple.add("10");
	        exemple.add("11");
	        exemple.add("12");
	        exemple.add("13");
	        exemple.add("14");
	        exemple.add("15");
	        exemple.add("16");
	        exemple.add("17");
	        exemple.add("18");
	        exemple.add("19");
	        exemple.add("20");
	        exemple.add("21");
	        exemple.add("22");
	        exemple.add("23");
	        
	        minute = (Spinner) findViewById(R.id.spinner2);
	        List<String> exemple2 = new ArrayList<String>();
	        exemple2.add("00");
	        exemple2.add("30");
	        
	        annee = (Spinner) findViewById(R.id.spinner3);
	        List<String> exemple3 = new ArrayList<String>();
	        exemple.add("2014");
	        exemple.add("2015");
	        exemple.add("2016");
	        exemple.add("2017");
	        exemple.add("2018");
	        exemple.add("2019");
	        exemple.add("2020");
	        exemple.add("2021");
	        exemple.add("2022");
	        exemple.add("2023");
	        exemple.add("2024");
	        exemple.add("2025");
	        exemple.add("2026");
	        exemple.add("2027");
	        exemple.add("2028");
	        exemple.add("2029");
	        exemple.add("2030");
	        exemple.add("2031");
	        exemple.add("2032");
	        exemple.add("2033");
	        exemple.add("2034");
	        exemple.add("2035");
	        exemple.add("2036");
	        exemple.add("2037");
	        
	        mois = (Spinner) findViewById(R.id.spinner4);
	        List<String> exemple4 = new ArrayList<String>();
	        exemple.add("01");
	        exemple.add("02");
	        exemple.add("02");
	        exemple.add("03");
	        exemple.add("04");
	        exemple.add("05");
	        exemple.add("06");
	        exemple.add("07");
	        exemple.add("08");
	        exemple.add("09");
	        exemple.add("10");
	        exemple.add("11");
	        exemple.add("12");
	        
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exemple);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        heure.setAdapter(adapter);
	        
	        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exemple2);
	        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        minute.setAdapter(adapter2);
	        
	        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exemple3);
	        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        annee.setAdapter(adapter3);
	        
	        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exemple4);
	        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        mois.setAdapter(adapter4);
	        
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