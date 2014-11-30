package com.example.chatpractice;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	EditText messageBox;
	Button sendButton, receiveButton;
	ListView listView;
	TextView textView;
	
	ArrayList<String> listItems = new ArrayList<String>();
	static ArrayAdapter<String> adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton    = (Button)   findViewById(R.id.sendButton);
        receiveButton = (Button)   findViewById(R.id.receiveButton);
        messageBox    = (EditText) findViewById(R.id.messageBox);
        listView      = (ListView) findViewById(R.id.listView);
        textView      = (TextView) findViewById(R.id.textView);
     
        Log.d("Translate", "App started");
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        listView.setAdapter(adapter);
        
        sendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				textView.setText("Clicked");
				addItems("Clicked send");
                Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
			}
		});
        
        receiveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String text = messageBox.getText().toString();
				new TranslationService("en", "es").execute(text);
                Toast.makeText(MainActivity.this, "Message translated", Toast.LENGTH_SHORT).show();
			}
		});
    }
    
    public static void addItems(String s) {
    	adapter.add(s);
    } 

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
