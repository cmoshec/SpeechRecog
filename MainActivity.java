


package com.example.moshe.speechrec;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {


    private static final int REQUEST_CODE = 1234;
    private static final int MAXRESULT = 3;
    private ListView lv;
    private TextView textView;
    /**
     * ********************************commiting changes in github: ****************************
     * goto VCS ->commit changes --> hover above commit button, and wait for commit and push option, then press it
     * 
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.list);
        textView = (TextView) findViewById(R.id.textView);
        Button b = (Button) findViewById(R.id.speakButton);
        b.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onClick(View v) {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak bitch");
        // i.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,MAXRESULT); //define the number of results listed in the listview
        startActivityForResult(i, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results));
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedFromList = lv.getItemAtPosition(position).toString(); //getting the text from the selected item
                    textView.setText(selectedFromList);

                    if (selectedFromList.contains("וויז")) {
                        //if (selectedFromList.hashCode()==245428529) { //hashcode for "dog"


                        Intent sendIntent = getPackageManager().getLaunchIntentForPackage("com.waze");
                        startActivity(sendIntent);
                    }
                    if (selectedFromList.contains("וואטסאפ")) {
                        //if (selectedFromList.hashCode()==245428529) { //hashcode for "dog"


                        Intent sendIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                        startActivity(sendIntent);
                    } else {
                        textView.setText("app not found");
                    }

                }
            });
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
