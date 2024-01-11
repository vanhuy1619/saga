package com.example.productcommandservice.common.kafka;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.TimeUnit;

@Configuration
public class RestTemplateClient {
	@Value("${rest.timeout}")
	private Integer restTimeout;

	@Value("${rest.poolMaxTotal}")
	private String poolMaxTotal;

	@Value("${rest.poolMaxPerRoute}")
	private String poolMaxPerRoute;

	@Value("${rest.connectionRequestTimeout}")
	private String connectionRequestTimeout;

	@Value("${rest.connectTimeout}")
	private String connectTimeout;

	@Value("${rest.timeout}")
	private String timeout;

	@Bean
	public PoolingHttpClientConnectionManager customizedPoolingHttpClientConnectionManager(){

		//One of the purpose of the TimeToLive parameter is ensure a more equal redistribution of persistent connection across a cluster of nodes.
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(5, TimeUnit.MINUTES);
		connManager.setMaxTotal(NumberUtils.toInt(poolMaxTotal, 200)); // max all api
		connManager.setDefaultMaxPerRoute(NumberUtils.toInt(poolMaxPerRoute, 50)); // max 1 api (dua vao base url cua api)
		return connManager;
	}

	@Bean
	public RestTemplate restTemplate() {

		RequestConfig reqConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(NumberUtils.toInt(connectionRequestTimeout, 5000)) //timeout cho toi luot (khi het connect trong pool)
				.setConnectTimeout(NumberUtils.toInt(connectTimeout, 5000))  // timeout khi khong ket noi duoc api (test in dev  4,5s is max)
				.setSocketTimeout(NumberUtils.toInt(timeout, 30000)) // timeout khi goi api qua lau (da ke noi roi va cho data)
				.build();

		HttpClientBuilder clientBuilder = HttpClientBuilder.create()
				.setConnectionManager(customizedPoolingHttpClientConnectionManager())
				.setConnectionManagerShared(true)  //this is important to set as true in case of more than one downstream APIs as we want to set a common HTTP connection pool
				.setDefaultRequestConfig(reqConfig);

		HttpComponentsClientHttpRequestFactory reqFactor = new HttpComponentsClientHttpRequestFactory();
		reqFactor.setHttpClient(clientBuilder.build());

		return new RestTemplate(reqFactor);
	}

}
