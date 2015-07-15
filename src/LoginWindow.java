import hr.fer.biomed.flexyNET.ApplicationActionInterface;
import hr.fer.biomed.flexyNET.GenericStream;
import hr.fer.biomed.flexyNET.flexyNETLibrary;
import hr.fer.biomed.flexyNET.flexyNetPacket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JLabel;


public class LoginWindow extends JFrame implements FocusListener, ApplicationActionInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField pwdPasswd;
	private JTextField txtUname;
	final ClassLoader cl = this.getClass().getClassLoader();
	private volatile String U = "";
	private volatile String P = "";
	private volatile int who = -1;
	private volatile int position = 0;
	public static flexyNETLibrary networkLibrary;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow();
					frame.setVisible(true);
					@SuppressWarnings("unused")
					ImageInfo RGBpixels = new ImageInfo();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginWindow() throws IOException {
		
		setTitle("Diaplat LogIn");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setBounds(100, 100, 1366, 768);
		this.setUndecorated(true); 
		networkLibrary = new flexyNETLibrary(1, this);
		final ClassLoader cl = this.getClass().getClassLoader();
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JDesktopPane desktopPane = new JDesktopPane() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			ImageIcon icon = new ImageIcon(cl.getResource("pozadinaLogo.png"));
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
		
		pwdPasswd = new JPasswordField("lukaaa");
		pwdPasswd.setBounds(118, 110, 160, 19);
		pwdPasswd.addFocusListener(this);
		desktopPane.add(pwdPasswd);
		
		txtUname = new JTextField("luka.celic");
		txtUname.setBounds(118, 79, 160, 19);
		desktopPane.add(txtUname);
		txtUname.addFocusListener(this);
		//txtUname.setColumns(10);
		
		final JLabel lblError = new JLabel("Please enter username and password");
		lblError.setVisible(true);
		lblError.setBounds(310, 81, 170, 15);
		desktopPane.add(lblError);
		
		
		
		final JButton btnLogin = new JButton("LogIn");
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				final String Username = txtUname.getText();
				@SuppressWarnings("deprecation")
				final String Passwd = pwdPasswd.getText();
				
				Runnable r = new Runnable() {
			         public void run() {
			        	 if (Username.length() > 0 && Passwd.length() > 0) {
			        		lblError.setVisible(false);
			        		networkLibrary.LoginToNetwork("heartways.no-ip.org", 3000, Username, Passwd);//"luka.celic", "lukaaa"); "heartways.no-ip.org
			        	 }
			        	 else
			        		System.out.println("Please enter username and password");
			        	 	lblError.setVisible(true);
			        }
			    };
			    new Thread(r).start();
			    if (Username.length() > 0 && Passwd.length() > 0) {
					while (networkLibrary.IsConnectionBidirectionalFullAutenticated() == false)
						try {
							setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
							Thread.sleep(5000);
							setCursor(null);
						} catch (InterruptedException e) {	
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					if  (networkLibrary.IsConnectionBidirectionalFullAutenticated())  {
						DiaplatMain main = new DiaplatMain(Username, Passwd, networkLibrary);
						main.setVisible(true);
						String sysUname = System.getProperty("user.name");
						new File("/home/"+sysUname+"/"+Username).mkdir();
						/*try {
							@SuppressWarnings("unused")
							AnalogButtonListening Button = new AnalogButtonListening(networkLibrary, Username);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
						dispose();
					}
					else {
						lblError.setText("Please enter correct username and password.");
					}
			    }
			}
		});
		btnLogin.setBounds(118, 141, 160, 40);
		desktopPane.add(btnLogin);
		
		
		final JButton btnQ = new JButton("q");
		btnQ.setFocusable(false);
		btnQ.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnQ.setBounds(310, 210, 50, 50);
		desktopPane.add(btnQ);
		
		final JButton btnW = new JButton("w");
		btnW.setFocusable(false);
		btnW.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnW.setBounds(372, 210, 50, 50);
		desktopPane.add(btnW);
		
		final JButton btnE = new JButton("e");
		btnE.setFocusable(false);
		btnE.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnE.setBounds(434, 210, 50, 50);
		desktopPane.add(btnE);
		
		final JButton btnR = new JButton("r");
		btnR.setFocusable(false);
		btnR.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);

			}
		});
		btnR.setBounds(496, 210, 50, 50);
		desktopPane.add(btnR);
		

		
		final JButton btnT = new JButton("t");
		btnT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnT.setFocusable(false);
		btnT.setBounds(558, 210, 50, 50);
		desktopPane.add(btnT);
		
		final JButton btnZ = new JButton("z");
		btnZ.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnZ.setFocusable(false);
		btnZ.setBounds(620, 210, 50, 50);
		desktopPane.add(btnZ);
		
		final JButton btnU = new JButton("u");
		btnU.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnU.setFocusable(false);
		btnU.setBounds(682, 210, 50, 50);
		desktopPane.add(btnU);
		
		final JButton btnI = new JButton("i");
		btnI.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnI.setFocusable(false);
		btnI.setBounds(744, 210, 50, 50);
		desktopPane.add(btnI);
		
		final JButton btnO = new JButton("o");
		btnO.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnO.setFocusable(false);
		btnO.setBounds(806, 210, 50, 50);
		desktopPane.add(btnO);
		
		final JButton btnP = new JButton("p");
		btnP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnP.setFocusable(false);
		btnP.setBounds(868, 210, 50, 50);
		desktopPane.add(btnP);
		
		final JButton btnA = new JButton("a");
		btnA.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnA.setFocusable(false);
		btnA.setBounds(320, 272, 50, 50);
		desktopPane.add(btnA);
		
		final JButton btnS = new JButton("s");
		btnS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnS.setFocusable(false);
		btnS.setBounds(382, 272, 50, 50);
		desktopPane.add(btnS);
		
		final JButton btnD = new JButton("d");
		btnD.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnD.setFocusable(false);
		btnD.setBounds(444, 272, 50, 50);
		desktopPane.add(btnD);
		
		final JButton btnF = new JButton("f");
		btnF.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnF.setFocusable(false);
		btnF.setBounds(506, 272, 50, 50);
		desktopPane.add(btnF);
		
		final JButton btnG = new JButton("g");
		btnG.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnG.setFocusable(false);
		btnG.setBounds(568, 272, 50, 50);
		desktopPane.add(btnG);
		
		final JButton btnH = new JButton("h");
		btnH.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnH.setFocusable(false);
		btnH.setBounds(630, 272, 50, 50);
		desktopPane.add(btnH);
		
		final JButton btnJ = new JButton("j");
		btnJ.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnJ.setFocusable(false);
		btnJ.setBounds(692, 272, 50, 50);
		desktopPane.add(btnJ);
		
		final JButton btnK = new JButton("k");
		btnK.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnK.setFocusable(false);
		btnK.setBounds(754, 272, 50, 50);
		desktopPane.add(btnK);
		
		final JButton btnL = new JButton("l");
		btnL.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnL.setFocusable(false);
		btnL.setBounds(816, 272, 50, 50);
		desktopPane.add(btnL);
		
		final JButton btnY = new JButton("y");
		btnY.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnY.setFocusable(false);
		btnY.setBounds(330, 334, 50, 50);
		desktopPane.add(btnY);
		
		final JButton btnX = new JButton("x");
		btnX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnX.setFocusable(false);
		btnX.setBounds(392, 334, 50, 50);
		desktopPane.add(btnX);
		
		final JButton btnC = new JButton("c");
		btnC.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnC.setFocusable(false);
		btnC.setBounds(454, 334, 50, 50);
		desktopPane.add(btnC);
		
		final JButton btnV = new JButton("v");
		btnV.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnV.setFocusable(false);
		btnV.setBounds(516, 334, 50, 50);
		desktopPane.add(btnV);
		
		final JButton btnB = new JButton("b");
		btnB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnB.setFocusable(false);
		btnB.setBounds(578, 334, 50, 50);
		desktopPane.add(btnB);
		
		final JButton btnN = new JButton("n");
		btnN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnN.setFocusable(false);
		btnN.setBounds(640, 334, 50, 50);
		desktopPane.add(btnN);
		
		final JButton btnM = new JButton("m");
		btnM.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnM.setFocusable(false);
		btnM.setBounds(702, 334, 50, 50);
		desktopPane.add(btnM);
		
		final JButton btnDot = new JButton(".");
		btnDot.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnDot.setFocusable(false);
		btnDot.setBounds(764, 334, 50, 50);
		desktopPane.add(btnDot);
		
		final JButton btn7 = new JButton("7");
		btn7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btn7.setFocusable(false);
		btn7.setBounds(943, 210, 50, 60);
		desktopPane.add(btn7);
		
		final JButton btn8 = new JButton("8");
		btn8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btn8.setFocusable(false);
		btn8.setBounds(1005, 210, 50, 60);
		desktopPane.add(btn8);
		
		final JButton btn9 = new JButton("9");
		btn9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btn9.setFocusable(false);
		btn9.setBounds(1067, 210, 50, 60);
		desktopPane.add(btn9);
		
		final JButton btn4 = new JButton("4");
		btn4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btn4.setFocusable(false);
		btn4.setBounds(943, 282, 50, 60);
		desktopPane.add(btn4);
		
		final JButton btn5 = new JButton("5");
		btn5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btn5.setFocusable(false);
		btn5.setBounds(1005, 282, 50, 60);
		desktopPane.add(btn5);
		
		final JButton btn6 = new JButton("6");
		btn6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btn6.setFocusable(false);
		btn6.setBounds(1067, 282, 50, 60);
		desktopPane.add(btn6);
		
		final JButton btn1 = new JButton("1");
		btn1.setFocusable(false);
		btn1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btn1.setBounds(943, 354, 50, 60);
		desktopPane.add(btn1);
		
		final JButton btn2 = new JButton("2");
		btn2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btn2.setFocusable(false);
		btn2.setBounds(1005, 354, 50, 60);
		desktopPane.add(btn2);
		
		final JButton btn3 = new JButton("3");
		btn3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btn3.setFocusable(false);
		btn3.setBounds(1067, 354, 50, 60);
		desktopPane.add(btn3);
		
		final JButton btn0 = new JButton("0");
		btn0.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btn0.setFocusable(false);
		btn0.setBounds(943, 421, 174, 25);
		desktopPane.add(btn0);
		
		JButton btnEnter = new JButton("ENTER");
		btnEnter.setFocusable(false);
		btnEnter.setBounds(826, 334, 92, 50);
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				final String Username = txtUname.getText();
				@SuppressWarnings("deprecation")
				final String Passwd = pwdPasswd.getText();
				
				Runnable r = new Runnable() {
			         public void run() {
			        	networkLibrary.LoginToNetwork("heartways.no-ip.org", 3000, Username, Passwd);//"luka.celic", "lukaaa");
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
				DiaplatMain main = new DiaplatMain(Username, Passwd, networkLibrary);
				main.setVisible(true);
				dispose();
			}
		});
		desktopPane.add(btnEnter);
		
		final JButton btnSpace = new JButton(" ");
		btnSpace.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnSpace.setFocusable(false);
		btnSpace.setBounds(418, 396, 422, 50);
		desktopPane.add(btnSpace);
		
		final JButton btnDash = new JButton("-");
		btnDash.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ReadAndWriteKeyboardInput(e);
			}
		});
		btnDash.setBounds(852, 396, 66, 50);
		desktopPane.add(btnDash);
		
		JButton btnDel = new JButton("");
		btnDel.setIcon(new ImageIcon(cl.getResource("del.png")));
		btnDel.setFocusable(false);
		btnDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (who == 0 && U.length() > 0) {
					position = txtUname.getCaretPosition();
					U=U.substring(0, position-1) + U.substring(position, U.length());
					txtUname.setText(U);
					txtUname.setCaretPosition(position-1);
				}
				else if (who == 1 && P.length() > 0) {
					position = pwdPasswd.getCaretPosition();
					P=P.substring(0, position-1) + P.substring(position, P.length());
					pwdPasswd.setText(P);
					pwdPasswd.setCaretPosition(position-1);
				}
			}
		});
		btnDel.setBounds(878, 272, 40, 50);
		desktopPane.add(btnDel);
		
		final JButton btnSpecchar = new JButton("");
		btnSpecchar.setIcon(new ImageIcon(cl.getResource("shift.png")));
		btnSpecchar.setFocusable(false);
		btnSpecchar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnSpecchar.getIcon().toString().substring(37, btnSpecchar.getIcon().toString().length()).equals("shift.png")) {
					btnSpecchar.setIcon(new ImageIcon(cl.getResource("shiftDown.png")));
					btnDot.setText(",");
					btnDash.setText("_");
					btnA.setText("A");
					btnB.setText("B");
					btnC.setText("C");
					btnD.setText("D");
					btnE.setText("E");
					btnF.setText("F");
					btnG.setText("G");
					btnH.setText("H");
					btnI.setText("I");
					btnJ.setText("J");
					btnK.setText("K");
					btnL.setText("L");
					btnM.setText("M");
					btnN.setText("N");
					btnO.setText("O");
					btnP.setText("P");
					btnR.setText("R");
					btnS.setText("S");
					btnT.setText("T");
					btnU.setText("U");
					btnV.setText("V");
					btnZ.setText("Z");
					btnQ.setText("Q");
					btnX.setText("X");
					btnY.setText("Y");
					btnW.setText("W");
				}
				else if (btnSpecchar.getIcon().toString().substring(37, btnSpecchar.getIcon().toString().length()).equals("shiftDown.png")){
					btnSpecchar.setIcon(new ImageIcon(cl.getResource("shift.png")));
					btnDot.setText(".");
					btnDash.setText("-");
					btnA.setText("a");
					btnB.setText("b");
					btnC.setText("c");
					btnD.setText("d");
					btnE.setText("e");
					btnF.setText("f");
					btnG.setText("g");
					btnH.setText("h");
					btnI.setText("i");
					btnJ.setText("j");
					btnK.setText("k");
					btnL.setText("l");
					btnM.setText("m");
					btnN.setText("n");
					btnO.setText("o");
					btnP.setText("p");
					btnR.setText("r");
					btnS.setText("s");
					btnT.setText("t");
					btnU.setText("u");
					btnV.setText("v");
					btnZ.setText("z");
					btnQ.setText("q");
					btnX.setText("x");
					btnY.setText("y");
					btnW.setText("w");
				}
			}
		});
		btnSpecchar.setBounds(340, 396, 66, 50);
		desktopPane.add(btnSpecchar);
		

		
		desktopPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtUname, pwdPasswd, btnLogin}));

	}
	
	
	
	private void ReadAndWriteKeyboardInput(MouseEvent e) {
		if (who == 0) {
			position = txtUname.getCaretPosition();
			JButton btn = (JButton) e.getSource();
			U=U.substring(0,position) + btn.getText() + U.substring(position,U.length());
			txtUname.setText(U);
			txtUname.setCaretPosition(position + 1);
		}
		else if (who == 1) {
			position = pwdPasswd.getCaretPosition();
			JButton btn = (JButton) e.getSource();
			P=P.substring(0,position) + btn.getText() + P.substring(position,P.length());
			pwdPasswd.setText(P);
			pwdPasswd.setCaretPosition(position + 1);
		}
		
		
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		//System.out.println(arg0.getSource().toString().substring(12,14));
		if (arg0.getSource().toString().substring(12,14).equals("JT")) {
			//System.out.println("JT");
			who = 0;
		}
		else if (arg0.getSource().toString().substring(12,14).equals("JP")) {
			//System.out.println("JP");
			who = 1;
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {

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
