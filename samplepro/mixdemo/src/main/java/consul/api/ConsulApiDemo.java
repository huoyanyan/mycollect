package consul.api;
import java.util.Map;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.agent.model.Service; 
public class ConsulApiDemo {
    public static void main(String[] args) {  
        ConsulRawClient client = new ConsulRawClient("192.168.38.129", 8500);  
        ConsulClient consul = new ConsulClient(client);  
        //获取所有服务  
        Map<String, Service> map = consul.getAgentServices().getValue();  
        System.out.println(map);
    }  
}
