import hr.fer.biomed.flexyNET.flexyNETLibrary;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;


public class AnalogButtonListening {
	
	public volatile static long lastCalled = 0;
	public static flexyNETLibrary networkLibrary;
	public byte[] Img;
	private String uname;
	
	public AnalogButtonListening(flexyNETLibrary nLibrary, String Username) throws InterruptedException {
		final GpioController gpio = GpioFactory.getInstance();
		networkLibrary = nLibrary;
		uname = Username;
		GpioPinDigitalInput ScanningButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, "ScannignButton", PinPullResistance.PULL_DOWN);
	

		
		ScanningButton.addListener(new GpioButtonListener());
		
		//for (;;)
        //    Thread.sleep(500);
   
	}
	
	public class GpioButtonListener implements GpioPinListenerDigital {

		@Override
		public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
			
			if (lastCalled + 200 < System.currentTimeMillis()){
				lastCalled = System.currentTimeMillis();
			}
			else return;
			
			if (event.getState().isHigh()) {
				@SuppressWarnings("unused")
				ScanImage scan = new ScanImage(networkLibrary, uname);
				//ImageViewer ImgView = new ImageViewer(scan.getImageInByte(), networkLibrary, uname);
				//ImgView.setVisible(true);
			}
		
	
		}
	}

}
