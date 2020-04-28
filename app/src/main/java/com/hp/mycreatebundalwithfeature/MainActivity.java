package com.hp.mycreatebundalwithfeature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private SplitInstallManager splitInstallManager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiate an instance of SplitInstallManager for the dynamic feature module
        splitInstallManager =
                SplitInstallManagerFactory.create(getApplicationContext());

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                featureOne();
                
            }
        });findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                featureTwo();
            }
        });

    }

    private void featureTwo() {
        // Builds a request to install the feature2 module
        SplitInstallRequest request =
                SplitInstallRequest
                        .newBuilder()
                        // You can download multiple on demand modules per
                        // request by invoking the following method for each
                        // module you want to install.
                        .addModule("feature2")
                        .build();

        // Begin the installation of the feature2 module and handle success/failure
        splitInstallManager
                .startInstall(request)
                .addOnSuccessListener(new OnSuccessListener<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        Intent intent = new Intent().setClassName(getPackageName(), "com.hp.feature2.FeatureTwoActivity");
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getApplicationContext(), "Couldn't download feature2: " + e.getMessage(), Toast.LENGTH_LONG).show();

                        // Module download failed; handle the error here
                    }
                });

    }

    private void featureOne() {
        // Builds a request to install the feature1 module
        SplitInstallRequest request =
                SplitInstallRequest
                        .newBuilder()
                        // You can download multiple on demand modules per
                        // request by invoking the following method for each
                        // module you want to install.
                        .addModule("feature1")
                        .build();

        // Begin the installation of the feature1 module and handle success/failure
        splitInstallManager
                .startInstall(request)
                .addOnSuccessListener(new OnSuccessListener<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        // Module download successful
                        Intent intent = new Intent().setClassName(getPackageName(), "com.hp.feature1.FeatureOneActivity");
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Module download failed; handle the error here
                        Toast.makeText(getApplicationContext(), "Couldn't download feature1: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }
}
