package com.github.foolishboy.disconf;

import java.lang.annotation.*;

/**
 * Disconf属性配置注解
 *
 * @author foolishboy66
 * @date 2019-11-18 11:58
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DisconfConfigAnnotation {

    /**
     * springboot配置项的名称
     */
    String springBootConfigName();

    /**
     * disconf配置项的名称
     */
    String disconfConfigName();

    /**
     * 配置项的默认值
     */
    String defaultValue() default "";
}
