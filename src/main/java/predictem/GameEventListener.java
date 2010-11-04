package predictem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListener;

public class GameEventListener implements AtmosphereResourceEventListener {

	@Override
	public void onBroadcast(AtmosphereResourceEvent<HttpServletRequest, HttpServletResponse> event) {
		System.out.println("onBroadcast: " + event);
		
	}

	@Override
	public void onDisconnect(AtmosphereResourceEvent<HttpServletRequest, HttpServletResponse> event) {
		System.out.println("onDisconnect: " + event);
		
	}

	@Override
	public void onResume(AtmosphereResourceEvent<HttpServletRequest, HttpServletResponse> event) {
		System.out.println("onResume: " + event);
		
	}

	@Override
	public void onSuspend(AtmosphereResourceEvent<HttpServletRequest, HttpServletResponse> event) {
		System.out.println("onSuspend: " + event);
	}

	@Override
	public void onThrowable(Throwable throwable) {
		// TODO Auto-generated method stub
	}
}
