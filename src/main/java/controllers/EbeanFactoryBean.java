package controllers;

import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import io.ebean.springsupport.txn.SpringAwareJdbcTransactionManager;

/**
 * Spring factory for creating the EbeanServer singleton.
 */
@Configuration
public class EbeanFactoryBean {
  
//  @Autowired
//  DataSource dataSource;
  
  @Bean
  public EbeanServer getObject() throws Exception {

    ServerConfig config = new ServerConfig();
    config.setName("db");

//    // set the spring's datasource and transaction manager.
//    config.setDataSource(dataSource);
//    config.setExternalTransactionManager(new SpringAwareJdbcTransactionManager());

    config.loadFromProperties();
    config.setDefaultServer(true);

    return EbeanServerFactory.create(config);
  }
}