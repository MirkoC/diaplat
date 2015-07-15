import hr.fer.biomed.flexyNET.flexyNETLibrary;

import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
//import java.io.InputStream;

import javax.imageio.ImageIO;



public class ScanImage {
	

	
	private static byte[] imageInByte;
	public static flexyNETLibrary networkLibrary;
	private String uname;
	public ScanImage(flexyNETLibrary nLibrary, String Username) {
		String sysUname = System.getProperty("user.name");
		uname = Username;
		networkLibrary = nLibrary;
		
		String output = Scan("scan.sh /home/"+sysUname+"/"+uname+"/raw.tiff");
		
		while ("NE\n".equals(output)) {
			System.out.println("Error. Scanning again...");
			output = Scan("scan.sh /home/"+sysUname+"/"+uname+"/raw.tiff");
		}

		if ( "DA\n".equals(output) ){
			System.out.println("Ready to send scanned image.");
			
			try {
				extractBytes2("/home/"+sysUname+"/"+uname+"/converted.jpg");
				///home/pi/diaplat/data/user1/test.ppm
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			ImageViewer ImgView = new ImageViewer(getImageInByte(), nLibrary, uname);
			ImgView.setVisible(true);
		
			
		}
		
	}
	
	public String Scan(String Com) {
		
		System.out.println("Scanning...");
		String sysUname = System.getProperty("user.name");
		
		ExecuteShellComand obj = new ExecuteShellComand();

		String command = Com;
		
		String output = obj.executeCommand(command);
		

		
		String s = "convert.sh" + " /home/"+sysUname+"/"+uname+"/raw.tiff" + " /home/"+sysUname+"/"+uname+"/converted.jpg";
		obj.executeCommand(s);
		//@SuppressWarnings("unused")
		//ImageInfo RGBpixels = new ImageInfo();
		                    
		return output;
		
	}
	
	
	public void extractBytes2 (String Image) {
		try {
						
			BufferedImage originalImage = ImageIO.read(new File(
					Image));
 
			// convert BufferedImage to byte array
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos);
			baos.flush();
			setImageInByte(baos.toByteArray());
			baos.close();
 
			// convert byte array back to BufferedImage
			//InputStream in = new ByteArrayInputStream(imageInByte);
			//BufferedImage bImageFromConvert = ImageIO.read(in);
 
			//ImageIO.write(bImageFromConvert, "jpg", new File("/home/cex/Desktop/OO.jpg"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	
	}

	public byte[] getImageInByte() {
		return imageInByte;
	}

	public static void setImageInByte(byte[] imageInByte) {
		ScanImage.imageInByte = imageInByte;
	}
	
}
