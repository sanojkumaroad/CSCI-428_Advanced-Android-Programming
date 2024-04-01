package edu.niu.android.locator;

import edu.niu.android.locator.databinding.ActivityMapsBinding;
import androidx.fragment.app.FragmentActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.content.pm.PackageManager;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback {
    private GoogleMap mMap;
    private static final int LOCATION_REQUEST_CODE = 101;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,0);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the Support MapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /*** Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null) {
            int permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);

            if (permission == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                LatLng locationLatLng = new LatLng(latitude, longitude);
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(locationLatLng, 7f);
                mMap.moveCamera(update);
            } else {
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE);
            }
        }
    }

    protected void requestPermission(String permissionType, int requestCode){
        ActivityCompat.requestPermissions(this,new String[]{permissionType}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case LOCATION_REQUEST_CODE:
            {
                if (grantResults.length == 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,
                            "Unable to show location - permission required",
                            Toast.LENGTH_LONG).show();
                } else{
                    SupportMapFragment mapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                }
            }
        }
    }
}
