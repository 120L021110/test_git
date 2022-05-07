package P3;

public class FriendshipGraph {
	public Vertex lastperson;//保存最后一个人
	public Vertex firstperson;//保存第一个人
	public class Vertex
	{
		String NameString;//存放当前结点的人
		int friendnum = 0;//存放这个人的朋友数量
		Friendship friendship;
		Vertex nextperson;//指向下一个人
		boolean visited = false;
	}
	public class Friendship//存放这个人的关系
	{
		String friendsname;
		Vertex itsfriend;//指向这个人的朋友在关系表中的位置
		Friendship nextfriend;
	}
	
	public FriendshipGraph()
	{
		this.lastperson = null;
		this.firstperson = null;
	}
	
	public boolean searchVertex(Person name)//找这个人
	{
		if(this.firstperson == null && this.lastperson == null)//表中还没有人
			return false;
		else
		{
			Vertex temp = this.firstperson;
			do
			{
				if(temp.NameString  == name.name)//表里已经有这个人
				{
					System.out.println("关系网中存在这个人！");
					return true;
				}
				temp = temp.nextperson;
			}while(temp != null);
			//没有找到这个人
			return false;
		}
	}
	
	public boolean searchFriendship(Person name1,Person name2)//寻找某个人的某个朋友
	{
		if(this.firstperson == null && this.lastperson == null)//表中还没有人
			return false;
		else
		{
			Vertex temp = this.firstperson;
			Friendship ship = null;
			do
			{
				if(temp.NameString  == name1.name)//表里有这个人
				{
					ship = temp.friendship;
					break;
				}
				temp = temp.nextperson;
			}while(temp != null);
			
			if(ship == null)//没有找到这个人
				return false;
			else
			{
				do
				{
					if(ship.friendsname == name2.name)//有这个朋友
						return true;
					else ship = ship.nextfriend;
				}while(ship != null);
				return false;
			}
		}
	}
	
	public boolean addVertex(Person name)//增加一个人
	{
		Vertex newperson = new Vertex();
		newperson.NameString = name.name;
		newperson.friendship = null;
		newperson.nextperson = null;
		
		if(this.firstperson == null && this.lastperson == null)//表中还没有人
		{
			this.firstperson = newperson;
			this.lastperson = newperson;
			return true;
		}
		else//已经有人了
		{
			Vertex temp = this.firstperson;
			do
			{
				if(temp.NameString  == newperson.NameString)//表里已经有这个人
				{
					System.out.println("关系网中存在名字相同的人！");
					System.exit(0);
				}
				temp = temp.nextperson;
			}while(temp != null);
			//没有找到这个人
			this.lastperson.nextperson  = newperson;
			this.lastperson = newperson;
			return true;
		}
	}
	
	public boolean addEdge(Person name1, Person name2)//建立两个人之间的关系
	{
		if(this.firstperson == null)
		{
			System.out.println("关系网中没有人！");
			return false;
		}
		else
		{
			
			Friendship ship1 = null, ship2 = null;
			Vertex tempPerson1 = this.firstperson;
			do{//找第一个人
				if(tempPerson1.NameString == name1.name)
				{
					ship1 = tempPerson1.friendship;
					break;
				}
				tempPerson1 = tempPerson1.nextperson;
			}while(tempPerson1 != null);
			if(tempPerson1 == null)
			{
				System.out.println("没有找到名为"+name1.name+"的人！");
				return false;
			}
			
			Vertex tempPerson2 = this.firstperson;
			do{//找第二个人
				if(tempPerson2.NameString == name2.name)
				{
					ship2 = tempPerson2.friendship;
					break;
				}
				tempPerson2 = tempPerson2.nextperson;
			}while(tempPerson2 != null);
			if(tempPerson2 == null)
			{
				System.out.println("没有找到名为"+name2.name+"的人！");
				return false;
			}
			
			ship2 = tempPerson1.friendship;
			if(ship2 != null)
			{
				do{
					if(ship2.friendsname == name2.name)//已经建立关系
						return false;
					ship2 = ship2.nextfriend ;
				}while(ship2 != null);
			}
			
			Friendship new1 = new Friendship();//新建朋友结点
			new1.friendsname = name2.name;//设置朋友名字
			new1.itsfriend = tempPerson2;//指向朋友在图中的位置
			new1.nextfriend = ship1;//指向下一个朋友。
				
			tempPerson1.friendnum++;//朋友数量增加
			tempPerson1.friendship =new1;//第一个朋友结点设置为新增的朋友
			
			return true;
		}
	}
	
	public int getDistance(Person name1, Person name2)
	{
		if(this.firstperson == null)
		{
			System.out.println("关系网中没有人！");
			return -1;
		}
		else
		{
			Vertex tempPerson1 = this.firstperson;
			do{//找第一个人
				if(tempPerson1.NameString == name1.name)
					break;
				tempPerson1 = tempPerson1.nextperson;
			}while(tempPerson1 != null);
			if(tempPerson1 == null)
			{
				System.out.println("没有找到名为"+name1.name+"的人！");
				return -1;
			}
			
			if(name1.name == name2.name) return 0;
			
			Vertex tempPerson2 = this.firstperson;
			do{//找第二个人
				if(tempPerson2.NameString == name2.name)
					break;
				tempPerson2 = tempPerson2.nextperson;
			}while(tempPerson2 != null);
			if(tempPerson2 == null)
			{
				System.out.println("没有找到名为"+name2.name+"的人！");
				return -1;
			}
			
			int distance = 1,result = 0;
			Friendship tempShip = tempPerson1.friendship;
			if (tempShip == null) return -1;//这个人没有朋友
			
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
			if(tempShip.itsfriend.visited ==true)//这个人已经遍历过了
				tempShip = tempShip.nextfriend;//准备遍历下一个朋友
			else//没有遍历过
			{
				if(tempShip.friendsname == name)//找到了
				{
					flag1 = true;
					break;
				}
				else if(tempShip.friendsname == name2)//找到自己了
				{
					tempShip = tempShip.nextfriend;//准备遍历下一个朋友
					continue;
				}
				else//没找到
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
					tempShip = tempShip.nextfriend;//准备遍历下一个朋友
				}
			}
		}while(tempShip != null);//直到搜完这个人的所有朋友
		
		
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
