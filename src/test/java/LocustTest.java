import com.github.myzhan.locust4j.Locust;

public class LocustTest {

        public static void main(String[] args) {
            Locust locust = Locust.getInstance();
            locust.setMasterHost("127.0.0.1");
            locust.setMasterPort(5557); //some free port to run the Locust slave

            locust.run(
                    new OpenApplicationTask(50)
            ); // <- You custom performance tasks should be here
        }

}


