package com.github.foolishboy.disconf;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;
import com.baidu.disconf.client.addons.properties.ReloadablePropertiesFactoryBean;
import com.baidu.disconf.client.config.DisClientSysConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * disconf自动配置类
 *
 * @author foolishboy66
 * @date 2019-11-17 20:49
 */
@Configuration
public class DisconfAutoConfiguration implements EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(DisconfAutoConfiguration.class);

    private DisconfProperties disconfProperties;

    private ConfigurableEnvironment environment;

    @Bean(destroyMethod = "destroy")
    public DisconfMgrBean getDisconfMgrBean() {

        DisconfMgrBean disconfMgrBean = new DisconfMgrBean();
        String scanPackage = disconfProperties.getScanPackage();
        if (scanPackage == null || "".equals(scanPackage = (scanPackage.trim()))) {
            logger.error("Disconf scan package is null!, please set the value in application.properties.(spring.disconf.scan-package)");
            throw new RuntimeException("Disconf scan package is null!, please set the value in application.properties.(spring.disconf.scan-package)");
        }
        disconfMgrBean.setScanPackage(scanPackage);
        return disconfMgrBean;
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public DisconfMgrBeanSecond getDisconfMgrBean2() {

        return new DisconfMgrBeanSecond();
    }

    /**
     * 使用托管方式的disconf配置(无代码侵入, 配置更改会自动reload)
     *
     * @return 可自动加载的工厂bean
     */
    @Bean("reloadablePropertiesFactoryBean")
    public ReloadablePropertiesFactoryBean reloadablePropertiesFactoryBean() {

        List<String> splitFiles = getSplitFiles(disconfProperties.getReloadableFiles());

        ReloadablePropertiesFactoryBean reloadablePropertiesFactoryBean = new ReloadablePropertiesFactoryBean();
        reloadablePropertiesFactoryBean.setLocations(splitFiles);
        return reloadablePropertiesFactoryBean;
    }

    /**
     * 使用托管方式的disconf配置(无代码侵入, 配置更改不会自动reload)
     *
     * @return 可自动加载的工厂bean
     */
    @Bean("unReloadablePropertiesFactoryBean")
    public ReloadablePropertiesFactoryBean unReloadablePropertiesFactoryBean() {

        List<String> splitFiles = getSplitFiles(disconfProperties.getUnReloadableFiles());

        ReloadablePropertiesFactoryBean reloadablePropertiesFactoryBean = new ReloadablePropertiesFactoryBean();
        reloadablePropertiesFactoryBean.setLocations(splitFiles);
        return reloadablePropertiesFactoryBean;
    }

    @Bean
    @ConditionalOnBean(name = "reloadablePropertiesFactoryBean")
    public ReloadingPropertyPlaceholderConfigurer propertyConfigurer(@Qualifier("reloadablePropertiesFactoryBean") ReloadablePropertiesFactoryBean reloadablePropertiesFactoryBean) throws IOException {

        ReloadingPropertyPlaceholderConfigurer reloadingPropertyPlaceholderConfigurer = new ReloadingPropertyPlaceholderConfigurer();
        reloadingPropertyPlaceholderConfigurer.setOrder(1);
        reloadingPropertyPlaceholderConfigurer.setIgnoreResourceNotFound(true);
        reloadingPropertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        reloadingPropertyPlaceholderConfigurer.setProperties(reloadablePropertiesFactoryBean.getObject());
        addPropertiesPropertySource("disconfReloadableProperties", reloadablePropertiesFactoryBean.getObject());

        return reloadingPropertyPlaceholderConfigurer;
    }

    @Bean
    @ConditionalOnBean(name = "unReloadablePropertiesFactoryBean")
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(@Qualifier("unReloadablePropertiesFactoryBean") ReloadablePropertiesFactoryBean unReloadablePropertiesFactoryBean) throws IOException {

        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setOrder(1);
        propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);
        propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        propertySourcesPlaceholderConfigurer.setProperties(unReloadablePropertiesFactoryBean.getObject());
        addPropertiesPropertySource("disconfUnReloadableProperties", unReloadablePropertiesFactoryBean.getObject());

        return propertySourcesPlaceholderConfigurer;
    }

    private List<String> getSplitFiles(String fileNamesStr) {

        if (fileNamesStr != null && !"".equals(fileNamesStr = (fileNamesStr.trim()))) {
            List<String> fileNames = Arrays.asList(fileNamesStr.split(","));
            if (disconfProperties.isEnableLocalDownloadDirInClassPath()) {
                return fileNames;
            }

            return fileNames.stream()
                    .map(str -> ("file:" + disconfProperties.getUserDefineDownloadDir() + "/" + str))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * 增加环境变量配置
     *
     * @param name   配置文件名
     * @param source 文件源
     */
    private void addPropertiesPropertySource(String name, Properties source) {

        PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource(name, source);
        environment.getPropertySources().addLast(propertiesPropertySource);
    }

    @Override
    public void setEnvironment(Environment environment) {

        this.environment = (ConfigurableEnvironment) environment;
        this.disconfProperties = new DisconfProperties(this.environment);
        //加载Disconf 系统自带的配置
        loadDisClientSysConfig();
    }

    /**
     * 加载Disconf 系统自带的配置
     */
    private void loadDisClientSysConfig() {

        //加载Disconf 系统自带的配置
        DisClientSysConfig disClientSysConfig = DisClientSysConfig.getInstance();
        try {
            disClientSysConfig.loadConfig(null);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        //将系统下载目录替换成用户配置的下载目录
        disClientSysConfig.LOCAL_DOWNLOAD_DIR = disconfProperties.getUserDefineDownloadDir();
    }
}
    