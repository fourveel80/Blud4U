package com.example.blood4u;



import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    Button signoutbutton;
    private static int RESULT_LOAD_IMG = 1;

    private DatabaseReference databaseReference;
    private TextView profileNameTextView, profileSurnameTextView, profileBirthDayTextView, profileBloodTypeTextView, profileBloodDonationTextView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ImageView profilePicImageView;
    private FirebaseStorage firebaseStorage;
    private TextView textViewemailname;
    private StorageReference storageReference;
    private static int PICK_IMAGE = 123;
    private Bitmap bitmap;
    Uri imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        profilePicImageView = findViewById(R.id.profile_pic_imageView);
        profileNameTextView = findViewById(R.id.profile_name_textView);
        profileSurnameTextView = findViewById(R.id.profile_surname_textView);
        profileBirthDayTextView = findViewById(R.id.profile_birthday_textView);
        profileBloodTypeTextView = findViewById(R.id.profile_bloodtype_textView);
        profileBloodDonationTextView = findViewById(R.id.profile_blooddonation_textView);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        try {
            databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        } catch (RuntimeException e) {
            Toast.makeText(ProfileActivity.this, "Profile has not yet been created",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        try {
            storageReference = firebaseStorage.getReference();
        } catch (RuntimeException e) {
            Toast.makeText(ProfileActivity.this, "Profile has not yet been created",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        // Get the image stored on Firebase via "User id/Images/Profile Pic.jpg".
        storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Using "Picasso" (http://square.github.io/picasso/) after adding the dependency in the Gradle.
                // ".fit().centerInside()" fits the entire image into the specified area.
                // Finally, add "READ" and "WRITE" external storage permissions in the Manifest.
                Picasso.get().load(uri).fit().centerInside().into(profilePicImageView);
            }
        });

        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                try {
                    Userinformation userProfile = dataSnapshot.getValue(Userinformation.class);
                    profileNameTextView.setText(userProfile.getUserName());
                    profileSurnameTextView.setText(userProfile.getUserSurname());
                    profileBirthDayTextView.setText(userProfile.getUserBirthday());
                    profileBloodTypeTextView.setText(userProfile.getUserbloodtype());
                    profileBloodDonationTextView.setText(userProfile.getUserblooddonate());
                    textViewemailname = (TextView) findViewById(R.id.textViewEmailAdress);
                    textViewemailname.setText(user.getEmail());
                } catch (NullPointerException e) {
                    Toast.makeText(ProfileActivity.this, "Cannot Find Profile Please Re sign in again.",
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled( DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        signoutbutton = (Button) findViewById(R.id.signoutbutton);
        signoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                profilePicImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }
    //bottomnavigationbar copy start from this line
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            Intent home_intent = new Intent(ProfileActivity.this,HomeActivity.class);
                            startActivity(home_intent);
                            break;
                        case R.id.navigation_setting:
                            Intent search_intent = new Intent(ProfileActivity.this, HomeActivity.class);
                            startActivity(search_intent);
                            break;
                        case R.id.navigation_Profile:
                            Intent profile_intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                            startActivity(profile_intent);
                            break;

                        // not finished
                    }
                    return false;
                }
            };
    //bottomnavigationbar copy finish at this line
    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {

        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            if (firebaseUser == null) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
            }
        }
    };

}
