package proto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// tag::anotations[] 
@EnableZuulProxy  // <1>
@EnableDiscoveryClient  // <2>
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
// end::anotations[] 
