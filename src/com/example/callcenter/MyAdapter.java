package com.example.callcenter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

    private Context context;
    
	public int[] recup_answer() {
    	String answer = Planing.getAnswer();
    	int [] array = null;
    	if(!answer.equals(" "))
    	{
	    	int i,heure,minute,x;
	    	answer = answer.substring(1);
	    	String str [] = answer.split("/");
	    	array = new int[str.length];
	        for(i=0; i<str.length; i++)
	        {
	        	str[i]=str[i].substring(11,16);
	        	heure = Integer.parseInt(str[i].substring(0,2));
	        	minute = Integer.parseInt(str[i].substring(3,5));
	        	x=heure*2+minute/30;
	        	array[i] = x;
	        }
    	}
      
        	
       return array;
    }

    public MyAdapter(Context context) {
        this.context = context;
    }

    public int getCount() {
        return 48;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	int[] array = recup_answer();
    	int i,y,bool;
    	
    	String[] texts = {
    		"00h00 Libre", 
    		"00h30 Libre", 
    		"01h00 Libre", 
    		"01h30 Libre", 
    		"02h00 Libre", 
    		"02h30 Libre", 
    		"03h00 Libre", 
    		"03h30 Libre", 
    		"04h00 Libre", 
    		"04h30 Libre", 
    		"05h00 Libre", 
    		"05h30 Libre", 
    		"06h00 Libre", 
    		"06h30 Libre", 
    		"07h00 Libre", 
    		"07h30 Libre", 
    		"08h00 Libre", 
    		"08h30 Libre", 
    		"09h00 Libre", 
    		"09h30 Libre", 
    		"10h00 Libre", 
    		"10h30 Libre", 
    		"11h00 Libre", 
    		"11h30 Libre", 
    		"12h00 Libre", 
    		"12h30 Libre", 
    		"13h00 Libre", 
    		"13h30 Libre", 
    		"14h00 Libre", 
    		"14h30 Libre", 
    		"15h00 Libre", 
    		"15h30 Libre", 
    		"16h00 Libre", 
    		"16h30 Libre", 
    		"17h00 Libre", 
    		"17h30 Libre", 
    		"18h00 Libre", 
    		"18h30 Libre", 
    		"19h00 Libre",
    		"19h30 Libre", 
    		"20h00 Libre", 
    		"20h30 Libre", 
    		"21h00 Libre", 
    		"21h30 Libre", 
    		"22h00 Libre", 
    		"22h30 Libre", 
    		"23h00 Libre", 
    		"23h30 Libre"};
    	
    	if(array != null)
    	{
	    	for(i=0;i<48;i++)
	    	{
	    		bool = 0;
	    		for(y=0;y<array.length;y++)
	    		{
	    			if(array[y]==i)
	    			{
	    				bool = 1;
	    			}
	    		}
	    		
	    		if(bool == 1)
	    		{
	    			texts[i] = texts[i].substring(0,6)+"Ocup";
	    		}
	    	}
    	}
    	
        TextView tv;
        if (convertView == null) {
            tv = new TextView(context);
            
        }
        else {
            tv = (TextView) convertView;
        }

            tv.setText(texts[position]);
        return tv;
    }
}