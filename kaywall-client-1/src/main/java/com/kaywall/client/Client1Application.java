package com.kaywall.client;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class Client1Application {

    private static final String SERVER_1_URI = "http://app-server-1";
    private static final String SERVER_2_URI = "http://app-server-2";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    public static void main(String[] args) {

        SpringApplication.run(Client1Application.class, args);
    }


    @GetMapping("client")
    public String client(){
        String body = restTemplate.getForEntity(SERVER_1_URI + "/hello", String.class).getBody();
        return "get from server1 : " + body;
    }


    @GetMapping("/testLoad")
    public String getTimeViaEurekaClient() {
        List<ServiceInstance> instances = discoveryClient.getInstances("app-server-1");


        String result = "no instance available";

        if (instances != null && instances.size() > 0) {
            ServiceInstance instance = instances.get(0);

            // Invoke server based on host and port.
            // Example using RestTemplate.
            URI productUri = URI.create(String
                    .format("http://%s:%s/hello",
                            instance.getHost(), instance.getPort()));

            System.out.println("URI:" + productUri.toString());

            result = restTemplate.getForObject(productUri, String.class);
        }

        return result;
    }

    @Bean
//    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
