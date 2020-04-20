package com.example.halp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : Fragment() {

    private val MAPKIT_API_KEY = "161a85a8-6f5e-475b-9b54-7e43cb5cde73"
    private val TARGET_LOCATION =
        Point(59.945933, 30.320045)

    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false);
    }

    override fun onStop() { // Activity onStop call must be passed to both MapView and MapKit instance.
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() { // Activity onStart call must be passed to both MapView and MapKit instance.
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapview.onStart()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        MapKitFactory.initialize(this.context)
        val rootView: View = inflater.inflate(R.layout.fragment_map, container, false)
        setHasOptionsMenu(false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(mapview != null){mapview.map.move(
            CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 5F),
            null
        )}
    }
}