package com.tulu.vnpetrolstation.remote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tulu.vnpetrolstation.common.Const;
import com.tulu.vnpetrolstation.common.VNPetrolStation;
import com.tulu.vnpetrolstation.model.GPSPoint;
import com.tulu.vnpetrolstation.model.POI;
import com.tulu.vnpetrolstation.utils.Utils;

import android.text.TextUtils;
import android.util.Log;

public class PoiConnector {
	private static final String TAG = PoiConnector.class.getSimpleName();

	private static String generatePoiUrl() {
		String url = "" + Const.POI_PETROL_STATION_SERVER;
		VNPetrolStation nvn = VNPetrolStation.getInstance();
		GPSPoint gps = nvn.getLastGPSPoint();
//		long lastTime = nvn.getLastGPSUpdateTime();
//		long now = (new Date()).getTime();
//		
//		if (lastTime + (long) 30 * 60 * 1000 < now)
//			return null;

		url += "" + (gps.getLat() - 0.07) + ",";
		url += "" + (gps.getLng() - 0.07) + ",";
		url += "" + (gps.getLat() + 0.07) + ",";
		url += "" + (gps.getLng() + 0.07);
		Log.v(TAG, url);
		return url;
	}

	public static List<POI> getPois() {
		String urlString = generatePoiUrl();
		if (TextUtils.isEmpty(urlString))
			return null;

		String encoded = "";
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(urlString);
			HttpResponse responseGet = client.execute(get);
			HttpEntity resEntityGet = responseGet.getEntity();
			if (resEntityGet != null) {
				String response = EntityUtils.toString(resEntityGet);
				JSONObject respObject = new JSONObject(response);
				String data = respObject.getString("data");
				encoded = Utils.decypher(data);

				ArrayList<POI> pois = new ArrayList<POI>();
				JSONArray pa = new JSONArray(encoded);
				for (int i = 0; i < pa.length(); i++) {
					JSONObject poiJson = pa.getJSONObject(i);

					POI poi = new POI();
					poi.setAddress(poiJson.getString("address"));
					poi.setPhone(poiJson.getString("phone"));
					poi.setTitle(poiJson.getString("title"));

					JSONObject gpsStr = poiJson.optJSONObject("gps");
					GPSPoint gpsPoint = new GPSPoint(gpsStr.getDouble("latitude"), gpsStr.getDouble("longitude"));
					poi.setGps(gpsPoint);

					pois.add(poi);
				}

				return pois;
			}
			return null;
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return null;
		}
	}
}
