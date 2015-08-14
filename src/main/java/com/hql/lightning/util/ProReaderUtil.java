package com.hql.lightning.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * 配置文件读取类
 *
 * @author lee
 *         2015-1-29
 */
public class ProReaderUtil {

    private static Logger logger = Logger.getLogger(ProReaderUtil.class.getName());

    private static ProReaderUtil instance = new ProReaderUtil();

    /**
     * 配置文件路径
     */
    private String confPath = "gameFiles";

    /**
     * redis配置缓存
     */
    private HashMap<String, String> redisConf = null;

    public static ProReaderUtil getInstance() {
        return instance;
    }

    /**
     * 设置配置文件根路径
     *
     * @param path
     */
    public void setConfPath(String path) {
        confPath = path;
    }

    /**
     * 获取配置文件根路径
     *
     * @return
     */
    public String getConfPath() {
        return confPath;
    }

    /**
     * 读取配置文件内容
     *
     * @param file
     * @return
     */
    private Properties getPro(String file) {
        Properties properties = new Properties();
        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream(confPath + "/conf/" + file + ".properties");
            properties.load(inputStream);
            inputStream.close();
        } catch (Exception e) {
            logger.error(file + " config file not found.");
        }

        return properties;
    }

    /**
     * 获取log4j配置
     *
     * @return
     */
    public Properties getLog4jPro() {
        Properties properties = getPro("log4j");
        return properties;
    }

    /**
     * 获取redis配置
     *
     * @return
     */
    public HashMap<String, String> getRedisPro() {
        if (redisConf == null) {
            redisConf = new HashMap<String, String>();
            Properties properties = getPro("redis");
            redisConf.put("host", properties.getProperty("redis.host"));
            redisConf.put("port", properties.getProperty("redis.port"));
            redisConf.put("maxTotal", properties.getProperty("redis.maxTotal"));
            redisConf.put("maxIdle", properties.getProperty("redis.maxIdle"));
            redisConf.put("timeOut", properties.getProperty("redis.timeOut"));
            redisConf.put("retryNum", properties.getProperty("redis.retryNum"));
        }

        return redisConf;
    }

    /**
     * 获取netty配置
     *
     * @return
     */
    public HashMap<String, String> getNettyPro() {
        HashMap<String, String> conf = new HashMap<String, String>();
        Properties properties = getPro("netty");
        conf.put("port", properties.getProperty("netty.port"));
        conf.put("host", properties.getProperty("netty.host"));
        conf.put("heartBeatTimeOut", properties.getProperty("netty.heartBeatTimeOut"));

        return conf;
    }

    /**
     * 获取逻辑工作线程配置
     *
     * @return
     */
    public String getWorkersPro() {
        Properties properties = getPro("workers");
        return properties.getProperty("workers.member");
    }

    /**
     * 获取jdbc配置
     *
     * @return
     */
    public HashMap<String, String> getJdbcPro() {
        HashMap<String, String> conf = new HashMap<String, String>();
        Properties properties = getPro("jdbc");
        conf.put("driver", properties.getProperty("jdbc.driver"));
        conf.put("url", properties.getProperty("jdbc.url"));
        conf.put("userName", properties.getProperty("jdbc.userName"));
        conf.put("password", properties.getProperty("jdbc.password"));

        return conf;
    }

    /**
     * 获取MyBatis配置
     *
     * @return
     * @throws Exception
     */
    public FileInputStream getMyBatisPro() throws Exception {
        return new FileInputStream(confPath + "/conf/mybatisConf.xml");
    }

    /**
     * 获取游戏业务模块配置
     *
     * @return
     * @throws Exception
     */
    public File getModulePro() throws Exception {
        return new File(confPath + "/conf/moduleConf.xml");
    }
}
