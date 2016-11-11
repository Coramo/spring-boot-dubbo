package me.coramo.springframework.boot.dubbo;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.rpc.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wcx on 16/9/30.
 */
@Configuration
@ConditionalOnClass(Exporter.class)
@EnableConfigurationProperties(DubboProperties.class)
public class DubboAutoConfiguration {

  @Autowired
  private DubboProperties dubboProperties;

  /**
   *
   * @param scan 无法获取dubboProperties 因为AnnotationBean需要在此之前加载
   * @return annotationBean
   */
  @Bean
  public static AnnotationBean annotationBean(@Value("${spring.dubbo.packages:}") String scan) {
    AnnotationBean annotationBean = new AnnotationBean();
    annotationBean.setPackage(scan);
    return annotationBean;
  }

  @Bean
  public ApplicationConfig applicationConfig() {
    return dubboProperties.getApplication();
  }

  @Bean
  public RegistryConfig registryConfig() {
    return dubboProperties.getRegistry();
  }

  @Bean
  public ProtocolConfig protocolConfig() {
    return dubboProperties.getProtocol();
  }

  @Bean
  public ProviderConfig providerConfig() {
    ProviderConfig providerConfig = dubboProperties.getProvider();
    if (dubboProperties.getProtocol() != null) {
      providerConfig.setProtocol(dubboProperties.getProtocol());
    }
    if (dubboProperties.getRegistry() != null) {
      providerConfig.setRegistry(dubboProperties.getRegistry());
    }
    if (dubboProperties.getApplication() != null) {
      providerConfig.setApplication(dubboProperties.getApplication());
    }
    return providerConfig;
  }

  @Bean
  public ConsumerConfig consumerConfig() {
    ConsumerConfig consumerConfig = dubboProperties.getConsumer();
    return consumerConfig;
  }

}
