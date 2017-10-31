package ru.codeunited.ignite.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteServices;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.services.ServiceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@Slf4j
public class ServiceGridConfiguration {

   // @Bean
    public ServiceConfiguration questServiceConfiguration(QuestServiceDistributedImpl uuidService, Ignite ignite) {
        // setup
        ServiceConfiguration serviceCfg = new ServiceConfiguration();
        serviceCfg.setService(uuidService);
        serviceCfg.setName(uuidService.getClass().getName());
        serviceCfg.setMaxPerNodeCount(2);

        // deploy
        ClusterGroup cg = ignite.cluster().forYoungest();
        IgniteServices services = ignite.services(cg);
        services.deploy(serviceCfg);
        log.info("{} deploy complete", serviceCfg.getName());

        return serviceCfg;
    }

}
