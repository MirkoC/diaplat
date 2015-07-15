import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageInfo {

	public BufferedImage img;
	public BufferedImage img2;
	public BufferedImage img3;
	public int[][] win;
	public double[] radious;
	public int rgb;
	public int a;
	public String[] coor;
	int[] x_win;
    int[] y_win;
    int dimensions = 35;
    int n_w;
    int n_h;

	public ImageInfo() {
		
		try {
			img = ImageIO.read(new File("/home/cex/luka.celic/converted.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Throws IOException
		try {
			img2 = ImageIO.read(new File("/home/cex/luka.celic/converted2.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 
		
		Color blue = new Color(0, 0, 255); // Color blue
		rgb=blue.getRGB();


		int br = 0;
 		n_w = img.getWidth() / dimensions;
 		n_h = img.getHeight() / dimensions;
		//int color;
		
		x_win = new int[n_h+1];
		y_win = new int[n_h+1];
		
 		for (int x = 0; x<img.getWidth(); x++) {
 			for (int y = 0; y<img.getHeight(); y++) {
 				int argb0 = img2.getRGB(x, y);
 				int argb1 = img.getRGB(x, y);

 				// Here the 'b' stands for 'blue' as well
 				// as for 'brightness' :-)
 				int b0 = argb0 & 0xFF;
 				int b1 = argb1 & 0xFF;
 				int bDiff = Math.abs(b1 - b0);
 				int diff = (255 << 24) | (bDiff << 16) | (bDiff << 8) | bDiff;
 				img.setRGB(x, y, diff);
 			}
 		
 		}
 		
 		File outputfile = new File("/home/cex/luka.celic/converted3.jpg");
        try {
			ImageIO.write(img, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
        
        try {
			img3 = ImageIO.read(new File("/home/cex/luka.celic/converted3.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 
        
        
        //System.out.println(n_w + "," + n_h);
        win = new int[n_w][n_h];
        
        coor = new String[n_w*n_h];
        a = 0;
 		for (int j = 0; j < n_w; j++) {
 			for (int i = 0; i < n_h; i++) {
				for (int x= 0+j*dimensions; x<dimensions+j*dimensions;x++){
					for (int y = 0+i*dimensions; y<dimensions+i*dimensions;y++){
						final Color cxy = new Color(img3.getRGB(x, y), true);
						if (cxy.getRed() > 10 && cxy.getGreen() > 10 && cxy.getBlue() > 10){
							br++;

						}
					}
				}
				//DrawWindow(j*dimensions, i*dimensions, dimensions+streach, streach);
				if (br>20) {
					//DrawWindow(j*dimensions, i*dimensions, dimensions);
					//System.out.println(i+ ", " + j);
					win[j][i] = 1;
					br = 0;
				}
				else{
					win[j][i] = 0;
				}
 			}
 			
 		}	
 		
 		//DrawWindow2(7*50,25*50,(9)*52,26*52);
 		
 		/*for (int x = 0; x < n_w; x++){
 			for (int y = 0; y < n_h; y++){
 				System.out.print(win[x][y]);
 			}
 			System.out.print("\n");
 		}*/

 		
 		for (int x = 0; x < n_w; x++){
 			for (int y = 0; y < n_h; y++){
 				if (win[x][y] == 1) {
 					//System.out.println(x + "," + y);
 					ConveyanceSearch(x,y);
 				}
 			}
 		}

	}

	private void ConveyanceSearch(int x, int y) {
		//System.out.println(x+","+y);
		//System.out.println(a);
		x_win[a] = x;
		y_win[a++] = y;
		
		//System.out.println(x_win[a-1]+","+y_win[a-1]);
		if (x !=0 && x != n_w && y!=0 && y!=n_h) {
			if (win[x-1][y] == 1) {
				//radious[a++] = Math.sqrt(Math.pow(x-1,2)+Math.pow(y, 2));
				win[x][y] = 0;
				ConveyanceSearch(x-1,y);
			}
			else if (win[x-1][y-1] == 1 ) {
				//radious[a++] = Math.sqrt(Math.pow(x-1,2)+Math.pow(y-1, 2));
				win[x][y] = 0;
				ConveyanceSearch(x-1, y-1);
			}
			else if (win[x][y-1] == 1 && y !=0) {
				//radious[a++] = Math.sqrt(Math.pow(x,2)+Math.pow(y-1, 2));
				win[x][y] = 0;
				ConveyanceSearch(x, y-1);
			}
			else if (win[x+1][y-1] == 1) {
				//radious[a++] = Math.sqrt(Math.pow(x+1,2)+Math.pow(y-1, 2));
				win[x][y] = 0;
				ConveyanceSearch(x+1, y-1);
			}
			else if (win[x+1][y] == 1) {
				//radious[a++] = Math.sqrt(Math.pow(x+1,2)+Math.pow(y, 2));
				win[x][y] = 0;
				ConveyanceSearch(x+1, y);
			}
			else if (win[x+1][y+1] == 1) {
				//radious[a++] = Math.sqrt(Math.pow(x+1,2)+Math.pow(y+1, 2));
				win[x][y] = 0;
				ConveyanceSearch(x+1, y+1);
			}
			else if (win[x][y+1] == 1) {
				//radious[a++] = Math.sqrt(Math.pow(x,2)+Math.pow(y+1, 2));
				win[x][y] = 0;
				ConveyanceSearch(x, y+1);
			}
			else if (win[x-1][y+1] == 1) {
				//radious[a++] = Math.sqrt(Math.pow(x-1,2)+Math.pow(y+1, 2));
				win[x][y] = 0;
				ConveyanceSearch(x-1, y+1);
			}
			else {
				win[x][y] = 0;
				
				
				int no = 0;
				
				
				System.out.println(a);
				System.out.println("==============");
				
				if (a == 2){
					if (x_win[1] == x_win[0]) {
						x_win[1] = x_win[0] + 1;
						System.out.println("DA2x");
					}
					if (y_win[1] == y_win[0]) {
						y_win[1] = y_win[0] + 1;
						System.out.println("DA2y");
					}			
				}
				
				if (a == 1) {
					System.out.println("DA1");
					x_win[1] = x_win[0] + 1;
					y_win[1] = y_win[0] + 1;
					no = 1;
					
				}
				
				
				
				int x_c_min = x_win[0];
				int y_c_min = y_win[0];
				int x_c_max = x_win[0];
				int y_c_max = y_win[0];
				
				for (int i = 1; i<x_win.length; i++) {
					if (x_win[i] < x_c_min && x_win[i] != 0) {
						x_c_min = x_win[i];
					}
				}
				
				for (int i = 1; i<y_win.length; i++) {
					if (y_win[i] < y_c_min && y_win[i] != 0) {
						y_c_min = y_win[i];
					}
				}
				for (int i = 1; i<x_win.length; i++) {
					if (x_win[i] > x_c_max) {
						x_c_max = x_win[i];
					}
				}
				
				for (int i = 1; i<y_win.length; i++) {
					if (y_win[i] > y_c_max) {
						y_c_max = y_win[i];
					}
				}
				
				//System.out.println(x_c_min + "," + y_c_min +"--" +x_c_max +"," + y_c_max);
	
				DrawWindow2(x_c_min*dimensions, y_c_min*dimensions, x_c_max*(dimensions+2-no), y_c_max*(dimensions+2-no) );
				a=0;
				x_win = new int[n_h+1];
				y_win = new int [n_h+1];
				x_c_min = 0;
				y_c_min = 0;
				x_c_max = 0;
				y_c_max = 0;
			}
		}
	}
	
	public void DrawWindow2(int x_min, int y_min, int x_max, int y_max){
		//System.out.println(x_min + "," + y_min +"--" +x_max +"," + y_max);
		for (int x =x_min; x < x_max; x++){
			for (int y = y_min; y < y_max; y++){
				
				img2.setRGB(x_min, y, rgb);
				img2.setRGB(x_max, y, rgb);
				img2.setRGB(x, y_min, rgb);
				img2.setRGB(x, y_max, rgb);
			}
		}
	
		
		File outputfile = new File("/home/cex/luka.celic/converted4.jpg");
        try {
			ImageIO.write(img2, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void DrawWindow(int offset_x, int offset_y, int dimensions){
		for (int x =offset_x; x < dimensions+offset_x; x++){
			for (int y = offset_y; y < dimensions+offset_y; y++){
				
				img2.setRGB(0+offset_x, y, rgb);
				img2.setRGB(dimensions+offset_x, y, rgb);
				img2.setRGB(x, 0+offset_y, rgb);
				img2.setRGB(x, dimensions+offset_y, rgb);
			}
		}
			

	
		
		File outputfile = new File("/home/cex/luka.celic/converted5.jpg");
        try {
			ImageIO.write(img2, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
