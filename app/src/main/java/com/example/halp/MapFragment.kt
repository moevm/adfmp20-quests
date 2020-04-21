package com.example.halp


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.geometry.SubpolylineHelper
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.RotationType
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.transport.TransportFactory
import com.yandex.mapkit.transport.masstransit.*
import com.yandex.mapkit.transport.masstransit.SectionMetadata.SectionData
import com.yandex.mapkit.transport.masstransit.Session.RouteListener
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.network.NetworkError
import com.yandex.runtime.network.RemoteError
import kotlinx.android.synthetic.main.fragment_map.*
import java.util.*


class MapFragment : Fragment(), UserLocationObjectListener,
    RouteListener {

    private val MAPKIT_API_KEY = "161a85a8-6f5e-475b-9b54-7e43cb5cde73"
    private val TARGET_LOCATION =
        Point(55.752078, 37.592664)

    private val ROUTE_START_LOCATION =
        Point(55.699671, 37.567286)
    private val ROUTE_END_LOCATION =
        Point(55.790621, 37.558571)

    private var mapView: MapView? = null
    private var mtRouter: MasstransitRouter? = null
    private var mapObjects: MapObjectCollection? = null

    private var userLocationLayer: UserLocationLayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false);
    }

    override fun onStop() {
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapview.onStart()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        MapKitFactory.initialize(this.context)
        TransportFactory.initialize(this.context)
        val rootView: View = inflater.inflate(R.layout.fragment_map, container, false)
        setHasOptionsMenu(false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val act = activity as MainActivity

        mapObjects = mapview!!.map.mapObjects.addCollection()


        if(mapview != null){mapview.map.move(
            CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 5F),
            null
        )}

        val options = MasstransitOptions(
            ArrayList(),
            ArrayList(),
            TimeOptions()
        )
        val points: MutableList<RequestPoint> =
            ArrayList()
        points.add(RequestPoint(ROUTE_START_LOCATION, RequestPointType.WAYPOINT, null))
        points.add(RequestPoint(ROUTE_END_LOCATION , RequestPointType.WAYPOINT, null))
        mtRouter = TransportFactory.getInstance().createMasstransitRouter()
        mtRouter!!.requestRoutes(points, options, this as Session.RouteListener)

        /* User Coords */
        val mapKit = MapKitFactory.getInstance()
        userLocationLayer = mapKit.createUserLocationLayer(mapview!!.mapWindow)
        userLocationLayer!!.setVisible(true)
        userLocationLayer!!.setHeadingEnabled(true)
        userLocationLayer!!.setObjectListener(this as UserLocationObjectListener)

    }


    override fun onMasstransitRoutes(routes: List<Route>) { // In this example we consider first alternative only
        if (routes.size > 0) {
            for (section in routes[0].sections) {
                drawSection(
                    section.metadata.data,
                    SubpolylineHelper.subpolyline(
                        routes[0].geometry, section.geometry
                    )
                )
            }
        }
    }

    override fun onMasstransitRoutesError(p0: Error) {
        var errorMessage = getString(R.string.unknown_error_message)
        if (p0 is RemoteError) {
            errorMessage = getString(R.string.remote_error_message)
        } else if (p0 is NetworkError) {
            errorMessage = getString(R.string.network_error_message)
        }
        Toast.makeText(this.context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun drawSection(
        data: SectionData,
        geometry: Polyline
    ) {
        val polylineMapObject = mapObjects!!.addPolyline(geometry)

        if (data.transports != null) {
            for (transport in data.transports!!) {
                if (transport.line.style != null) {
                    polylineMapObject.strokeColor = transport.line.style!!.color!! or -0x1000000
                    return
                }
            }

            val knownVehicleTypes =
                HashSet<String>()
            knownVehicleTypes.add("bus")
            knownVehicleTypes.add("tramway")
            for (transport in data.transports!!) {
                val sectionVehicleType =
                    getVehicleType(transport, knownVehicleTypes)
                if (sectionVehicleType == "bus") {
                    polylineMapObject.strokeColor = -0xff0100 // Green
                    return
                } else if (sectionVehicleType == "tramway") {
                    polylineMapObject.strokeColor = -0x10000 // Red
                    return
                }
            }
            polylineMapObject.strokeColor = -0xffff01 // Blue
        } else {
            polylineMapObject.strokeColor = -0x1000000 // Black
        }
    }

    private fun getVehicleType(
        transport: Transport,
        knownVehicleTypes: HashSet<String>
    ): String? {
        for (type in transport.line.vehicleTypes) {
            if (knownVehicleTypes.contains(type)) {
                return type
            }
        }
        return null
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        userLocationLayer!!.setAnchor(
            PointF((mapView!!.width * 0.5).toFloat(), (mapView!!.height * 0.5).toFloat()),
            PointF((mapView!!.width * 0.5).toFloat(), (mapView!!.height * 0.83).toFloat())
        )
        userLocationView.arrow.setIcon(
            ImageProvider.fromResource(
                this.context, R.drawable.user_arrow
            )
        )
        val pinIcon = userLocationView.pin.useCompositeIcon()
        pinIcon.setIcon(
            "icon",
            ImageProvider.fromResource(this.context, R.drawable.icon),
            IconStyle().setAnchor(PointF(0f, 0f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(0f)
                .setScale(1f)
        )
        pinIcon.setIcon(
            "pin",
            ImageProvider.fromResource(this.context, R.drawable.search_result),
            IconStyle().setAnchor(PointF(0.5f, 0.5f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(1f)
                .setScale(0.5f)
        )
        userLocationView.accuracyCircle.fillColor = Color.BLUE
    }

    override fun onObjectRemoved(p0: UserLocationView) {}

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {}

}


