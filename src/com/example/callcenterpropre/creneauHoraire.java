package com.example.callcenterpropre;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.os.Looper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
	
	@SuppressLint("SimpleDateFormat")
	public static int testDate(String date) 
	{ 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");  
		Date d = new Date(); 
		try 
		{ 
			d = sdf.parse(date); 
			String t = sdf.format(d); 
			if(t.compareTo(date) != 0) 
			return 0;
			else 
			return 1; 
		} 
		catch (Exception e) 
		{ 
			return 0;
		} 
	} 
	
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
		    .setTitle("Erreur")
		    .setMessage("Créneau horaire déjà occupé!")
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
		    .setTitle("Félicitation")
		    .setMessage("Le RDV est enregistré")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	dialog.cancel();
		        }
		     })
		     .show();
		}
		
		public void alertdialog3()
		{
			
			new AlertDialog.Builder(this)
		    .setTitle("Erreur")
		    .setMessage("Mauvais formatage de Date!")
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
		    String login = (String) getIntent().getSerializableExtra("login");
		    String mdp = (String) getIntent().getSerializableExtra("mdp");

		    String answer = "";
		    
		    if(testDate(annee.getSelectedItem().toString()+"/"+mois.getSelectedItem().toString()+"/"+jour.getSelectedItem().toString())==1)
		    {
		    
				try {
					answer = httpRequest("http://192.168.176.25/addRdv.php?id="+login+"&mdp="+sha1(mdp)+"&date="+annee.getSelectedItem().toString()+"-"+mois.getSelectedItem().toString()+"-"+jour.getSelectedItem().toString()+"%20"+heure.getSelectedItem().toString()+":"+minute.getSelectedItem().toString()+":00&sujet="+encode(monTexte3.getText().toString()));
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
		    else
		    {
		    	System.out.println("BAD");
				Looper.prepare();

				alertdialog3();

		        Looper.loop();
		    }
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
	
		protected void onCreate(Bundle savedInstanceState) {
	    	
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.creneau);
	        Button b = (Button) findViewById(R.id.button1);
	        b.setOnClickListener((OnClickListener) this);
	        Button c = (Button) findViewById(R.id.button2);
	        c.setOnClickListener(OnClick2);
	        
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
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exemple);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        heure.setAdapter(adapter);
	        
	        minute = (Spinner) findViewById(R.id.spinner2);
	        List<String> exemple2 = new ArrayList<String>();
	        exemple2.add("00");
	        exemple2.add("30");
	        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exemple2);
	        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        minute.setAdapter(adapter2);
	        
	        annee = (Spinner) findViewById(R.id.spinner3);
	        List<String> exemple3 = new ArrayList<String>();
	        exemple3.add("2014");
	        exemple3.add("2015");
	        exemple3.add("2016");
	        exemple3.add("2017");
	        exemple3.add("2018");
	        exemple3.add("2019");
	        exemple3.add("2020");
	        exemple3.add("2021");
	        exemple3.add("2022");
	        exemple3.add("2023");
	        exemple3.add("2024");
	        exemple3.add("2025");
	        exemple3.add("2026");
	        exemple3.add("2027");
	        exemple3.add("2028");
	        exemple3.add("2029");
	        exemple3.add("2030");
	        exemple3.add("2031");
	        exemple3.add("2032");
	        exemple3.add("2033");
	        exemple3.add("2034");
	        exemple3.add("2035");
	        exemple3.add("2036");
	        exemple3.add("2037");
	        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exemple3);
	        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        annee.setAdapter(adapter3);
	        
	        mois = (Spinner) findViewById(R.id.spinner4);
	        List<String> exemple4 = new ArrayList<String>();
	        exemple4.add("01");
	        exemple4.add("02");
	        exemple4.add("02");
	        exemple4.add("03");
	        exemple4.add("04");
	        exemple4.add("05");
	        exemple4.add("06");
	        exemple4.add("07");
	        exemple4.add("08");
	        exemple4.add("09");
	        exemple4.add("10");
	        exemple4.add("11");
	        exemple4.add("12");
	        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exemple4);
	        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        mois.setAdapter(adapter4);
	        
	        jour = (Spinner) findViewById(R.id.spinner5);
	        List<String> exemple5 = new ArrayList<String>();
	        exemple5.add("01");
	        exemple5.add("02");
	        exemple5.add("03");
	        exemple5.add("04");
	        exemple5.add("05");
	        exemple5.add("06");
	        exemple5.add("07");
	        exemple5.add("08");
	        exemple5.add("09");
	        exemple5.add("10");
	        exemple5.add("11");
	        exemple5.add("12");
	        exemple5.add("13");
	        exemple5.add("14");
	        exemple5.add("15");
	        exemple5.add("16");
	        exemple5.add("17");
	        exemple5.add("18");
	        exemple5.add("19");
	        exemple5.add("20");
	        exemple5.add("21");
	        exemple5.add("22");
	        exemple5.add("23");
	        exemple5.add("24");
	        exemple5.add("25");
	        exemple5.add("26");
	        exemple5.add("27");
	        exemple5.add("28");
	        exemple5.add("29");
	        exemple5.add("30");
	        exemple5.add("31");
	        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exemple5);
	        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        jour.setAdapter(adapter5);

	    }

		View.OnClickListener OnClick2 = new View.OnClickListener() 
		{
		    public void onClick(View v) 
		    {
		    	 new Thread (new Runnable() 
		         {
					public void run() 
					{	
						
		 	  	    	Intent intent = new Intent(creneauHoraire.this, MyCalendarActivity.class);
		 	  	    	String login2 = (String) getIntent().getSerializableExtra("login");
		 			    String mdp2 = (String) getIntent().getSerializableExtra("mdp");
		 	  	    	intent.putExtra("login", login2.toString());
			    		intent.putExtra("mdp", mdp2);
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