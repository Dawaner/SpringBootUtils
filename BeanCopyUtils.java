package com.dawan.lesson.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    public static <V> V copyBean(Object source, Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回目标结果
        return result;
    }

    public static <V,O> List<V> copyBeanList(List<O> source, Class<V> clazz){
        return source.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }

    public static <v> v mixObject(Object source, Object source1) {
        try {
            //实现属性拷贝
            BeanUtils.copyProperties(source, source1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回目标结果
        return (v) source1;
    }


}
