import hr.fer.biomed.flexyNET.ApplicationActionInterface;
import hr.fer.biomed.flexyNET.GenericStream;
import hr.fer.biomed.flexyNET.flexyNETLibrary;
import hr.fer.biomed.flexyNET.flexyNetPacket;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSpinnerUI;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class DiaplatMain extends JFrame implements ApplicationActionInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static flexyNETLibrary networkLibrary;
	

	
	public static boolean a;


	public DiaplatMain(final String Username, final String Passwd, flexyNETLibrary nLibrary) {
		
		setTitle("Diaplat --Version 0.0.1");
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		//networkLibrary = new flexyNETLibrary(1, this);
		this.setUndecorated(true); 
		networkLibrary = nLibrary;
		final ClassLoader cl = this.getClass().getClassLoader();
		final DiaplatMain thisApp = this;
		final ExecuteShellComand obj = new ExecuteShellComand();
		final String command = "lsusb";



    
       
		
		JDesktopPane desktopPane = new JDesktopPane() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			ImageIcon icon = new ImageIcon(cl.getResource("pozadina.png"));
		    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		    int width = (int) screenSize.getWidth();
		    int height = (int) screenSize.getHeight();
		    Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_REPLICATE);
		    @Override
            protected void paintComponent(Graphics grphcs) {
                super.paintComponent(grphcs);
                grphcs.drawImage(image, 0, 0, null);
                repaint();
                revalidate();
            }
		};
		desktopPane.setBackground(new Color(25, 25, 112));
		contentPane.add(desktopPane, BorderLayout.CENTER);
		setVisible(true);

		
		final JLabel lblStatus = new JLabel("Disconnected");
		lblStatus.setForeground(Color.RED);
		lblStatus.setBounds(163, 75, 104, 15);
		desktopPane.add(lblStatus);
		
		final JLabel lblStatus_1 = new JLabel("Disconnected");
		lblStatus_1.setForeground(Color.RED);
		lblStatus_1.setBounds(397, 75, 104, 15);
		desktopPane.add(lblStatus_1);
		
		final JLabel lblStatus_2 = new JLabel("Disconnected");
		lblStatus_2.setForeground(Color.RED);
		lblStatus_2.setBounds(279, 75, 106, 15);
		desktopPane.add(lblStatus_2);
		

		
		JLabel lblBp = new JLabel();
		lblBp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBp.setIcon(new ImageIcon(cl.getResource("tlakomjer.png")));
		lblBp.setBounds(189, 41, 25, 25);
		desktopPane.add(lblBp);
		lblBp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String output = obj.executeCommand(command);
				System.out.println(output);
				if (output.toLowerCase().contains("Canon".toLowerCase())) {
					lblStatus.setForeground(Color.GREEN);
					lblStatus.setText("Connected");
				}
				else if (!output.toLowerCase().contains("Canon".toLowerCase())) {
					lblStatus.setForeground(Color.RED);
					lblStatus.setText("Disconnected");
				}
			}
		});
		
		JLabel lblScale = new JLabel();
		lblScale.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblScale.setIcon(new ImageIcon(cl.getResource("vaga.png")));
		lblScale.setBounds(306, 41, 25, 25);
		desktopPane.add(lblScale);
		lblScale.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (lblStatus_2.getText() == "Disconnected"){
					lblStatus_2.setForeground(Color.GREEN);
					lblStatus_2.setText("Connected");
				}
				else{
					lblStatus_2.setForeground(Color.RED);
					lblStatus_2.setText("Disconnected");

				}
			}
		});
		
		JLabel lblScanner = new JLabel();
		lblScanner.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblScanner.setIcon(new ImageIcon(cl.getResource("skener.png")));
		lblScanner.setBounds(410, 41, 45, 25);
		desktopPane.add(lblScanner);
		lblScanner.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (lblStatus_1.getText() == "Disconnected"){
					lblStatus_1.setForeground(Color.GREEN);
					lblStatus_1.setText("Connected");
				}
				else{
					lblStatus_1.setForeground(Color.RED);
					lblStatus_1.setText("Disconnected");

				}
			}
		});
		
		final JButton btnServerStatus = new JButton("Disconnect");
		btnServerStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnServerStatus.getText() == "Connect"){

					Runnable r = new Runnable() {
				         public void run() {
				        	 if (Username.length() > 0 && Passwd.length() > 0) {
				        		networkLibrary.LoginToNetwork("heartways.no-ip.org", 3000, Username, Passwd);//"luka.celic", "lukaaa"); "heartways.no-ip.org
				        	 }
				        }
				    };
				    new Thread(r).start();
				    
					while (networkLibrary.IsConnectionBidirectionalFullAutenticated() == false)
						try {
							setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
							Thread.sleep(5000);
							setCursor(null);
						} catch (InterruptedException e) {	
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    
						btnServerStatus.setText("Disconnect");

				}
				else {
					try {
						networkLibrary.DisconnectFormNetwork();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					networkLibrary = new flexyNETLibrary(1, thisApp);
					btnServerStatus.setText("Connect");
				}
			}
		});
		btnServerStatus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServerStatus.setBounds(12, 41, 117, 25);
		desktopPane.add(btnServerStatus);
		
		final JLabel lblStatusfoot = new JLabel();
		lblStatusfoot.setBounds(12, 453, 125, 15);
		desktopPane.add(lblStatusfoot);
		lblStatusfoot.setVisible(false);
		
		JLabel lblFoot = new JLabel();
		lblFoot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblFoot.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				@SuppressWarnings("unused")
				ScanImage Scan = new ScanImage(networkLibrary, Username);
				
			}
		});
		lblFoot.setIcon(new ImageIcon(cl.getResource("noge.png")));
		lblFoot.setBounds(12, 242, 200, 200);
		desktopPane.add(lblFoot);
		
		JLabel lblBloodpressure = new JLabel();
		lblBloodpressure.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBloodpressure.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		lblBloodpressure.setIcon(new ImageIcon(cl.getResource("tlak.png")));
		lblBloodpressure.setBounds(464, 228, 250, 250);
		desktopPane.add(lblBloodpressure);
		
		JLabel lblBathscale = new JLabel();
		lblBathscale.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBathscale.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		lblBathscale.setIcon(new ImageIcon(cl.getResource("velikaVaga.png")));
		lblBathscale.setBounds(996, 243, 225, 225);
		desktopPane.add(lblBathscale);
		
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setBounds(474, 500, 240, 14);
		desktopPane.add(progressBar_1);
		
		JProgressBar progressBar_2 = new JProgressBar();
		progressBar_2.setBounds(1012, 500, 200, 14);
		desktopPane.add(progressBar_2);
		
		final JButton btnStates = new JButton("Normal");
		btnStates.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnStates.setBounds(474, 526, 240, 25);
		desktopPane.add(btnStates);
		
		final JSpinner Sist = new JSpinner();
		Sist.setUI(new BasicSpinnerUI() {
		      @Override protected Component createPreviousButton() {
		        Component b = super.createPreviousButton();
		        JPanel wrap = new JPanel(new BorderLayout());
		        wrap.add(b);
		        wrap.setPreferredSize(new Dimension(40, 0));
		        return wrap;
		      }
		    });
		Sist.setModel(new SpinnerNumberModel(180, 150, 240, 10));
		Sist.setBounds(474, 575, 70, 25);
		desktopPane.add(Sist);
		Sist.setVisible(false);
		
		final JSpinner Diast = new JSpinner();
		Diast.setUI(new BasicSpinnerUI() {
		      @Override protected Component createPreviousButton() {
		        Component b = super.createPreviousButton();
		        JPanel wrap = new JPanel(new BorderLayout());
		        wrap.add(b);
		        wrap.setPreferredSize(new Dimension(40, 0));
		        return wrap;
		      }
		    });
		Diast.setModel(new SpinnerNumberModel(140, 120, 200, 10));
		Diast.setBounds(474, 612, 70, 25);
		desktopPane.add(Diast);
		Diast.setVisible(false);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					networkLibrary.DisconnectFormNetwork();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					LoginWindow newUser = new LoginWindow();
					newUser.setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});
		btnLogOut.setBounds(12, 75, 117, 25);
		desktopPane.add(btnLogOut);
		
		JLabel lblYouruser = new JLabel("Welcome " + Username);
		lblYouruser.setForeground(new Color(255, 102, 102));
		lblYouruser.setBackground(Color.ORANGE);
		lblYouruser.setBounds(12, 112, 200, 20);
		desktopPane.add(lblYouruser);
		
		JButton btnOlderImages = new JButton("Older Images");
		btnOlderImages.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OldImages oldImages = new OldImages(Username);
				oldImages.setVisible(true);
			}
		});
		btnOlderImages.setBounds(22, 453, 150, 25);
		desktopPane.add(btnOlderImages);
		
		
		
		
		btnStates.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// start Scan.sh
				if (btnStates.getText() == "Normal") {
					btnStates.setText("PreHiper");
				}
				else if (btnStates.getText() == "PreHiper") {
					btnStates.setText("Hiper");
				}
				else if (btnStates.getText() == "Hiper") {
					btnStates.setText("Hipo");
				}
				else if (btnStates.getText() == "Hipo") {
					btnStates.setText("Other...");
					Diast.setVisible(true);
					Sist.setVisible(true);
				}
				else if (btnStates.getText() == "Other...") {
					btnStates.setText("Normal");
					Diast.setVisible(false);
					Sist.setVisible(false);
				}
			}
		});
		
		
		new Timer().schedule(new TimerTask() {
			public void run()  {
				String output = obj.executeCommand(command);
				if (output.toLowerCase().contains("Canon".toLowerCase()) || output.toLowerCase().contains("DeskJet".toLowerCase())) {
					lblStatus_1.setForeground(Color.GREEN);
					lblStatus_1.setText("Connected");
					
				}
				else if (!output.toLowerCase().contains("Canon".toLowerCase()) || !output.toLowerCase().contains("DeskJet".toLowerCase())) {
					lblStatus_1.setForeground(Color.RED);
					lblStatus_1.setText("Disconnected");
					
				}
			}
			}, 5000, 5000);
		
		
			
		

	}
	

	
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
