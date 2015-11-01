# google-directions-helper
Gets google directions and maps them to objects. Integrates nicely with android google maps api.

###Example usage:

In class, override RouteInfoTask.InfoRetrievedListener:
```
...
 @Override
    public void onInfoRetrieved(RouteInformation info)
    {
		//A static holder for the RouteInformation, if going to another Activity
        RouteInformationHolder.INFO = info;
        Intent mapsIntent = new Intent(MyActivity.this, MapsActivity.class);
        startActivity(mapsIntent);
    }
... 

```
Calling the API:

```
RouteInfoTask task = new RouteInfoTask(new StartEndPoints(
                       startAddress,
                        endAddress);
				task.registerInfoListener(this);
				task.execute();
                
```

In class where you use maps:

```
@Override
    public void onMapReady(GoogleMap map) {
        RouteInformation ri = RouteInformationHolder.INFO;
        if(ri != null){
            map.addMarker(new MarkerOptions().position(ri.getStartPoint()).title(ri.getStartAddress()));
            map.addMarker(new MarkerOptions().position(ri.getEndPoint()).title(ri.getEndAddress()));
            for(Route r: ri.getRoutes()){
                map.addPolyline(r.getOverviewPolyline());
            }
            LatLngBounds bounds = new LatLngBounds(ri.getRoutes().get(0).getSouthwest(),
                    ri.getRoutes().get(0).getNortheast());
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(ri.getBounds(), 10));
        }
    } 

```