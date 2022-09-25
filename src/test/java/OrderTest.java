import config.CreateCourierData;
import org.junit.Before;
import config.Order;

public class OrderTest {
    Order order;
    String oauthToken;

    @Before
    public void setUp(){
        order = Order.getRandomOrder();
        oauthToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MjdkMzE4ZWQzYjg2YTAwM2Q2N2M1ODQiLCJpYXQiOjE2NjQxMjk1ODMsImV4cCI6MTY2NDczNDM4M30.IKzjRlcG_JmPBl7WKUuI5CVkJn7sU4jiX_rKZt8TZqk";
    }
}
