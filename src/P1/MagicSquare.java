package P1;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MagicSquare {
	public static final int MAX = 200;
	
	public static boolean generateMagicSquare(int n) {
		if(n < 0)
		{
			System.out.print("n为负数，无法生成MagicSquare。请输入奇数！\n");
			return false;
		}
		else if(n%2 == 0)
		{
			System.out.print("n为偶数，无法生成MagicSquare。请输入奇数！\n");
			return false;
		}

		int magic[][] = new int[n][n];
		int row = 0, col = n / 2, i, j, square = n * n;//第一个要处理的位置在第一行的中间位置

		for (i = 1; i <= square; i++) {
			magic[row][col] = i;//当前位置赋值
			if (i % n == 0)
				row++;
			else {//对其他情况
				if (row == 0)//如果现在处理的是第一行
					row = n - 1;//要处理的下一位置设置在最后一行
				else//如果不是第一行
					row--;//要处理的下一个位置在现在位置的上一行，即由下到上处理
				if (col == (n - 1))//如果现在处理的是最后一列
					col = 0;//要处理的下一位置设置在第一列
				else
					col++;//要处理的下一位置在现在位置的下一列，即从左向右处理
			}
		}
/* 整体来讲，这个函数是选取第一行的中间位置作为起点
 * 逐次向右上方的位置填写依次增大的数字
 * 当触及上方边缘时，从下方的对应位置继续操作
 * 触及右方边缘时操作类似
 * 最终填满整个方阵
*/
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++)
				System.out.print(magic[i][j] + "\t");
			System.out.println();
		}

		boolean flag = false;
		int[] write = new int [8];
		File file = null;
		FileWriter fw = null;
		try {
			file = new File("./src/P1/txt/6.txt");
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			fw = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			for (int x = 0; x < n; x++)
			{
				for (int y = 0; y < n; y++)
				{
					int z = 0,l = 1;
					write[z] = magic[x][y] % 10;
					for(z = 1; z < 8; z++)
					{
						l *= 10;
						write[z] = (magic[x][y] / l) % 10;
					}
					
					for(z = 7;z >= 0; z--)
					{
						if(write[z] != 0 || flag == true) 
						{
							fw.write(write[z]+48);
							flag = true;
						}
						flag = false;
					}
					fw.write("\t");
				}
				fw.write("\n");
			}
			fw.flush();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
}

	
	static boolean isLegalMAgicSquare(String fileName) {
		FileReader fr = null;
		boolean flag =true;
		int ch = 0,num = 0,row = 0,colunms = 0,StandardRow = 0,StandardColunms = 0;
		int[][] nums = new int[MAX][MAX]; 
		
		try {
			fr = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {//读取第一个字符
			ch = fr.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(ch!=-1)//读取第一行，确定有多少列
		{
			if(ch>47 && ch<58)//读到数字，存起来
			{
				num = num*10+ch-48;
				if (flag == false) flag = true;
			}
			else if(ch == 9)//读到/t，表明数字结束
			{
				nums[row][colunms] = num;//数字存储到数组中
				num = 0;
				colunms++;//加一列
				flag = false;
			}
			else if(ch == 13 || ch == 10)//读到末尾，本行结束
			{
				if (flag == true)//前一个数字没存进去
				{
					nums[row][colunms] = num;//数字存储到数组中
					num = 0;
					colunms++;//加一列
					flag = false;
				}
				StandardColunms = colunms;//记录第一行多少列，用于后续判断
				colunms = 0;
				row++;//准备下一行读取
				do 
				{
					try {//读取下一个字符
						ch = fr.read();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}while(ch<=47 || ch >=58);
		
				break;
			}
			else 
			{
				System.out.print("读取到其他字符，可能存在负数或不是矩阵\n");
				return false;//读到其他字符，不符合要求
			}
			try {//读取下一个字符
				ch = fr.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		while(ch!=-1)//读取之后内容
		{
			if(ch>47 && ch<58)//读到数字，存起来
			{
				num = num*10+ch-48;
				if (flag == false) flag = true;
			}
			else if(ch == 9)//读到/t，表明数字结束
			{
				nums[row][colunms] = num;//数字存储到数组中
				num = 0;
				colunms++;//加一列
				flag = false;
			}
			else if(ch == 13 || ch == 10)//读到末尾，本行结束
			{

				if (flag == true)//前一个数字没存进去
				{
					nums[row][colunms] = num;//数字存储到数组中
					num = 0;
					colunms++;//加一列
					flag = false;
				}
				if(StandardColunms != colunms) 
				{
					System.out.print("列数不全相等，不是方阵\n");
					return false;//列数不对应，不符合要求
				}
				colunms = 0;
				row++;//准备下一行读取
				do 
				{
					try {//读取下一个字符
						ch = fr.read();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(ch == -1)//下一行没有数字了
					{
						row--;
						break;//减少一行，退出循环
					}
				}while(ch <= 47 || ch >= 58);//读取到下一行开头数字
				continue;
			}
			else 
			{
				System.out.print("读取到其他字符，可能数字并非正整数，或者数字之间并非使用\\t分割，不是合规方阵\n");
				return false;//读到其他字符，不符合要求
			}
			
			try {//读取下一个字符
				ch = fr.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (flag == true)//前一个数字没存进去
		{
			nums[row][colunms] = num;//数字存储到数组中
			num = 0;
			colunms++;//加一列
			flag = false;
		}
		
		StandardRow = row + 1;
		if(StandardRow != StandardColunms) 
		{
			System.out.print("列数与行数不相等，不是方阵\n");
			return false;//行数与列数不等，不符合要求
		}
		
		int Sum = 0,sum1 = 0,sum2 = 0,sum3 = 0,StandardSum = 0;
		
		//计算第一行的和，用于之后的判断
		for(colunms = 0; colunms < StandardColunms; colunms+=3)
		{
			sum1 += nums[0][colunms];
			sum2 += nums[0][colunms+1];
			sum3 += nums[0][colunms+2];
		}
		for(; colunms < StandardColunms; colunms++)
			Sum += nums[0][colunms];
		StandardSum = sum1+sum2+sum3;
		sum1 = 0;
		sum2 = 0;
		sum3 = 0;
		
		//判断每行的和是否相等
		for(row = 1; row < StandardRow; row++)
		{
			for(colunms = 0; colunms < StandardColunms; colunms+=3)
			{
				sum1 += nums[row][colunms];
				sum2 += nums[row][colunms+1];
				sum3 += nums[row][colunms+2];
			}
			for(; colunms < StandardColunms; colunms++)
				Sum += nums[row][colunms];
			Sum = sum1+sum2+sum3;//每行求和
			if(Sum != StandardSum) 
			{
				System.out.print("各行之和不相等，不符合要求\n");
				return false;//和不等，不符合要求
			}
			else 
			{
				Sum = 0;
				sum1 = 0;
				sum2 = 0;
				sum3 = 0;
			}
		}
		
		//判断每一列的和是否相等
		for(colunms = 0; colunms < StandardColunms; colunms++)
		{
			for(row = 0; row < StandardRow; row+=3)
			{
				sum1 += nums[row][colunms];
				sum2 += nums[row+1][colunms];
				sum3 += nums[row+2][colunms];
			}
			for(; row < StandardRow; row++)
				Sum += nums[row][colunms];
			Sum = sum1+sum2+sum3;//每列求和
			if(Sum != StandardSum) 
			{
				System.out.print("各列之和不相等，不符合要求\n");
				return false;//和不等，不符合要求
			}
			else 
			{
				Sum = 0;
				sum1 = 0;
				sum2 = 0;
				sum3 = 0;
			}
		}
		
		//判断对角线的和是否相等
		row = 0;
		for(; row < StandardRow; row+=3)
		{
			colunms = row;	
			sum1 += nums[row][colunms];
			sum2 += nums[row+1][colunms+1];
			sum3 += nums[row+2][colunms+2];
		}
		for(; row < StandardRow; row++)
			Sum += nums[row][colunms];
		Sum +=sum1+sum2+sum3;
		if(Sum != StandardSum) 
		{
			System.out.print("对角线之和不相等，不符合要求\n");
			return false;//和不等，不符合要求
		}
		
		Sum = 0;
		sum1 = 0;
		sum2 = 0;
		sum3 = 0;
		
		row = 0;
		for(; row < StandardRow-2; row+=3)
		{
			colunms = row;	
			sum1 += nums[row][StandardColunms-colunms-1];
			sum2 += nums[row+1][StandardColunms-colunms-2];
			sum3 += nums[row+2][StandardColunms-colunms-3];
		}
		for(; row < StandardRow; row++)
		{
			colunms = row;	
			Sum += nums[row][StandardColunms-colunms-1];
		}
		Sum +=sum1+sum2+sum3;
		if(Sum != StandardSum) 
		{
			System.out.print("对角线之和不相等，不符合要求\n");
			return false;//和不等，不符合要求
		}
		
		System.out.print("满足所有要求，是MagicSquare！\n");
		return true;//满足所有要求，符合要求
	}
	
	public static void main(String[] args) {
		boolean result;
		String path_origin = "./src/P1/txt/";
		result = isLegalMAgicSquare(path_origin+"1.txt");
		result = isLegalMAgicSquare(path_origin+"2.txt");
		result = isLegalMAgicSquare(path_origin+"3.txt");
		result = isLegalMAgicSquare(path_origin+"4.txt");
		result = isLegalMAgicSquare(path_origin+"5.txt");
		result = generateMagicSquare(3);
		if(result = true)	
			result = isLegalMAgicSquare(path_origin+"6.txt");
	}
}
