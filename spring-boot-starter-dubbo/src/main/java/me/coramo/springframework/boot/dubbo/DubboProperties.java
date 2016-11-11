package me.coramo.springframework.boot.dubbo;

import com.alibaba.dubbo.config.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by wcx on 16/9/30.
 */
@Data
@ConfigurationProperties(prefix = "spring.dubbo")
public class DubboProperties {

  private String packages;

  private ApplicationConfig application = new ApplicationConfig();

  private RegistryConfig registry = new RegistryConfig();

  private ProtocolConfig protocol = new ProtocolConfig();

  private ProviderConfig provider = new ProviderConfig();

  private ConsumerConfig consumer = new ConsumerConfig();

}
