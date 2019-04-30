package com.example.erika.lbdapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import com.example.erika.lbdapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private View rootView;
    private MapView mapView;
    private GoogleMap gMap;

    private OnFragmentInteractionListenermaps mListener;

    public MapsFragment() {
        // Required empty public constructor
    }

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        return rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) rootView.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListenermaps) {
            mListener = (OnFragmentInteractionListenermaps) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        // se crean las localizaciones
        LatLng latLng = new LatLng(43.257, -2.923440);
        LatLng latLng1 = new LatLng(43.259, -2.923444);
        LatLng latLng2 = new LatLng(43.250, -2.923444);
        // se añade zoom
        float zoom = 13;
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        //tres marcadores con las localizaciones de las tiendas
        gMap.addMarker(new MarkerOptions().position(latLng));
        gMap.addMarker(new MarkerOptions().position(latLng1));
        gMap.addMarker(new MarkerOptions().position(latLng2));
    }

    public interface OnFragmentInteractionListenermaps {
        // TODO: Update argument type and name
        void onFragmentInteractionmaps();

    }
}
