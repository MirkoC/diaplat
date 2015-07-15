import hr.fer.biomed.flexyNET.ApplicationActionInterface;
import hr.fer.biomed.flexyNET.CallbackActionInterface;
import hr.fer.biomed.flexyNET.GenericStream;
import hr.fer.biomed.flexyNET.GenericStreamManager;
import hr.fer.biomed.flexyNET.flexyNETLibrary;
import hr.fer.biomed.flexyNET.flexyNetPacket;
import hr.fer.biomed.flexyNET.flexyNet_CONST;
import hr.fer.biomed.flexyNET.pSQLStruturedDataClass;


public class SendScannedImage implements ApplicationActionInterface {
	
	public static flexyNETLibrary networkLibrary;
	
	public SendScannedImage(byte[] ScannedImage, flexyNETLibrary nLibrary ) throws InterruptedException {
		
		
		pSQLStruturedDataClass Table = AddPatientScan(2, ScannedImage);
		networkLibrary = nLibrary;
		try {
			networkLibrary.SendStreamWithCallbackAndWaitForReply(_StreamCallback, flexyNet_CONST.NODE_ADDRESS_INTERPRETER,(byte)flexyNet_CONST.TYPE_DB_STREAM, GenericStreamManager.STREAM_TYPE_DB_REQUEST, "", Table.AddDataToByteArray(0, 0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Image sent.");
	}
	
	public pSQLStruturedDataClass AddPatientScan(int PatientID, byte[] ScannedImage) { 
		pSQLStruturedDataClass Table = new pSQLStruturedDataClass(1, 1, "Add Patient Scan", "Add Patient Scan".length());
		Object[] tempData = new Object[3];
		
		
		tempData[0] = PatientID;
		tempData[1] = ScannedImage;
		tempData[2] = 1;
		Table.AddRowData(0, tempData, 3);

		return Table;
	}
	
	private CallbackActionInterface _StreamCallback = new CallbackActionInterface() {
		public boolean StreamCalbackAction(GenericStream stream, int connectionNumber, CallbackArgument callbackArgument) {
			return(true);
		}
	};
	
	


	@Override
	public void ApplicationNotificationAction(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ApplicationPacketAction(flexyNetPacket arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean ApplicationStreamAction(GenericStream arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void processPacketDecodeError() {
		// TODO Auto-generated method stub
		
	}

}
