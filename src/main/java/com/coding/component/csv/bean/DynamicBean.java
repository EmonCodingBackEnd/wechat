package com.coding.component.csv.bean;

import lombok.Data;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

import java.util.HashMap;
import java.util.Map;

@Data
public class DynamicBean {

    // 实体Object
    private Object Object = null;

    // 属性Map
    private BeanMap beanMap = null;

    private DynamicBean() {
        super();
    }

    public DynamicBean(Map propertyMap) {
        this.Object = generateBean(propertyMap);
        this.beanMap = BeanMap.create(Object);
    }

    private Object generateBean(Map propertyMap) {
        BeanGenerator generator = new BeanGenerator();
        BeanGenerator.addProperties(generator, propertyMap);
        return generator.create();
    }

    public Object getValue(String property) {
        return beanMap.get(property);
    }

    public void setValue(String property, Object value) {
        beanMap.put(property, value);
    }

    public static void main(String[] args) throws Exception {
        HashMap<String, Class> propertyMap = new HashMap<>();
        propertyMap.put("id", Class.forName("java.lang.Integer"));
        propertyMap.put("name", Class.forName("java.lang.String"));
        propertyMap.put("address", Class.forName("java.lang.String"));
        propertyMap.put("Rm_Any", Class.forName("java.lang.String"));

        DynamicBean bean = new DynamicBean(propertyMap);

        bean.setValue("id", 1);
        bean.setValue("name", "LiMing");
        bean.setValue("address", "滨江区江南大道伟业路");

        System.out.println(bean);
        System.out.println(bean.getValue("id"));
        System.out.println(bean.getValue("name"));
        System.out.println(bean.getValue("address"));
    }
}
