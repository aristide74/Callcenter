package com.example.callcenterpropre;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

public class MyCalendarActivity extends Activity {
CalendarView calendar;
Calendar date=new GregorianCalendar();


@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.my_calendar_view);

	
	//On créer la vue du calendrier 
	calendar = (CalendarView)findViewById(R.id.calendar);
	
	//Si Un date autre que celle du jour est selectionner, on modifie la variable "date"
	calendar.setOnDateChangeListener(new OnDateChangeListener() {
			
			@Override
			public void onSelectedDayChange(CalendarView view, final int year, final int month,
					final int dayOfMonth) {
				// TODO Auto-generated method stub
				
				date.set(year, month, dayOfMonth);
				
				Toast.makeText(getBaseContext(),dayOfMonth+" : "+month+" : "+year , 
					Toast.LENGTH_LONG).show();
			}
		});
	
	
	
	
}

//On declarer le menu que l'on va utiliser 
public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu items for use in the action bar
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main, menu);
    return super.onCreateOptionsMenu(menu);
}

//Selectionne une action en fonction de l'objet selectionner dans le menu
public boolean onOptionsItemSelected(MenuItem item) {
    // Handle presses on the action bar items
    switch (item.getItemId()) {
        case R.id.suite:
            ajout();
            return true;
        case R.id.undo:
            onDestroy();
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }
}


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



public void ajout(){
	new Thread (new Runnable() 
    {
  	    @SuppressLint("SimpleDateFormat")
		public void run() 
  	    {
  	    	String answer = requeteCreneau(date);
  	    	
  	    	Intent intent = new Intent(MyCalendarActivity.this, Planing.class);
  	    	String login2 = (String) getIntent().getSerializableExtra("login");
			String mdp2 = (String) getIntent().getSerializableExtra("mdp");
	  	    intent.putExtra("login", login2.toString());
    		intent.putExtra("mdp", mdp2);
    		intent.putExtra("answer", answer);
    		intent.putExtra("date", date);
    		startActivity(intent);
  	    }
  	}).start();
}

protected void onDestroy() {
    super.onDestroy();
    finish();
}
}
