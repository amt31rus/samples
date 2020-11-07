package perccards;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class perccards1 {

	public static void main(String[] args) {

		String s0 = "c:\\imgs"; // путь по умолчанию
		for (String s: args)
			{
				System.out.println(s);
				s0 = s;
			};
			
		System.out.println(s0);
		String s1 = s0.replace('\\','/');
//		System.out.println (s1);
// матрицы количества темных пикселей мастей и достоинств построчно	строка длиной 30, кол-во строк -25	
		String infilename = null;
		String indir=null;
//		File dir = new File("c:/imgs"); //path указывает на директорию
		File dir = new File(s1); //path указывает на директорию
		for ( File file : dir.listFiles() ){
		    if ( file.isFile() )
		    	indir= file.getPath();
		    	infilename = file.getName();
		    	System.out.print(infilename+" ");
		        CheckCards(indir);
		        System.out.println();
	}
} // public static void main(String[] args)
	
	static void CheckCards(String infilename){
		
//		File file = new File("c:\\imgs\\"+infilename);
		File file = new File(infilename);

		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage result = new BufferedImage (30,25, image.getType());
		for (int cardnum = 1; cardnum < 6; cardnum++) {
			String outfilename = "c:\\results\\"+infilename+"_"+cardnum+"_value.png";
			point Start1  =  SetPosition (cardnum, true);
			Check30x25(image, outfilename, Start1.x, Start1.y);
			outfilename = "c:\\results\\"+infilename+"_"+cardnum+"_suit.png";
			point Start2  =  SetPosition (cardnum, false);
			Check30x25(image, outfilename, Start2.x, Start2.y);
		} // for (int cardnum = 1; cardnum < 6; cardnum++)
	} // static void CheckCards(String infilename)
	
	static point SetPosition(int Numcard, boolean pictop) {
		point Startpoint = new point();
		Startpoint.x = 148 + (Numcard-1)*71;
		if (pictop) {
			 Startpoint.y = 591;
		} else {
			Startpoint.y = 616;
		}
		return  Startpoint;
	}//static void SetPosition()
	
	static Color ColorCorrection (BufferedImage corimage, int PosX, int PosY) {
		Color color = new Color(corimage.getRGB(PosX, PosY));
		int blue = color.getBlue();
		int red = color.getRed();
		int green = color.getGreen();
		if (blue > 90 & red > 90 & green > 90) 
		{
			blue = 255;
			red = 255;
			green = 255;
		} else
		{
			blue = 0;
			red = 0;
			green = 0;
		}
		Color newColor = new Color (red,green,blue);
		return newColor;
	} // static void ColorCorrection ()

	static void Check30x25(BufferedImage checkimage, String outfilename, int StartX, int StartY) {
		// стартовые координаты фрагмента, номер карты в ряду, 
		// верх или низ /масть или достоинство читаем
		BufferedImage result = new BufferedImage (30,25, checkimage.getType());
		int[] Cardmatr = new int[25];
		int sumstr = 0;
		String stringarray= "{";
		for (int y = 0; y< 25; y++) {
			sumstr = 0;
			for (int x = 0; x < 30; x++) {
				int x1 = x + StartX;
				int y1 = y + StartY;
				Color correctedColor = ColorCorrection (checkimage, x1, y1);
				result.setRGB(x, y, correctedColor.getRGB());
				int blue1 = correctedColor.getBlue();
				int red1 = correctedColor.getRed();
				int green1 = correctedColor.getGreen();
				if (blue1+red1+green1 == 0 ){ sumstr++;	}
			} // for (int x = 0; x < 30; x++)
			Cardmatr[y] = sumstr;
			stringarray = stringarray + sumstr +", ";
		} // for (int y = 0; y< 25; y++)
//		System.out.println (stringarray);
//		File output = new File (outfilename);
//		try {
//			ImageIO.write(result, "png",output);
//		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		comparematrix(Cardmatr);
	} // Check30x25(int StartX, int StartY)
	
	static void comparematrix(int[] a) {
		String[] valueletter = {"A","K","Q","J","10","9","8","7","6","5","4","3","2"," ","c","d","h","s"};
		int[][] valuematrix = {
				//{0, 0, 4, 4, 5, 6, 6, 6, 5, 7, 6, 7, 6, 6, 6, 15, 16, 16, 7, 6, 6, 7, 7, 7, 0},
				{0, 4, 4, 6, 6, 7, 6, 7, 7, 6, 8, 6, 8, 6, 15, 16, 16, 18, 6, 8, 6, 8, 8, 0, 0},
				{0, 8, 8, 7, 7, 7, 7, 7, 7, 7, 7, 9, 10, 10, 9, 8, 8, 7, 7, 7, 8, 7, 8, 0, 0},
				{0, 7, 11, 15, 12, 9, 8, 8, 7, 6, 6, 8, 8, 6, 8, 12, 13, 11, 11, 13, 18, 14, 8, 0, 0},
				{0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 6, 9, 11, 9, 5, 0, 0},
				{0, 8, 15, 20, 15, 11, 10, 11, 9, 10, 10, 10, 10, 10, 10, 9, 11, 10, 11, 12, 15, 13, 9, 0, 0},
				{0, 6, 10, 12, 9, 7, 6, 7, 7, 7, 8, 10, 14, 12, 8, 3, 3, 4, 5, 7, 13, 10, 7, 0, 0},
				{0, 6, 10, 12, 8, 7, 6, 5, 7, 6, 8, 9, 11, 8, 8, 6, 7, 7, 6, 8, 13, 11, 7, 0, 0},
				{0, 15, 15, 15, 4, 3, 4, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0},
				{0, 7, 11, 13, 7, 4, 4, 4, 3, 8, 12, 15, 12, 9, 8, 7, 7, 7, 8, 9, 12, 10, 7, 0, 0},
				{0, 13, 13, 13, 3, 3, 3, 3, 3, 8, 12, 13, 8, 4, 3, 4, 4, 3, 6, 10, 13, 11, 7, 0, 0},
				{0, 4, 5, 5, 6, 7, 8, 8, 8, 7, 7, 7, 8, 8, 8, 18, 18, 18, 4, 4, 4, 4, 4, 0, 0},
				{0, 14, 14, 14, 5, 4, 4, 4, 4, 4, 5, 8, 9, 5, 4, 3, 4, 4, 7, 11, 13, 11, 7, 0, 0},
				{0, 6, 10, 12, 10, 8, 6, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 5, 4, 4, 16, 15, 16, 0, 0},
				{30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30},
				{0, 0, 0, 0, 3, 5, 6, 6, 5, 5, 11, 12, 13, 12, 12, 6, 2, 4, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 2, 4, 6, 8, 10, 12, 12, 10, 8, 6, 4, 4, 2, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 8, 13, 13, 13, 13, 13, 11, 9, 7, 5, 3, 1, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 2, 4, 6, 8, 10, 12, 12, 14, 14, 14, 11, 8, 2, 2, 0, 0, 0, 0, 0, 0, 0}
		};
		int diff = 0;
		int mindiff = 750;
		int indexmindiff = 0;
		for (int i = 0; i < 18; i++) {
			diff = 0;
			for (int j = 0; j< 25; j++) {
				diff = diff + Math.abs(a[j] - valuematrix[i][j] );
			} // for (int j = 0; j< 25; j++)
			if (diff < mindiff) {
				mindiff = diff;
				indexmindiff = i;
			} // if (diff < mindiff)
		} //for (int i = 0; i < 13; i++)
		System.out.print (valueletter[indexmindiff]);
	}
} // public class perccards1

class point{
	public int x,y;
}; // class point
