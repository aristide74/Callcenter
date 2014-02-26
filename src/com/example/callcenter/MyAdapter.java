package com.example.callcenter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

    private Context context;
    
    public int[] recup_answer() {
    	String answer = Planing.getAnswer();
    	int i;
    	String str [] = answer.split("/");
        for(i=0; i<str.length; i++)
        {
        	System.out.println(str[i]);
        }
        int[] array = {1,2,3};
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
    	int i;
    	for(i=0;i<array.length;i++)
    	{
    		System.out.println("tableau : "+array[i]);
    	}
    	String[] texts = {
    		"00h00 Ocup", 
    		"00h30 Libre", 
    		"01h00 Libre", 
    		"01h30 Libre", 
    		"02h00 Ocup", 
    		"02h30 Libre", 
    		"03h00 Libre", 
    		"03h30 Libre", 
    		"04h00 Ocup", 
    		"04h30 Ocup", 
    		"05h00 Libre", 
    		"05h30 Libre", 
    		"06h00 Libre", 
    		"06h30 Ocup", 
    		"07h00 Libre", 
    		"07h30 Libre", 
    		"08h00 Libre", 
    		"08h30 Ocup", 
    		"09h00 Ocup", 
    		"09h30 Ocup", 
    		"10h00 Libre", 
    		"10h30 Libre", 
    		"11h00 Libre", 
    		"11h30 Libre", 
    		"12h00 Libre", 
    		"12h30 Libre", 
    		"13h00 Libre", 
    		"13h30 Ocup", 
    		"14h00 Ocup", 
    		"14h30 Ocup", 
    		"15h00 Ocup", 
    		"15h30 Libre", 
    		"16h00 Libre", 
    		"16h30 Libre", 
    		"17h00 Libre", 
    		"17h30 Libre", 
    		"18h00 Ocup", 
    		"18h30 Ocup", 
    		"19h00 Libre",
    		"19h30 Libre", 
    		"20h00 Libre", 
    		"20h30 Libre", 
    		"21h00 Libre", 
    		"21h30 Ocup", 
    		"22h00 Ocup", 
    		"22h30 Ocup", 
    		"23h00 Ocup", 
    		"23h30 Ocup"};
    	
        TextView tv;
        if (convertView == null) {
            tv = new TextView(context);
           // tv.setLayoutParams(new GridView.LayoutParams(5, 5));
        }
        else {
            tv = (TextView) convertView;
        }

            tv.setText(texts[position]);
        return tv;
    }
}