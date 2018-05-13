package proto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import de.codecentric.boot.admin.config.EnableAdminServer;
import be.ordina.msdashboard.EnableMicroservicesDashboardServer;

// tag::anotations[] 
@EnableDiscoveryClient  // <1>
@EnableAdminServer      // <2>
@EnableMicroservicesDashboardServer  // <3>
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
// end::anotations[] 
