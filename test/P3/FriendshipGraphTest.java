package P3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FriendshipGraphTest {
	FriendshipGraph graph = new FriendshipGraph();
	
	@Test
	public void addVertexTest() {
		Person yu = new Person("Yu");
		Person tao = new Person("Tao");
		Person cai = new Person("Cai");
		Person ji = new Person("Ji");
		graph.addVertex(yu);
		graph.addVertex(tao);
		graph.addVertex(cai);
		graph.addVertex(ji);
		assertTrue(graph.searchVertex(yu));
		assertTrue(graph.searchVertex(tao));
		assertTrue(graph.searchVertex(cai));
		assertTrue(graph.searchVertex(ji));
	}
	
	@Test
	public void addEdgeTest() {
		Person wang = new Person("Wang");
		Person ning = new Person("Ning");
		Person li = new Person("Li");
		Person hai = new Person("Hai");
		graph.addVertex(wang);
		graph.addVertex(ning);
		graph.addVertex(li);
		graph.addVertex(hai);
		graph.addEdge(wang, ning);
		graph.addEdge(ning, wang);
		graph.addEdge(ning, li);
		graph.addEdge(li, ning);
		assertTrue(graph.searchFriendship(wang,ning));
		assertTrue(graph.searchFriendship(ning,wang));
		assertTrue(graph.searchFriendship(ning,li));
		assertTrue(graph.searchFriendship(li,ning));
	}

	@Test
	public void getDistancetest() {
		Person wo = new Person("Wo");
		Person shi = new Person("Shi");
		Person xue = new Person("Xue");
		Person sheng = new Person("Sheng");
		graph.addVertex(wo);
		graph.addVertex(shi);
		graph.addVertex(xue);
		graph.addVertex(sheng);
		graph.addEdge(wo, shi);
		graph.addEdge(shi, wo);
		graph.addEdge(shi, xue);
		graph.addEdge(xue, shi);
		assertEquals(1, graph.getDistance(wo, shi));
		assertEquals(2, graph.getDistance(wo, xue));
		assertEquals(0, graph.getDistance(sheng, sheng));
		assertEquals(-1, graph.getDistance(wo, sheng));
	}
}
