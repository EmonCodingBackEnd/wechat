/*
 * 文件名称：DefaultCSVParser.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181213 15:11
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181213-01         Rushing0711     M201812131511 新建文件
 ********************************************************************************/
package com.coding.component.csv.parser;

import com.coding.component.csv.bean.BaseBean;
import com.coding.component.csv.bean.DynamicBean;
import com.coding.component.regex.RegexResult;
import com.coding.component.regex.RegexSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

@Slf4j
public class DefaultCSVParser extends AbstractCSVParser {

    @Override
    protected <T> T parseToBean(
            final Class<T> clazz,
            final Map<String, String> map,
            final CsvPath csvPath,
            final boolean headerTransfer)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (Map.class.isAssignableFrom(clazz)) {
            return (T) map;
        }
        if (DynamicBean.class.isAssignableFrom(clazz)) {
            @SuppressWarnings("rawtypes")
            Map<String, Class> propertyMap = new HashMap<String, Class>();
            for (Entry<String, String> entry : map.entrySet()) {
                propertyMap.put(entry.getKey(), Class.forName("java.lang.String"));
            }
            DynamicBean bean = new DynamicBean(propertyMap);
            BeanMap beanMap = bean.getBeanMap();
            for (Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                if (beanMap.containsKey(key)) {
                    beanMap.put(key, entry.getValue());
                } else {
                    String uncapitalizeKey = StringUtils.uncapitalize(key);
                    beanMap.put(uncapitalizeKey, entry.getValue());
                }
            }
            return (T) bean;
        }
        if (BaseBean.class.isAssignableFrom(clazz)) {
            final T bean = clazz.newInstance();

            ReflectionUtils.doWithFields(
                    clazz,
                    field -> {
                        if (null != map.get(field.getName())) {
                            try {
                                field.setAccessible(true);
                                Object value;
                                value =
                                        parseToActualValue(
                                                field,
                                                map.get(field.getName()),
                                                csvPath,
                                                headerTransfer);
                                field.set(bean, value);
                                field.setAccessible(false);
                            } catch (Exception e) {
                                log.error(
                                        String.format("生成类[%s]的实例时数据转换失败，使用字段默认值", clazz.getName()),
                                        e);
                            }
                        }
                    },
                    field -> {
                        int mod = field.getModifiers();
                        if (Modifier.isTransient(mod)) {
                            return false;
                        } else if (Modifier.isFinal(mod)) {
                            return false;
                        } else if (Modifier.isStatic(mod)) {
                            return false;
                        } else if (Modifier.isVolatile(mod)) {
                            return false;
                        } else {
                            return true;
                        }
                    });
            return bean;
        }
        String errMsg = String.format("生成类[%s]失败，无法解析该类型", clazz.getName());
        throw new RuntimeException(errMsg);
    }

    private Object parseToActualValue(
            Field field, String value, CsvPath csvPath, boolean headerTransfer) throws Exception {
        value = null == value ? value : value.trim();

        Object actualValue;
        String typeName = field.getType().getSimpleName();
        if (typeName.equals("short") || typeName.equals("Short")) {
            value = (null == value || "".equals(value)) ? "0" : value;
            actualValue = Short.parseShort(value);
        } else if (typeName.equals("int") || typeName.equals("Integer")) {
            value = (null == value || "".equals(value)) ? "0" : value;
            actualValue = Integer.parseInt(value);
        } else if (typeName.equals("long") || typeName.equals("Long")) {
            value = (null == value || "".equals(value)) ? "0" : value;
            actualValue = Long.parseLong(value);
        } else if (typeName.equals("float") || typeName.equals("Float")) {
            value = (null == value || "".equals(value)) ? "0" : value;
            actualValue = Float.parseFloat(value);
        } else if (typeName.equals("double") || typeName.equals("Double")) {
            value = (null == value || "".equals(value)) ? "0.00" : value;
            actualValue = Double.parseDouble(value);
        } else if (typeName.equals("boolean") || typeName.equals("Boolean")) {
            actualValue = "1".equals(value) || "true".equals(value);
        } else if (typeName.equals("char") || typeName.equals("Character")) {
            assert value != null;
            actualValue = value.charAt(0);
        } else if (typeName.equals("String")) {
            actualValue = value;
        } else if (typeName.equals("Date")) {
            actualValue = parseToDate(value);
        } else if (typeName.equals("BigDecimal")) {
            value = (null == value || "".equals(value)) ? "0.00" : value;
            actualValue = new BigDecimal(value);
        } else if (List.class.isAssignableFrom(field.getType())) {
            actualValue = parseToList(field, value, csvPath, headerTransfer);
        } else {
            String errMsg = String.format("目标类的属性field=[%s]目前尚未支持解析", field.getType().getName());
            throw new RuntimeException(errMsg);
        }
        return actualValue;
    }

    private Object parseToDate(String string) throws ParseException {
        Date date;
        if (null == string || string.trim().isEmpty()) {
            date = null;
        } else {
            DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setLenient(false);
            date = sdf.parse(string.trim());
        }
        return date;
    }

    /**
     * 支持List&ltString&gt,List&ltObject&gt,List&ltList&gt,List&ltMap&gt<br>
     * 其中，List&ltObject&gt中的Object表示Result,Config等自定义类型&ltbr&gt
     * List&ltList&gt中的List表示针对String和Object（继承自Object的自定义类型）<br/&>
     * List&ltMap&gt中的Map表示&ltString, String&gt;&ltString, Object&gt<br>
     * <br>
     * 配置示例： List&ltString&gt="1,2,3..."<br>
     * List&ltList&ltString&gt&gt="1,2,3...|1,2,3...|..."<br>
     * List&ltObject&gt或者List&ltMap&ltString,Object&gt<br>
     * = "key11=val11,key12=val12|key21=val21,key22=val22|key31=val31,key32=val32..." <br>
     *
     * @param field 被解析字段
     * @param value 字段的配置值
     * @param csvPath 顶级路径信息
     * @param headerTransfer 是否采用header转义
     * @return 目标结果
     * @throws Exception
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private Object parseToList(Field field, String value, CsvPath csvPath, boolean headerTransfer)
            throws Exception {
        if (null == value || value.isEmpty()) {
            return null;
        }
        Object actualValue;
        RegexResult result = RegexSupport.matchMultiCSVFile(value);
        boolean isCSVFile = result.isMatched();
        String basePath = csvPath.getPath().substring(0, csvPath.getPath().lastIndexOf("/") + 1);
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) { // 一层泛型参数
            ParameterizedType oneParamType = (ParameterizedType) genericType;
            Type[] oneTypes = oneParamType.getActualTypeArguments();
            Class oneClazz = null;
            if (oneTypes[0] instanceof Class) { // 如果参数是Class的实例，示例：
                // List<String>
                oneClazz = (Class) oneTypes[0];
                List allList = new ArrayList();
                if (isCSVFile) {
                    String[] filePaths = value.split("\\|");
                    List list;
                    for (String filePath : filePaths) {
                        String path = basePath + filePath;
                        list = parse(oneClazz, path, headerTransfer);
                        if (null != list && list.size() != 0) {
                            allList.addAll(list);
                        }
                    }
                } else { // 只对简单值类型（比如int,Integer），如果是自定义类是不支持的
                    String[] eles = value.split(",");
                    for (String ele : eles) {
                        allList.add(oneClazz.cast(ele));
                    }
                }
                actualValue = allList;
            } else if (oneTypes[0] instanceof ParameterizedType) { // 二层泛型参数
                ParameterizedType twoParamType = (ParameterizedType) oneTypes[0];
                oneClazz = (Class) twoParamType.getRawType();
                Type[] twoTypes = twoParamType.getActualTypeArguments();
                Class twoClazz = null;
                if (List.class.isAssignableFrom(oneClazz)) { // 如果参数是List，示例：List<List<String>>
                    if (twoTypes[0] instanceof Class) {
                        twoClazz = (Class) twoTypes[0];
                        List<List> allList = new ArrayList<List>();
                        if (isCSVFile) { // 如果配置了List<List<String>>目前是不支持的，需要调整 [lm's ps bug
                            // 20150824]
                            String[] filePaths = value.split("\\|");
                            List list;
                            for (String filePath : filePaths) {
                                String path = basePath + filePath;
                                list = parse(oneClazz, path, headerTransfer);
                                if (null != list && list.size() != 0) {
                                    allList.add(list);
                                }
                            }
                        } else {
                            String[] eles = value.split("\\|");
                            for (String ele : eles) {
                                List list = new ArrayList();
                                String[] els = ele.split(",");
                                for (String el : els) {
                                    list.add(twoClazz.cast(el));
                                }
                                allList.add(list);
                            }
                        }
                        actualValue = allList;
                    } else { // 不支持二层泛型参数以上的解析
                        String errMsg =
                                String.format(
                                        "目标类的属性field=[%s]目前尚未支持解析", field.getType().getName());
                        throw new RuntimeException(errMsg);
                    }
                } else if (Map.class.isAssignableFrom(oneClazz)) { // 如果参数是Map，示例：List<Map<String,
                    // String,
                    // String>.
                    if (twoTypes[1] instanceof Class) {
                        twoClazz = (Class) twoTypes[0];
                        List<Map> allList = new ArrayList<Map>();
                        if (isCSVFile) {
                            String[] filePaths = value.split("\\|");
                            List list;
                            for (String filePath : filePaths) {
                                String path = basePath + filePath;
                                list = parse(oneClazz, path, headerTransfer);
                                if (null != list && list.size() != 0) {
                                    allList.addAll(list);
                                }
                            }
                        } else {
                            String[] eles = value.split(":");
                            Map map;
                            String[] pairs;
                            for (String ele : eles) {
                                map = new HashMap();
                                String[] els = ele.split(",");
                                for (String el : els) {
                                    pairs = el.split("=");
                                    if (pairs.length == 1) {
                                        map.put(pairs[0], twoClazz.cast(""));
                                    } else {
                                        if (pairs[1].contains("&dou")) {
                                            pairs[1] = pairs[1].replace("&dou", ",");
                                        }
                                        map.put(pairs[0], twoClazz.cast(pairs[1]));
                                    }
                                }
                                allList.add(map);
                            }
                        }
                        actualValue = allList;
                    } else { // 不支持二层泛型参数以上的解析
                        String errMsg =
                                String.format(
                                        "目标类的属性field=[%s]目前尚未支持解析", field.getType().getName());
                        throw new RuntimeException(errMsg);
                    }
                } else { // 不支持List<Class>,List<List>,List<Map>之外的解析
                    String errMsg =
                            String.format("目标类的属性field=[%s]目前尚未支持解析", field.getType().getName());
                    throw new RuntimeException(errMsg);
                }
            } else {
                String errMsg =
                        String.format("目标类的属性field=[%s]目前尚未支持解析", field.getType().getName());
                throw new RuntimeException(errMsg);
            }
        } else {
            String errMsg = String.format("目标类的属性field=[%s]目前尚未支持解析", field.getType().getName());
            throw new RuntimeException(errMsg);
        }
        return actualValue;
    }
}
