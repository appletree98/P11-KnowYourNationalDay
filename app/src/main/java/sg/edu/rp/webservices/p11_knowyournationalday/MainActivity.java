package sg.edu.rp.webservices.p11_knowyournationalday;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> alStudent;
    ArrayAdapter<String> aaStudent;

    RadioGroup rg,rg1,rg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listview);

        alStudent = new ArrayList<String>();
        aaStudent = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alStudent);
        lv.setAdapter(aaStudent);

        alStudent.add("Singapore National Day is on 9 Aug");
        alStudent.add("Singapore is 52 year old");
        alStudent.add("Theme is '#OneNation Together'");
        aaStudent.notifyDataSetChanged();

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout passPhrase =
                (LinearLayout) inflater.inflate(R.layout.passphrase, null);
        final EditText etPassphrase = (EditText) passPhrase
                .findViewById(R.id.editTextPassPhrase);

        final String code = etPassphrase.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("code", code);

        if (code.equalsIgnoreCase("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please Enter")
                    .setView(passPhrase)
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            if (etPassphrase.getText().toString() == "123456") {
                                Toast.makeText(MainActivity.this, "You had entered " +
                                        etPassphrase.getText().toString(), Toast.LENGTH_LONG).show();
                            }

                        }

                    })
                    .setNegativeButton("No access code", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            Toast.makeText(MainActivity.this, "No code ", Toast.LENGTH_LONG).show();
                        }

                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Tally against the respective action item clicked
        //  and implement the appropriate action

        if (item.getItemId() == R.id.quit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure?")
                    // Set text for the positive button and the corresponding
                    //  OnClickListener when it is clicked
                    .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    // Set text for the negative button and the corresponding
                    //  OnClickListener when it is clicked
                    .setNegativeButton("Not Really", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (item.getItemId() == R.id.send) {

            String[] list = new String[]{"Email", "SMS"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the way to enrich your friend")
                    // Set the list of items easily by just supplying an
                    //  array of the items
                    .setItems(list, new DialogInterface.OnClickListener() {
                        // The parameter "which" is the item index
                        // clicked, starting from 0
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.putExtra(Intent.EXTRA_EMAIL,
                                        new String[]{"abc@myrp.edu.sg"});
                                email.putExtra(Intent.EXTRA_SUBJECT,
                                        "-");
                                email.putExtra(Intent.EXTRA_TEXT,
                                        "Hello, \n" + message);
                                email.setType("message/rfc822");
                                startActivity(Intent.createChooser(email,
                                        "Choose an Email client :"));
                                Toast.makeText(MainActivity.this, "Email has been sent",
                                        Toast.LENGTH_LONG).show();

                                startActivity(Intent.createChooser(email, "Send email using..."));

                            } else {
                                Toast.makeText(MainActivity.this, "You chose SMS",
                                        Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + 91234567));
                                intent.putExtra("sms_body", "Hello");
                                startActivity(intent);
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else if (item.getItemId() == R.id.quiz) {
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout quiz =
                    (LinearLayout) inflater.inflate(R.layout.quiz, null);

            rg = (RadioGroup)quiz.findViewById(R.id.rg);
            rg1 = (RadioGroup)quiz.findViewById(R.id.rg1);
            rg2 = (RadioGroup)quiz.findViewById(R.id.rg2);



            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Test Yourself!")
                    .setView(quiz)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            int selected = rg.getCheckedRadioButtonId();
                            int selected1 = rg1.getCheckedRadioButtonId();
                            int selected2 = rg2.getCheckedRadioButtonId();

                            int score = 0;

                            if(selected == -1 | selected1 == -1 | selected2 == -2){
                                Toast.makeText(MainActivity.this, "Please answer all the Question", Toast.LENGTH_SHORT).show();
                            } else {
                                if(selected == R.id.rbNo){
                                    score = score + 1;
                                }
                                if(selected1 == R.id.rbYes1){
                                    score = score + 1;
                                }
                                if(selected2 == R.id.rbYes2){
                                    score = score + 1;
                                }
                                Toast.makeText(MainActivity.this, "Your Total Score is " + score, Toast.LENGTH_SHORT).show();

                            }
                        }
                    })
                    .setNegativeButton("Don't know lah", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (item.getItemId() == R.id.quit) {
            finishAffinity();
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }
    protected void onResume(){
        super.onResume();


    }
}
