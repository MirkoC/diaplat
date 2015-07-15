import hr.fer.biomed.flexyNET.flexyNETLibrary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ImageViewer extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static flexyNETLibrary networkLibrary;
	public byte[] ImageInBytes;
	
    private String uname;
	

	public ImageViewer(byte[] imageInByte, flexyNETLibrary nLibrary, String Username) {
		uname = Username;
		networkLibrary = nLibrary;
		ImageInBytes = imageInByte;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int width = (int) screenSize.getWidth() - 638 - 638/2;
	    final int height = (int) screenSize.getHeight()- 876/4;
		setBounds(100, 100, width , height);
		getContentPane().setLayout(new BorderLayout());
		final JPanel contentPanel = new JPanel() {
			/**
			 * 
			 */
			String sysUname = System.getProperty("user.name");
			private static final long serialVersionUID = 1L;
			ImageIcon icon = new ImageIcon("/home/"+sysUname+"/"+uname+"/converted.jpg");
		    Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_REPLICATE);
		    @Override
	        protected void paintComponent(Graphics grphcs) {
	            super.paintComponent(grphcs);
	            grphcs.drawImage(image, 0, 0, null);
	            repaint();
	            revalidate();
	        }
		};

		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setUndecorated(true);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(0, 0, 139));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Send");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							@SuppressWarnings("unused")
							SendScannedImage SendingImage = new SendScannedImage(ImageInBytes, networkLibrary);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String sysUname = System.getProperty("user.name");
						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Calendar cal = Calendar.getInstance();
						File file = new File("/home/"+sysUname+"/"+uname+"/converted.jpg");
						File file2 = new File("/home/"+sysUname+"/"+uname+"/"+dateFormat.format(cal.getTime()).replaceAll("\\s","").replaceAll("/", "")+".jpg");
						file.renameTo(file2);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
