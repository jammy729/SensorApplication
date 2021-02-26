package com.example.sensortestapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
	private SensorManager sensorManager = null;
	private Sensor accelSensor = null;
	private Sensor lightSensor = null;

	TextView sensorName;
	EditText accelXEditText, accelYEditText, accelZEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sensorName = (TextView) findViewById(R.id.sensorName);
		accelXEditText = (EditText) findViewById(R.id.editTextTextPersonName);
		accelYEditText = (EditText) findViewById(R.id.editTextTextPersonName2);
		accelZEditText = (EditText) findViewById(R.id.editTextTextPersonName);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

		List <Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

		for (int i = 0; i < sensorList.size(); i++) {
			sensorName.append("\n" + sensorList.get(i).getName());
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

	}

	@Override
	protected void onPause() {
		sensorManager.unregisterListener(this);
		super.onPause();
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		int type = sensorEvent.sensor.getType();
		if (type == Sensor.TYPE_ACCELEROMETER) {
			float[] accelValues = sensorEvent.values;
			accelXEditText.setText("AccelX: " + accelValues[0]);
			accelYEditText.setText("AccelZ: " + accelValues[1]);
			accelZEditText.setText("AccelY: " + accelValues[2]);

		} else if (type == Sensor.TYPE_LIGHT) {
			float[] lightValues = sensorEvent.values;
			Log.d("MainActivity", "Light is  " + lightValues[0]);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
}