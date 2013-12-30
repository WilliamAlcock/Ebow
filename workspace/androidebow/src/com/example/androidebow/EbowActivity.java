package com.example.androidebow;

import android.os.Bundle;
import android.app.Activity;

public class EbowActivity extends Activity {
	/** Hold a reference to our GLSurfaceView */
	private EbowSurfaceView mGLSurfaceView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (mGLSurfaceView==null) {
			System.out.println("New surface view");
			mGLSurfaceView = new EbowSurfaceView(this);
		}
		setContentView(mGLSurfaceView);
	}
	
	@Override
	protected void onResume() {
		// The activity must call the GL surface view's onResume() on activity
		// onResume().
		super.onResume();
		mGLSurfaceView.onResume();
	}
	
	@Override
	protected void onPause() {
		// The activity must call the GL surface view's onPause() on activity
		// onPause().
		super.onPause();
		mGLSurfaceView.onPause();
	}
}
