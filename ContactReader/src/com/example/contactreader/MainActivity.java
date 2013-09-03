package com.example.contactreader;

import java.io.BufferedInputStream;
import java.io.InputStream;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	String check;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final TextView text = (TextView) findViewById(R.id.textView1);  
		Button btn = (Button) findViewById(R.id.button1); 
		btn.setOnClickListener(new OnClickListener() {  
			@Override  
			public void onClick(View arg0) {  
				ContentResolver cr = getContentResolver();  
				Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);  
				if (cur.getCount() > 0) {  
					while (cur.moveToNext()) {  
						String id = cur.getString(  
								cur.getColumnIndex(ContactsContract.Contacts._ID));  
						String name = cur.getString(  
								cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));  
						if (Integer.parseInt(cur.getString(  
								cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {  
							Cursor pCur = cr.query(  
									ContactsContract.CommonDataKinds.Phone.CONTENT_URI,   
									null,   
									ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",   
											new String[]{id}, null);  
							while (pCur.moveToNext()) {  
								System.out.println("Name : "+name);
								check = "Shalika Nadeeshani";  //Name of the particular contact  
								if(check.equals(name)){  
									Log.v("Name :",name); //Display the name of the contact in logcat  
									text.setText(name); //Display the name in Textview  
									/*Following code section is for getting the contact image*/  
									ImageView profile = (ImageView)findViewById(R.id.imageView1);           
									Uri my_contact_Uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(id));  
									InputStream photo_stream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),my_contact_Uri);        
									BufferedInputStream buf = new BufferedInputStream(photo_stream);  
									Bitmap my_btmp = BitmapFactory.decodeStream(buf);  
									profile.setImageBitmap(my_btmp);  
								}  
							}   
							pCur.close();  
						}  
					}  
				}  
			}  
		});  
	}  
}
