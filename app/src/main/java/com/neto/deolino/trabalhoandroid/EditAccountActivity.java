package com.neto.deolino.trabalhoandroid;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.neto.deolino.trabalhoandroid.enumerations.Gender;

/**
 * Created by deolino on 30/10/16.
 */
public class EditAccountActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CODE_GET_PHOTO  = 1;
    private static final int ACTIVITY_RESULT_GET_IMG            = 2;

    EditText etName;
    EditText etDescription;
    RadioGroup rgSex;
    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        etName = (EditText) findViewById(R.id.etName);
        rgSex = (RadioGroup) findViewById(R.id.rgSex);
        etDescription = (EditText) findViewById(R.id.etUserDescription);

    }

    @Override
    protected void onPause() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", etName.getText().toString());
        editor.putInt("sex", rgSex.getCheckedRadioButtonId());
        editor.putString("description", etDescription.getText().toString());
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        etName.setText(prefs.getString("name", etName.getText().toString()));
        rgSex.check(prefs.getInt("sex", rgSex.getCheckedRadioButtonId()));
        etDescription.setText(prefs.getString("description", etDescription.getText().toString()));
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_account_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION_CODE_GET_PHOTO){
            if((grantResults.length>0)&&(PackageManager.PERMISSION_GRANTED==grantResults[0])){
                imgLocal();
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuAddFriends:
                intent = new Intent(this, AddFriendsActivity.class);
                startActivity(intent);
                break;
            case R.id.menuAddPhoto:
                if(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                    imgLocal();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE_GET_PHOTO);
                }
                break;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void imgLocal() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, ACTIVITY_RESULT_GET_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_RESULT_GET_IMG && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            image = BitmapFactory.decodeFile(picturePath);
        }
    }


    public void saveButtonClicked(View view){
        Intent intent;
       // user.setGender(rgSex.getCheckedRadioButtonId()==R.id.rbMale ? Gender.MALE : Gender.FEMALE);
        String name = etName.getText().toString();
        String description = etDescription.getText().toString();
        intent = new Intent(this, UserDerscriptionActivity.class);
        startActivity(intent);
       // if(!name.isEmpty()) user.setName(name);
        //if(!description.isEmpty()) user.setDescription(description);
        //if(image != null) user.setImage(image);
    }
}
