package com.example.callcenterpropre;

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
    		"00h00 --------------------------", 
    		"00h30 --------------------------", 
    		"01h00 --------------------------", 
    		"01h30 --------------------------", 
    		"02h00 --------------------------", 
    		"02h30 --------------------------", 
    		"03h00 --------------------------", 
    		"03h30 --------------------------", 
    		"04h00 --------------------------", 
    		"04h30 --------------------------", 
    		"05h00 --------------------------", 
    		"05h30 --------------------------", 
    		"06h00 --------------------------", 
    		"06h30 --------------------------", 
    		"07h00 --------------------------", 
    		"07h30 --------------------------", 
    		"08h00 --------------------------", 
    		"08h30 --------------------------", 
    		"09h00 --------------------------", 
    		"09h30 --------------------------", 
    		"10h00 --------------------------", 
    		"10h30 --------------------------", 
    		"11h00 --------------------------", 
    		"11h30 --------------------------", 
    		"12h00 --------------------------", 
    		"12h30 --------------------------", 
    		"13h00 --------------------------", 
    		"13h30 --------------------------", 
    		"14h00 --------------------------", 
    		"14h30 --------------------------", 
    		"15h00 --------------------------", 
    		"15h30 --------------------------", 
    		"16h00 --------------------------", 
    		"16h30 --------------------------", 
    		"17h00 --------------------------", 
    		"17h30 --------------------------", 
    		"18h00 --------------------------", 
    		"18h30 --------------------------", 
    		"19h00 --------------------------",
    		"19h30 --------------------------", 
    		"20h00 --------------------------", 
    		"20h30 --------------------------", 
    		"21h00 --------------------------", 
    		"21h30 --------------------------", 
    		"22h00 --------------------------", 
    		"22h30 --------------------------", 
    		"23h00 --------------------------", 
    		"23h30 --------------------------"};
    	
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
	    			texts[i] = texts[i].substring(0,6)+"XXXXXXXXXXXXXXXXXXXXXX";
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