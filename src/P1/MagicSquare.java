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
			System.out.print("nΪ�������޷�����MagicSquare��������������\n");
			return false;
		}
		else if(n%2 == 0)
		{
			System.out.print("nΪż�����޷�����MagicSquare��������������\n");
			return false;
		}

		int magic[][] = new int[n][n];
		int row = 0, col = n / 2, i, j, square = n * n;//��һ��Ҫ�����λ���ڵ�һ�е��м�λ��

		for (i = 1; i <= square; i++) {
			magic[row][col] = i;//��ǰλ�ø�ֵ
			if (i % n == 0)
				row++;
			else {//���������
				if (row == 0)//������ڴ�����ǵ�һ��
					row = n - 1;//Ҫ�������һλ�����������һ��
				else//������ǵ�һ��
					row--;//Ҫ�������һ��λ��������λ�õ���һ�У������µ��ϴ���
				if (col == (n - 1))//������ڴ���������һ��
					col = 0;//Ҫ�������һλ�������ڵ�һ��
				else
					col++;//Ҫ�������һλ��������λ�õ���һ�У����������Ҵ���
			}
		}
/* �������������������ѡȡ��һ�е��м�λ����Ϊ���
 * ��������Ϸ���λ����д�������������
 * �������Ϸ���Եʱ�����·��Ķ�Ӧλ�ü�������
 * �����ҷ���Եʱ��������
 * ����������������
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
		
		try {//��ȡ��һ���ַ�
			ch = fr.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(ch!=-1)//��ȡ��һ�У�ȷ���ж�����
		{
			if(ch>47 && ch<58)//�������֣�������
			{
				num = num*10+ch-48;
				if (flag == false) flag = true;
			}
			else if(ch == 9)//����/t���������ֽ���
			{
				nums[row][colunms] = num;//���ִ洢��������
				num = 0;
				colunms++;//��һ��
				flag = false;
			}
			else if(ch == 13 || ch == 10)//����ĩβ�����н���
			{
				if (flag == true)//ǰһ������û���ȥ
				{
					nums[row][colunms] = num;//���ִ洢��������
					num = 0;
					colunms++;//��һ��
					flag = false;
				}
				StandardColunms = colunms;//��¼��һ�ж����У����ں����ж�
				colunms = 0;
				row++;//׼����һ�ж�ȡ
				do 
				{
					try {//��ȡ��һ���ַ�
						ch = fr.read();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}while(ch<=47 || ch >=58);
		
				break;
			}
			else 
			{
				System.out.print("��ȡ�������ַ������ܴ��ڸ������Ǿ���\n");
				return false;//���������ַ���������Ҫ��
			}
			try {//��ȡ��һ���ַ�
				ch = fr.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		while(ch!=-1)//��ȡ֮������
		{
			if(ch>47 && ch<58)//�������֣�������
			{
				num = num*10+ch-48;
				if (flag == false) flag = true;
			}
			else if(ch == 9)//����/t���������ֽ���
			{
				nums[row][colunms] = num;//���ִ洢��������
				num = 0;
				colunms++;//��һ��
				flag = false;
			}
			else if(ch == 13 || ch == 10)//����ĩβ�����н���
			{

				if (flag == true)//ǰһ������û���ȥ
				{
					nums[row][colunms] = num;//���ִ洢��������
					num = 0;
					colunms++;//��һ��
					flag = false;
				}
				if(StandardColunms != colunms) 
				{
					System.out.print("������ȫ��ȣ����Ƿ���\n");
					return false;//��������Ӧ��������Ҫ��
				}
				colunms = 0;
				row++;//׼����һ�ж�ȡ
				do 
				{
					try {//��ȡ��һ���ַ�
						ch = fr.read();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(ch == -1)//��һ��û��������
					{
						row--;
						break;//����һ�У��˳�ѭ��
					}
				}while(ch <= 47 || ch >= 58);//��ȡ����һ�п�ͷ����
				continue;
			}
			else 
			{
				System.out.print("��ȡ�������ַ����������ֲ�������������������֮�䲢��ʹ��\\t�ָ���ǺϹ淽��\n");
				return false;//���������ַ���������Ҫ��
			}
			
			try {//��ȡ��һ���ַ�
				ch = fr.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (flag == true)//ǰһ������û���ȥ
		{
			nums[row][colunms] = num;//���ִ洢��������
			num = 0;
			colunms++;//��һ��
			flag = false;
		}
		
		StandardRow = row + 1;
		if(StandardRow != StandardColunms) 
		{
			System.out.print("��������������ȣ����Ƿ���\n");
			return false;//�������������ȣ�������Ҫ��
		}
		
		int Sum = 0,sum1 = 0,sum2 = 0,sum3 = 0,StandardSum = 0;
		
		//�����һ�еĺͣ�����֮����ж�
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
		
		//�ж�ÿ�еĺ��Ƿ����
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
			Sum = sum1+sum2+sum3;//ÿ�����
			if(Sum != StandardSum) 
			{
				System.out.print("����֮�Ͳ���ȣ�������Ҫ��\n");
				return false;//�Ͳ��ȣ�������Ҫ��
			}
			else 
			{
				Sum = 0;
				sum1 = 0;
				sum2 = 0;
				sum3 = 0;
			}
		}
		
		//�ж�ÿһ�еĺ��Ƿ����
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
			Sum = sum1+sum2+sum3;//ÿ�����
			if(Sum != StandardSum) 
			{
				System.out.print("����֮�Ͳ���ȣ�������Ҫ��\n");
				return false;//�Ͳ��ȣ�������Ҫ��
			}
			else 
			{
				Sum = 0;
				sum1 = 0;
				sum2 = 0;
				sum3 = 0;
			}
		}
		
		//�ж϶Խ��ߵĺ��Ƿ����
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
			System.out.print("�Խ���֮�Ͳ���ȣ�������Ҫ��\n");
			return false;//�Ͳ��ȣ�������Ҫ��
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
			System.out.print("�Խ���֮�Ͳ���ȣ�������Ҫ��\n");
			return false;//�Ͳ��ȣ�������Ҫ��
		}
		
		System.out.print("��������Ҫ����MagicSquare��\n");
		return true;//��������Ҫ�󣬷���Ҫ��
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
