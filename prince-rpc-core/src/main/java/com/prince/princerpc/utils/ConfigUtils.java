package com.prince.princerpc.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.setting.dialect.Props;
import com.prince.princerpc.config.RpcConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;


/**
 * @program: prince-rpc
 * @description: 配置工具类
 * @author: clf
 * @create: 2024-08-24 09:26
 **/
public class ConfigUtils {

    /**
     * 加载配置对象
     * @param clazz
     * @param prefix
     * @return
     * @param <T>
     */
    public static <T> T loadConfig(Class<T> clazz, String prefix) {
        return loadConfig(clazz,prefix,"");
    }

    /**
     * 加载配置对象，区分环境
     * @param clazz
     * @param prefix
     * @param environment
     * @return
     * @param <T>
     */
//    public static <T> T loadConfig(Class<T> clazz, String prefix, String environment) {
//        StringBuilder builder = new StringBuilder("application");
//        if(environment != null && !environment.isEmpty()) {
//            builder.append("-").append(environment);
//        }
//        builder.append(".properties");
//        Props props = new Props(builder.toString());
//        return props.toBean(clazz,prefix);
//    }



    /**
     * 加载配置对象，根据环境加载对应的配置文件，并支持多种格式的配置文件。
     *
     * @param clazz       配置类的Class对象
     * @param prefix      配置文件中的前缀，用于绑定配置属性
     * @param environment 当前环境标识，例如"dev", "prod"等
     * @param <T>         配置类的类型
     * @return 返回绑定了配置属性的配置类实例
     */
    public static <T> T loadConfig(Class<T> clazz, String prefix, String environment) {
        // 创建配置文件名的基础部分，例如 "application"
        StringBuilder builder = new StringBuilder("application");

        // 如果 environment 参数不为空，则添加环境标识到文件名，例如 "application-dev"
        if (ObjectUtil.isNotEmpty(environment)) {
            builder.append("-").append(environment);
        }

        // 尝试加载 .properties 格式的配置文件
        String propertiesFileName = builder.toString() + ".properties";
        Props props = null;
        try{
            props = new Props(propertiesFileName);
        }catch (cn.hutool.core.io.resource.NoResourceException e){
            System.out.println("未找到 .properties 文件，尝试加载 .yml 或 .yaml 文件");
        }
        if (props != null) {
            // 如果加载的是 .properties 文件，则通过 Props 工具类将配置文件映射为配置类实例
            return props.toBean(clazz, prefix);
        }
            // 如果 .properties 文件不存在，尝试加载 .yml 或 .yaml 格式的配置文件
            String yamlFileName = builder.toString() + ".yml";
            T config = loadYamlConfig(clazz, yamlFileName, prefix);

            // 如果 .yml 文件不存在，再尝试加载 .yaml 文件
            if (config == null) {
                yamlFileName = builder.toString() + ".yaml";
                config = loadYamlConfig(clazz, yamlFileName, prefix);
            }

            // 如果 YAML 文件加载成功，则返回配置对象
            if (config != null) {
                return config;
            }

            // 如果所有文件都不存在，抛出异常
            throw new IllegalArgumentException("未找到有效的配置文件: " + propertiesFileName + " 或 " + yamlFileName);

    }

    /**
     * 从 YAML 文件中加载配置对象。
     *
     * @param clazz  配置类的Class对象
     * @param fileName YAML 文件的文件名
     * @param prefix YAML 文件中的前缀，用于绑定配置属性
     * @param <T>    配置类的类型
     * @return 返回绑定了配置属性的配置类实例，如果文件不存在或加载失败则返回 null
     */
    private static <T> T loadYamlConfig(Class<T> clazz, String fileName, String prefix) {
        // 尝试从 classpath 加载 YAML 文件
        InputStream inputStream = ConfigUtils.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            return null; // 文件不存在，返回 null
        }

        // 使用 SnakeYAML 库解析 YAML 文件
        Yaml yaml = new Yaml();
        Object data = yaml.load(inputStream);

        // 将解析后的数据映射为指定的配置类对象
        return mapYamlToBean(clazz, data, prefix);
    }

    /**
     * 将 YAML 数据映射为配置类对象。
     *
     * @param clazz  配置类的Class对象
     * @param data   从 YAML 文件中加载的数据
     * @param prefix YAML 文件中的前缀，用于绑定配置属性
     * @param <T>    配置类的类型
     * @return 返回绑定了配置属性的配置类实例
     */
    private static <T> T mapYamlToBean(Class<T> clazz, Object data, String prefix) {
        // 如果 YAML 文件中的前缀不为空，提取相应的部分
        if (ObjectUtil.isNotEmpty(prefix) && data instanceof Map) {
            data = ((Map<?, ?>) data).get(prefix);
        }

        // 使用 Hutool 或类似工具将数据映射为配置类对象
        return cn.hutool.core.bean.BeanUtil.toBean(data, clazz);
    }
}
