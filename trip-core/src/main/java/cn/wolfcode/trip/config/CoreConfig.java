package cn.wolfcode.trip.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@MapperScan("cn.wolfcode.trip.mapper")
@PropertySource("classpath:core.properties")
public class CoreConfig {

}
