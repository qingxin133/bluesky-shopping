import com.bluesky.shopping.oauth.OAuthApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = OAuthApplication.class)
public class ApplyTokenTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Test
    public void applyToken(){
        //构建请求地址  http://localhost:9200/oauth/token
//        ServiceInstance serviceInstance = loadBalancerClient.choose("user-auth");
        // http://localhost:9200
//        URI uri = serviceInstance.getUri();
        // http://localhost:9200/oauth/token uri+"/oauth/token";
        String url= "http://localhost:9200/oauth/token";

        // 封装请求参数 body , headers
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username","shopping");
        body.add("password","shopping");

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization",this.getHttpBasic("shopping","shopping"));
        HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<>(body,headers);

        //当后端出现了401,400.后端不对着两个异常编码进行处理,而是直接返回给前端
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode()!=400 && response.getRawStatusCode() != 401){
                    super.handleError(response);
                }
            }
        });

        //发送请求
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
        Map map = responseEntity.getBody();
        System.out.println(map);

    }

    private String getHttpBasic(String clientId, String clientSecret) {
        String value =clientId+":"+clientSecret;
        byte[] encode = Base64Utils.encode(value.getBytes());
        //Basic Y2hhbmdnb3U6Y2hhbmdnb3U=
        return "Basic "+new String(encode);
    }
}
