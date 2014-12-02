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
	Button regButton, recButton, sendButton, transButton;
	ListView listView;
	TextView langView, userView;
	int mode = 0;
	
	ArrayList<String> listItems = new ArrayList<String>();
	static ArrayAdapter<String> adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regButton   = (Button)   findViewById(R.id.regButton);
        recButton   = (Button)   findViewById(R.id.recButton);
        sendButton  = (Button)   findViewById(R.id.sendButton);
        transButton = (Button)   findViewById(R.id.translateButton);
        messageBox  = (EditText) findViewById(R.id.messageBox);
        listView    = (ListView) findViewById(R.id.listView);
        userView    = (TextView) findViewById(R.id.userView);
        langView    = (TextView) findViewById(R.id.langView);
     
        Log.d("Translate", "App started");
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        listView.setAdapter(adapter);
        langView.setText(UserMain.lang());
        
        regButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				userView.setText(messageBox.getText().toString());
				UserMain.set(userView.getText().toString(), langView.getText().toString());
				messageBox.setText("");
				Log.d("Translate", UserMain.name() + ": " + UserMain.lang());
				ChatApiClient client = new ChatApiClient();
				client.registerUser();
                Toast.makeText(MainActivity.this, "Registering User", Toast.LENGTH_SHORT).show();
			}
		});
        
        recButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View view) {
        		ChatApiClient client = new ChatApiClient();
        		client.getMessages();
        		Toast.makeText(MainActivity.this, "Message Received", Toast.LENGTH_SHORT).show();
        	}
        });
        
        sendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				User u = new User("a","en");
				ChatApiClient client = new ChatApiClient();
				client.sendMessage(u, messageBox.getText().toString());
                Toast.makeText(MainActivity.this, "Sending message to: " + u.getName(), Toast.LENGTH_SHORT).show();
			}
		});
        
        transButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View view) {
        		String text = messageBox.getText().toString();
        		TranslationService.translate(text, "en", "es");
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
