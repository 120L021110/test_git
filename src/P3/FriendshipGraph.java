package P3;

public class FriendshipGraph {
	public Vertex lastperson;//�������һ����
	public Vertex firstperson;//�����һ����
	public class Vertex
	{
		String NameString;//��ŵ�ǰ������
		int friendnum = 0;//�������˵���������
		Friendship friendship;
		Vertex nextperson;//ָ����һ����
		boolean visited = false;
	}
	public class Friendship//�������˵Ĺ�ϵ
	{
		String friendsname;
		Vertex itsfriend;//ָ������˵������ڹ�ϵ���е�λ��
		Friendship nextfriend;
	}
	
	public FriendshipGraph()
	{
		this.lastperson = null;
		this.firstperson = null;
	}
	
	public boolean searchVertex(Person name)//�������
	{
		if(this.firstperson == null && this.lastperson == null)//���л�û����
			return false;
		else
		{
			Vertex temp = this.firstperson;
			do
			{
				if(temp.NameString  == name.name)//�����Ѿ��������
				{
					System.out.println("��ϵ���д�������ˣ�");
					return true;
				}
				temp = temp.nextperson;
			}while(temp != null);
			//û���ҵ������
			return false;
		}
	}
	
	public boolean searchFriendship(Person name1,Person name2)//Ѱ��ĳ���˵�ĳ������
	{
		if(this.firstperson == null && this.lastperson == null)//���л�û����
			return false;
		else
		{
			Vertex temp = this.firstperson;
			Friendship ship = null;
			do
			{
				if(temp.NameString  == name1.name)//�����������
				{
					ship = temp.friendship;
					break;
				}
				temp = temp.nextperson;
			}while(temp != null);
			
			if(ship == null)//û���ҵ������
				return false;
			else
			{
				do
				{
					if(ship.friendsname == name2.name)//���������
						return true;
					else ship = ship.nextfriend;
				}while(ship != null);
				return false;
			}
		}
	}
	
	public boolean addVertex(Person name)//����һ����
	{
		Vertex newperson = new Vertex();
		newperson.NameString = name.name;
		newperson.friendship = null;
		newperson.nextperson = null;
		
		if(this.firstperson == null && this.lastperson == null)//���л�û����
		{
			this.firstperson = newperson;
			this.lastperson = newperson;
			return true;
		}
		else//�Ѿ�������
		{
			Vertex temp = this.firstperson;
			do
			{
				if(temp.NameString  == newperson.NameString)//�����Ѿ��������
				{
					System.out.println("��ϵ���д���������ͬ���ˣ�");
					System.exit(0);
				}
				temp = temp.nextperson;
			}while(temp != null);
			//û���ҵ������
			this.lastperson.nextperson  = newperson;
			this.lastperson = newperson;
			return true;
		}
	}
	
	public boolean addEdge(Person name1, Person name2)//����������֮��Ĺ�ϵ
	{
		if(this.firstperson == null)
		{
			System.out.println("��ϵ����û���ˣ�");
			return false;
		}
		else
		{
			
			Friendship ship1 = null, ship2 = null;
			Vertex tempPerson1 = this.firstperson;
			do{//�ҵ�һ����
				if(tempPerson1.NameString == name1.name)
				{
					ship1 = tempPerson1.friendship;
					break;
				}
				tempPerson1 = tempPerson1.nextperson;
			}while(tempPerson1 != null);
			if(tempPerson1 == null)
			{
				System.out.println("û���ҵ���Ϊ"+name1.name+"���ˣ�");
				return false;
			}
			
			Vertex tempPerson2 = this.firstperson;
			do{//�ҵڶ�����
				if(tempPerson2.NameString == name2.name)
				{
					ship2 = tempPerson2.friendship;
					break;
				}
				tempPerson2 = tempPerson2.nextperson;
			}while(tempPerson2 != null);
			if(tempPerson2 == null)
			{
				System.out.println("û���ҵ���Ϊ"+name2.name+"���ˣ�");
				return false;
			}
			
			ship2 = tempPerson1.friendship;
			if(ship2 != null)
			{
				do{
					if(ship2.friendsname == name2.name)//�Ѿ�������ϵ
						return false;
					ship2 = ship2.nextfriend ;
				}while(ship2 != null);
			}
			
			Friendship new1 = new Friendship();//�½����ѽ��
			new1.friendsname = name2.name;//������������
			new1.itsfriend = tempPerson2;//ָ��������ͼ�е�λ��
			new1.nextfriend = ship1;//ָ����һ�����ѡ�
				
			tempPerson1.friendnum++;//������������
			tempPerson1.friendship =new1;//��һ�����ѽ������Ϊ����������
			
			return true;
		}
	}
	
	public int getDistance(Person name1, Person name2)
	{
		if(this.firstperson == null)
		{
			System.out.println("��ϵ����û���ˣ�");
			return -1;
		}
		else
		{
			Vertex tempPerson1 = this.firstperson;
			do{//�ҵ�һ����
				if(tempPerson1.NameString == name1.name)
					break;
				tempPerson1 = tempPerson1.nextperson;
			}while(tempPerson1 != null);
			if(tempPerson1 == null)
			{
				System.out.println("û���ҵ���Ϊ"+name1.name+"���ˣ�");
				return -1;
			}
			
			if(name1.name == name2.name) return 0;
			
			Vertex tempPerson2 = this.firstperson;
			do{//�ҵڶ�����
				if(tempPerson2.NameString == name2.name)
					break;
				tempPerson2 = tempPerson2.nextperson;
			}while(tempPerson2 != null);
			if(tempPerson2 == null)
			{
				System.out.println("û���ҵ���Ϊ"+name2.name+"���ˣ�");
				return -1;
			}
			
			int distance = 1,result = 0;
			Friendship tempShip = tempPerson1.friendship;
			if (tempShip == null) return -1;//�����û������
			
			result = DFS(tempShip, distance, name2.name,name1.name);
			this.reset();
			return result;
			
		}
	}
	
	public int DFS(Friendship tempShip, int distance, String name, String name2)
	{
		boolean flag1 = false, flag2 = false;
		int distance2 = 0,distance1 = 100;
		do {
			if(tempShip.itsfriend.visited ==true)//������Ѿ���������
				tempShip = tempShip.nextfriend;//׼��������һ������
			else//û�б�����
			{
				if(tempShip.friendsname == name)//�ҵ���
				{
					flag1 = true;
					break;
				}
				else if(tempShip.friendsname == name2)//�ҵ��Լ���
				{
					tempShip = tempShip.nextfriend;//׼��������һ������
					continue;
				}
				else//û�ҵ�
				{
					tempShip.itsfriend.visited = true;
					distance2 = DFS(tempShip.itsfriend.friendship, distance+1, name,name2);
					if(distance != -1)
					{
						if(flag2 == false) 
						{
							flag2 = true;
							distance1 = distance2;
						}
						else 
						{
							if(distance1 > distance2) distance1 = distance2;
						}
					}
					tempShip = tempShip.nextfriend;//׼��������һ������
				}
			}
		}while(tempShip != null);//ֱ����������˵���������
		
		
		if(flag1 == true) 
		{
			if(flag2 == false) return distance;
			else if(distance > distance1) return distance1;
			else return distance;
		}
		else
		{
			if(flag2 == true) return distance1;
			else return -1;
		}
		
	}
	
	public boolean reset()
	{
		Vertex re = this.firstperson;
		if (re == null) return true;
		do
		{
			re.visited = false;
			re = re.nextperson ;
		}while(re != null);
		return true;
	}
	
	public static void main(String[] args) {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Rachel");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println(graph.getDistance(rachel, ross)); 
		System.out.println(graph.getDistance(rachel, ben)); 
		//should print 2
		System.out.println(graph.getDistance(rachel, rachel)); 
		//should print 0
		System.out.println(graph.getDistance(rachel, kramer));
		//should print -1


	}
}
