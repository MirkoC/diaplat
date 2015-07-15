import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JLabel;


public class OldImages extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public OldImages(String Username) {
		final String uname = Username;
		final String sysUname = System.getProperty("user.name");
		final ClassLoader cl = this.getClass().getClassLoader();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int width = (int) screenSize.getWidth() - 638 - 638/2;
	    final int height = (int) screenSize.getHeight()- 876/4;
		setBounds(100, 100, width , height);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(0, 0, 128));
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setUndecorated(true); 
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			final JLabel lblNewLabel = new JLabel("New label");
			contentPanel.add(lblNewLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(176, 196, 222));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
				});
				{
					JLabel Date = new JLabel("14.11.1988.20:50:12");
					buttonPane.add(Date);
				}
				{
					JButton btnPrev = new JButton("Prev");
					btnPrev.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							//lblNewLabel.setIcon(new ImageIcon(cl.getResource("noge.png")));
						
						}
					});
					buttonPane.add(btnPrev);
				}
				{
					JButton btnNext = new JButton("Next");
					btnNext.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
						}
					});
					buttonPane.add(btnNext);
				}
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
