package com.example.halp

import android.os.Bundle;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;

import com.yandex.mapkit.mapview.MapView;


class MapFragment : Fragment() {

    private val MAPKIT_API_KEY = "161a85a8-6f5e-475b-9b54-7e43cb5cde73"
    private val TARGET_LOCATION: Point = Point(59.945933, 30.320045)

    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {


        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        MapKitFactory.initialize(this.context)
        setContentView(R.layout.fragment_map)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false);
        mapView = findViewById(R.id.mapview) as MapView

        mapView!!.map.move(
            CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 5F),
            null
        )



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false);
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStop() {
        mapView!!.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView!!.onStart()
    }
}
