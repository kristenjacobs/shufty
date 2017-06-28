package tech.krisfj.shufty;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file browser.
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                //intent.setType("*/*");
                intent.setType("text/plain");
                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                String content = readTextFile(uri);
                TextView tv1 = (TextView)findViewById(R.id.textView1);
                tv1.setText(content);
                tv1.setMovementMethod(new ScrollingMovementMethod());
            }
        }
    }

    private String readTextFile(Uri uri){
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)));
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

    /*
    // See: https://gist.github.com/dwelch2344/
    private static final String FOLDER = "/Users/dave/Desktop/encrypted/";
    private static final String PASS = "abc123";
    String decrypt() {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        File encryptedFile = new File(FOLDER + "symmetric.txt.gpg");
        byte[]  encryptedByteArray = FileUtils.readFileToByteArray(encryptedFile);

        byte[] decryptedByteArray = ByteArrayHandler.decrypt(encryptedByteArray, PASS.toCharArray());
        String decryptedString = new String(decryptedByteArray);

        System.out.println(decryptedString);

        System.out.println();

        byte[] encryptedAgain = ByteArrayHandler.encrypt(decryptedByteArray, PASS.toCharArray(), "foobar.txt", SymmetricKeyAlgorithmTags.AES_256, true);
        String encryptedAgainString = new String(encryptedAgain);
        System.out.println(encryptedAgainString);


        byte[] decryptedAgainByteArray = ByteArrayHandler.decrypt(encryptedAgain, PASS.toCharArray());
        String decrypteAgaindString = new String(decryptedAgainByteArray);
        System.out.println(decrypteAgaindString);
    }
    */
}
