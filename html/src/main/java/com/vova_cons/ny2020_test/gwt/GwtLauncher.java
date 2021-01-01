package com.vova_cons.ny2020_test.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.vova_cons.ny2020_test.Core;

/** Launches the GWT application. */
public class GwtLauncher extends GwtApplication {
		@Override
		public GwtApplicationConfiguration getConfig () {
			// Resizable application, uses available space in browser
			return new GwtApplicationConfiguration(true);
			// Fixed size application:
			//return new GwtApplicationConfiguration(480, 320);
		}

		@Override
		public ApplicationListener createApplicationListener () { 
			return new Core();
		}
}
