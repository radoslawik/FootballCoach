package footballcoach.com.footballcoach;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddMatchActivity extends AppCompatActivity implements LocationListener{

    private LocationManager locationManager;
    private String locationProvider;
    protected LocationListener locationListener;

    static final int REQUEST_TAKE_PHOTO = 1;
    String currentPhotoPath;

    private SeekBar seekbarHomePassAcc, seekbarAwayPassAcc, seekbarBallPos;
    private TextView tvHomePassAcc, tvAwayPassAcc, tvHomeBallPos, tvAwayBallPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_add_match);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerMatchType);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.match_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // location part
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        locationProvider = locationManager.getBestProvider(criteria, false);
        Location location = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            location = locationManager.getLastKnownLocation(locationProvider);
        }
        if (location != null) {
            System.out.println("Provider " + locationProvider + " has been selected.");
            onLocationChanged(location);
        } else {
            System.out.println("Location not available");
        }

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String strDate = formatter.format(c);
        TextView txtDate = (TextView) findViewById(R.id.tvDate);
        txtDate.setText(strDate);

        //seekbars part
        seekbarHomePassAcc = (SeekBar)findViewById(R.id.seekbarHomePassAcc);
        tvHomePassAcc = (TextView)findViewById(R.id.tvHomePassAcc);
        seekbarHomePassAcc.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvHomePassAcc.setText(String.valueOf(progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                return;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                return;
            }
        });

        seekbarAwayPassAcc = (SeekBar)findViewById(R.id.seekbarAwayPassAcc);
        tvAwayPassAcc = (TextView)findViewById(R.id.tvAwayPassAcc);
        seekbarAwayPassAcc.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvAwayPassAcc.setText(String.valueOf(progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                return;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                return;
            }
        });

        seekbarAwayPassAcc = (SeekBar)findViewById(R.id.seekbarAwayPassAcc);
        tvAwayPassAcc = (TextView)findViewById(R.id.tvAwayPassAcc);
        seekbarAwayPassAcc.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvAwayPassAcc.setText(String.valueOf(progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                return;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                return;
            }
        });

        seekbarBallPos = (SeekBar)findViewById(R.id.seekbarBallPos);
        tvAwayBallPos = (TextView)findViewById(R.id.tvAwayBallPos);
        tvHomeBallPos = (TextView)findViewById(R.id.tvHomeBallPos);
        seekbarBallPos.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvHomeBallPos.setText(String.valueOf(progress)+"%");
                tvAwayBallPos.setText(String.valueOf(seekBar.getMax()-progress)+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                return;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                return;
            }
        });

    }

    public void addNewMatch(View view){
        Intent retIntent = new Intent();
        Match newMatch = new Match(
                1,
                "",
                "fakeOne",
                "Friendly",
                Calendar.getInstance().getTime(),
                new TeamStats(3),
                new TeamStats(4),
                R.drawable.ic_menu_camera);
        retIntent.putExtra("newMatch", newMatch);
        setResult(Activity.RESULT_OK, retIntent);
        finish();
    }

    @Override
    public void onLocationChanged(Location location) {

        TextView txtLoc = (TextView) findViewById(R.id.tvLocation);
        String parsedLocation = "Unknown location";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (addresses != null && addresses.size() > 0) {
                Address returnedAddress = addresses.get(0);
                parsedLocation = returnedAddress.getThoroughfare() + ",\n" + returnedAddress.getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while geocoding");
        }
        System.out.println(parsedLocation);
        txtLoc.setText(parsedLocation);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    public void addPhotoClick(View view)
    {
        dispatchTakePictureIntent();
        // Do something in response to button click
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                System.out.println("error while creating the file");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "footballcoach.com.footballcoach.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


/*    private void setPic() {
        ImageView imageView = (ImageView)findViewById(R.id.testimage);
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }*/
}
