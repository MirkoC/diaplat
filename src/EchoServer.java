import javax.bluetooth.*;

public class EchoServer {
    

	private LocalDevice localDevice; // local Bluetooth Manager
	private DiscoveryAgent discoveryAgent; // discovery agent


	public EchoServer() throws BluetoothStateException {
	    localDevice = null;
	    setDiscoveryAgent(null);
	    // Retrieve the local device to get to the Bluetooth Manager
	    localDevice =  
	                                         LocalDevice.getLocalDevice();                   
	    // Servers set the discoverable mode to GIAC
	     
	                                         localDevice.setDiscoverable(DiscoveryAgent.GIAC);                   
	    // Clients retrieve the discovery agent
	    setDiscoveryAgent(localDevice.getDiscoveryAgent());                   
	}


	public DiscoveryAgent getDiscoveryAgent() {
		return discoveryAgent;
	}


	public void setDiscoveryAgent(DiscoveryAgent discoveryAgent) {
		this.discoveryAgent = discoveryAgent;
	}
    
    
}