package it.jaschke.alexandria;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by HiepLT on 12/14/15.
 */
public class ScanActivity extends Activity implements ZBarScannerView.ResultHandler {
    private static final String TAG = "BARCODE_SCAN_ACTIVITY";

    private ZBarScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        // Initialize Barcode Format
        ArrayList<BarcodeFormat> barcodeFormats = new ArrayList<BarcodeFormat>();
        barcodeFormats.add(BarcodeFormat.ISBN13);

        mScannerView = new ZBarScannerView(this);       // Programmatically initialize the scanner view
        // mScannerView.setFormats(barcodeFormats);
        setContentView(mScannerView);                   // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getContents()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)


        Intent data = new Intent();
        data.putExtra(AddBook.SCAN_CONTENTS, rawResult.getContents());
        data.putExtra(AddBook.SCAN_FORMAT,   rawResult.getBarcodeFormat().toString());

        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        finish();
    }
}
