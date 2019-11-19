package com.github.foolishboy.disconf;

import com.baidu.disconf.client.support.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

import java.lang.reflect.Field;

/**
 * disconf配置属性类
 *
 * @author foolistboy66
 * @date 2019-11-17 21:06
 */
@SuppressWarnings("ConfigurationProperties")
@ConfigurationProperties(prefix = DisconfProperties.DISCONF_PROPERTIES_PREFIX)
public class DisconfProperties {

    private static final Logger logger = LoggerFactory.getLogger(DisconfProperties.class);

    static final String DISCONF_PROPERTIES_PREFIX = "spring.disconf";

    /**
     * 扫描包的基础路径
     * 必填
     */
    @DisconfConfigAnnotation(springBootConfigName = "scan-package", disconfConfigName = "disconf.scanPackage")
    private String scanPackage;

    /**
     * 配置服务器的HOST,用逗号分隔
     * 必填
     * 示例：127.0.0.1:8000,127.0.0.1:8000
     */
    @DisconfConfigAnnotation(springBootConfigName = "conf-server-host", disconfConfigName = "disconf.conf_server_host")
    private String confServerHost;

    /**
     * APP请采用产品线_服务名格式
     * 非必填
     * 优先读取命令行参数，然后再读取此文件的值
     */
    @DisconfConfigAnnotation(springBootConfigName = "app", disconfConfigName = "disconf.app")
    private String app;

    /**
     * 版本号,请采用 X_X_X_X 格式
     * 非必填
     * 默认为DEFAULT_VERSION，优先读取命令行参数，然后再读取此文件的值，最后才读取默认值
     */
    @DisconfConfigAnnotation(springBootConfigName = "version", disconfConfigName = "disconf.version")
    private String version;

    /**
     * 是否使用远程配置文件，true会从远程获取配置，false则直接获取本地配置
     * 非必填
     * 默认为false
     */
    @DisconfConfigAnnotation(springBootConfigName = "enable-remote-conf", disconfConfigName = "disconf.enable.remote.conf", defaultValue = "false")
    private boolean enableRemoteConf = false;

    /**
     * 环境，默认为DEFAULT_ENV。
     * 非必填
     * 优先读取命令行参数，然后再读取此文件的值，最后才读取默认值
     */
    @DisconfConfigAnnotation(springBootConfigName = "env", disconfConfigName = "disconf.env")
    private String env;

    /**
     * 忽略的分布式配置，用空格分隔
     * 非必填
     * 默认为空
     */
    @DisconfConfigAnnotation(springBootConfigName = "ignore", disconfConfigName = "disconf.ignore")
    private String ignore;

    /**
     * 调试模式。调试模式下，ZK超时或断开连接后不会重新连接（常用于client单步debug）。非调试模式下，ZK超时或断开连接会自动重新连接。
     * 非必填
     * 默认为false
     */
    @DisconfConfigAnnotation(springBootConfigName = "debug", disconfConfigName = "disconf.debug", defaultValue = "false")
    private boolean debug = false;

    /**
     * 获取远程配置 重试次数，默认是3次
     * 非必填
     * 默认为3次
     */
    @DisconfConfigAnnotation(springBootConfigName = "conf-server-url-retry-times", disconfConfigName = "disconf.conf_server_url_retry_times", defaultValue = "3")
    private int confServerUrlRetryTimes = 3;

    /**
     * 获取远程配置 重试时休眠时间，默认是5秒
     * 非必填
     * 默认为5秒
     */
    @DisconfConfigAnnotation(springBootConfigName = "conf-server-url-retry-sleep-seconds", disconfConfigName = "disconf.conf_server_url_retry_sleep_seconds", defaultValue = "5")
    private int confServerUrlRetrySleepSeconds = 5;

    /**
     * 用户定义的下载文件夹, 远程文件下载后会放在这里。注意，此文件夹必须有有权限，否则无法下载到这里
     * 非必填
     * 默认为./disconf/download
     */
    @DisconfConfigAnnotation(springBootConfigName = "user-define-download-dir", disconfConfigName = "disconf.user_define_download_dir", defaultValue = "./disconf/download")
    private String userDefineDownloadDir;

    /**
     * 下载的文件会被迁移到classpath根路径下，强烈建议将此选项置为 true(默认是true)
     * 非必填
     * 默认为true
     */
    @DisconfConfigAnnotation(springBootConfigName = "enable-local-download-dir-in-class-path", disconfConfigName = "disconf.enable_local_download_dir_in_class_path", defaultValue = "true")
    private boolean enableLocalDownloadDirInClassPath = true;

    /**
     * 当disconf上文件修改时，会自动加载的文件，多个用英文逗号隔开
     * 非必填
     * 默认为空
     */
    @DisconfConfigAnnotation(springBootConfigName = "reloadable-files", disconfConfigName = "disconf.reloadFiles")
    private String reloadableFiles;

    /**
     * 当disconf上文件修改时，不会自动加载的文件，多个用英文逗号隔开
     * 非必填
     * 默认为空
     */
    @DisconfConfigAnnotation(springBootConfigName = "un-reloadable-files", disconfConfigName = "disconf.unReloadFiles")
    private String unReloadableFiles;

    public String getScanPackage() {
        return scanPackage;
    }

    public void setScanPackage(String scanPackage) {
        this.scanPackage = scanPackage;
    }

    public String getConfServerHost() {
        return confServerHost;
    }

    public void setConfServerHost(String confServerHost) {
        this.confServerHost = confServerHost;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isEnableRemoteConf() {
        return enableRemoteConf;
    }

    public void setEnableRemoteConf(boolean enableRemoteConf) {
        this.enableRemoteConf = enableRemoteConf;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getIgnore() {
        return ignore;
    }

    public void setIgnore(String ignore) {
        this.ignore = ignore;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getConfServerUrlRetryTimes() {
        return confServerUrlRetryTimes;
    }

    public void setConfServerUrlRetryTimes(int confServerUrlRetryTimes) {
        this.confServerUrlRetryTimes = confServerUrlRetryTimes;
    }

    public int getConfServerUrlRetrySleepSeconds() {
        return confServerUrlRetrySleepSeconds;
    }

    public void setConfServerUrlRetrySleepSeconds(int confServerUrlRetrySleepSeconds) {
        this.confServerUrlRetrySleepSeconds = confServerUrlRetrySleepSeconds;
    }

    public String getUserDefineDownloadDir() {
        return userDefineDownloadDir;
    }

    public void setUserDefineDownloadDir(String userDefineDownloadDir) {
        this.userDefineDownloadDir = userDefineDownloadDir;
    }

    public boolean isEnableLocalDownloadDirInClassPath() {
        return enableLocalDownloadDirInClassPath;
    }

    public void setEnableLocalDownloadDirInClassPath(boolean enableLocalDownloadDirInClassPath) {
        this.enableLocalDownloadDirInClassPath = enableLocalDownloadDirInClassPath;
    }

    public String getReloadableFiles() {
        return reloadableFiles;
    }

    public void setReloadableFiles(String reloadableFiles) {
        this.reloadableFiles = reloadableFiles;
    }

    public String getUnReloadableFiles() {
        return unReloadableFiles;
    }

    public void setUnReloadableFiles(String unReloadableFiles) {
        this.unReloadableFiles = unReloadableFiles;
    }

    public DisconfProperties(Environment environment) {
        loadConfig(environment);
    }

    private void loadConfig(Environment environment) {

        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(DisconfConfigAnnotation.class)) {
                DisconfConfigAnnotation config = field.getAnnotation(DisconfConfigAnnotation.class);
                // 获取配置属性的值
                String value = environment.getProperty(DisconfProperties.DISCONF_PROPERTIES_PREFIX + "." + config.springBootConfigName(), config.defaultValue());
                // 设置到系统环境变量中，给disClientConfig解析
                System.setProperty(config.disconfConfigName(), value);
                try {
                    field.setAccessible(true);
                    ClassUtils.setFieldValeByType(field, this, value);
                } catch (Exception e) {
                    logger.error(String.format("invalid config: %s", config.springBootConfigName()), e);
                }
            }
        }
    }

}

