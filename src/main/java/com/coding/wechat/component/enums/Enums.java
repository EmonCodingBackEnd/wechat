/*
 * 文件名称：Enums.java
 * 系统名称：[系统名称]
 * 模块名称：枚举类集合
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180616 12:21
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180616-01         Rushing0711     M201806161221 新建文件
 ********************************************************************************/
package com.coding.wechat.component.enums;

/**
 * 枚举类集合.
 *
 * <p>
 *
 * <p>创建时间: <font style="color:#00FFFF">20180423 17:40</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Enums {

    /** 用户状态枚举类 */
    enum UserStatusEnum implements BaseEnum<String> {

        /** 1 - 有效 */
        VALID("1", "有效"),
        /** 0 - 无效. */
        INVALID("0", "无效"),
        ;

        private String code;

        private String msg;

        UserStatusEnum(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMsg() {
            return msg;
        }
    }

    /** 订单核销码状态枚举 */
    enum PurchaseGoodsStatus implements BaseEnum<String> {
        TOBE_VERIFICATION("0", "待核销"),

        VERIFICATIONED("1", "已核销"),

        REFUNDED("2", "已退款");

        private String code;

        private String msg;

        PurchaseGoodsStatus(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMsg() {
            return msg;
        }

        public static PurchaseGoodsStatus getByCode(String code) {
            if (code != null) {
                for (PurchaseGoodsStatus purchaseGoodsStatus : PurchaseGoodsStatus.values()) {
                    if (purchaseGoodsStatus.getCode().equals(code)) {
                        return purchaseGoodsStatus;
                    }
                }
            }
            return null;
        }

        public static PurchaseGoodsStatus getByMsg(String msg) {
            if (msg != null) {
                for (PurchaseGoodsStatus purchaseGoodsStatus : PurchaseGoodsStatus.values()) {
                    if (purchaseGoodsStatus.getMsg().equals(msg)) {
                        return purchaseGoodsStatus;
                    }
                }
            }
            return null;
        }
    }

    /** 订单状态枚举 */
    enum PurchaseStatus implements BaseEnum<String> {
        TO_BE_PAID("1", "待支付"),
        HAVE_TO_PAY("2", "已支付"),
        CLOSED("3", "已关闭"),
        HAS_BEEN_COMPLETED("4", "已完成"),
        HAVE_REFUND("5", "已退款");

        private String code;
        private String msg;

        PurchaseStatus(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMsg() {
            return msg;
        }

        public static PurchaseStatus getByCode(String code) {
            if (code != null) {
                for (PurchaseStatus purchaseStatus : PurchaseStatus.values()) {
                    if (purchaseStatus.getCode().equals(code)) {
                        return purchaseStatus;
                    }
                }
            }
            return null;
        }

        public static PurchaseStatus getByMsg(String msg) {
            if (msg != null) {
                for (PurchaseStatus purchaseStatus : PurchaseStatus.values()) {
                    if (purchaseStatus.getMsg().equals(msg)) {
                        return purchaseStatus;
                    }
                }
            }
            return null;
        }
    }

    /** 隔离有效无效数据的枚举 */
    enum Status implements BaseEnum<String> {
        INVALID("0", "无效"),
        VALID("1", "有效");

        private String code;
        private String msg;

        Status(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMsg() {
            return msg;
        }

        public static Status getByCode(String code) {
            if (code != null) {
                for (Status status : Status.values()) {
                    if (status.getCode().equals(code)) {
                        return status;
                    }
                }
            }
            return null;
        }

        public static Status getByMsg(String msg) {
            if (msg != null) {
                for (Status status : Status.values()) {
                    if (status.getMsg().equals(msg)) {
                        return status;
                    }
                }
            }
            return null;
        }
    }

    /** 积分流水类型枚举 */
    enum IntegralFlowType implements BaseEnum<String> {
        SIGN_IN("1", "签到"),
        COMMENTS("2", "评论"),
        SHARE("3", "分享"),
        AMOUNT_PAID("4", "金额支付"),
        INTEGRAL_CONSUMPTION("5", "积分商城消耗"),
        EXPIRE("6", "过期");

        private String code;
        private String msg;

        IntegralFlowType(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMsg() {
            return msg;
        }

        public static IntegralFlowType getByCode(String code) {
            if (code != null) {
                for (IntegralFlowType integralFlowType : IntegralFlowType.values()) {
                    if (integralFlowType.getCode().equals(code)) {
                        return integralFlowType;
                    }
                }
            }
            return null;
        }

        public static IntegralFlowType getByMsg(String msg) {
            if (msg != null) {
                for (IntegralFlowType integralFlowType : IntegralFlowType.values()) {
                    if (integralFlowType.getMsg().equals(msg)) {
                        return integralFlowType;
                    }
                }
            }
            return null;
        }
    }

    /** 订单类型枚举 */
    enum PurchaseType implements BaseEnum<String> {
        ORDINARY_PURCHASETYPE("1", "普通订单"),
        INTEGRAL_PURCHASETYPE("2", "积分商城订单");

        private String code;
        private String msg;

        PurchaseType(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMsg() {
            return msg;
        }

        public static PurchaseType getByCode(String code) {
            if (code != null) {
                for (PurchaseType purchaseType : PurchaseType.values()) {
                    if (purchaseType.getCode().equals(code)) {
                        return purchaseType;
                    }
                }
            }
            return null;
        }

        public static PurchaseType getByMsg(String msg) {
            if (msg != null) {
                for (PurchaseType purchaseType : PurchaseType.values()) {
                    if (purchaseType.getMsg().equals(msg)) {
                        return purchaseType;
                    }
                }
            }
            return null;
        }
    }

    /** 普通订单商品类型枚举 */
    enum PurchaseItemType implements BaseEnum<String> {
        ORDINARY_COURSE("1", "课程"),
        ORDINARY_ACTIVITY("2", "活动"),
        ORDINARY_COUPONS("3", "优惠卷"),
        ORDINARY_CONSUMERCARD("4", "消费卡"),
        ORDINARY_SPELL_GROUP("5", "拼团"),
        ORDINARY_TICKET("6", "门票");

        private String code;
        private String msg;

        PurchaseItemType(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMsg() {
            return msg;
        }

        public static PurchaseItemType getByCode(String code) {
            if (code != null) {
                for (PurchaseItemType purchaseItemType : PurchaseItemType.values()) {
                    if (purchaseItemType.getCode().equals(code)) {
                        return purchaseItemType;
                    }
                }
            }
            return null;
        }

        public static PurchaseItemType getByMsg(String msg) {
            if (msg != null) {
                for (PurchaseItemType purchaseItemType : PurchaseItemType.values()) {
                    if (purchaseItemType.getMsg().equals(msg)) {
                        return purchaseItemType;
                    }
                }
            }
            return null;
        }
    }

    /** 积分商城订单商品类型枚举 */
    enum IntegralPurcahseItemType implements BaseEnum<String> {
        INTEGRAL_PHYSICAL("1", "实物"),
        INTEGRAL_COUPONS("2", "优惠卷"),
        INTEGRAL_TICKET("3", "门票");

        private String code;
        private String msg;

        IntegralPurcahseItemType(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMsg() {
            return msg;
        }

        public static IntegralPurcahseItemType getByCode(String code) {
            if (code != null) {
                for (IntegralPurcahseItemType integralPurcahseItemType :
                        IntegralPurcahseItemType.values()) {
                    if (integralPurcahseItemType.getCode().equals(code)) {
                        return integralPurcahseItemType;
                    }
                }
            }
            return null;
        }

        public static IntegralPurcahseItemType getByMsg(String msg) {
            if (msg != null) {
                for (IntegralPurcahseItemType integralPurcahseItemType :
                        IntegralPurcahseItemType.values()) {
                    if (integralPurcahseItemType.getMsg().equals(msg)) {
                        return integralPurcahseItemType;
                    }
                }
            }
            return null;
        }
    }

    /**
     * banner枚举
     *
     * @author wtf 2018年5月7日
     */
    enum BannerType implements BaseEnum<String> {
        TYPE_COURSE("1", "课程"),
        TYPE_ACTIVITY("2", "活动"),
        TYPE_CONSUMERCARD("3", "消费卡"),
        TYPE_TICKET("4", "门票"),
        TYPE_CUSTOM("5", "自定义"),
        TYPE_NULL("6", "无");

        private String code;
        private String msg;

        BannerType(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMsg() {
            return msg;
        }

        public static BannerType getByCode(String code) {
            if (code != null) {
                for (BannerType bannerType : BannerType.values()) {
                    if (bannerType.getCode().equals(code)) {
                        return bannerType;
                    }
                }
            }
            return null;
        }

        public static BannerType getByMsg(String msg) {
            if (msg != null) {
                for (BannerType bannerType : BannerType.values()) {
                    if (bannerType.getMsg().equals(msg)) {
                        return bannerType;
                    }
                }
            }
            return null;
        }
    }

    /** 性别枚举 */
    enum SexType implements BaseEnum<String> {
        MEN("1", "男"),
        WOMEN("2", "女");

        private String code;
        private String msg;

        SexType(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public static SexType getSexTypeByCode(String code) {
            if (code != null) {
                for (SexType sexType : SexType.values()) {
                    if (sexType.getCode().equals(code)) {
                        return sexType;
                    }
                }
            }
            return null;
        }

        public static SexType getSexTypeByMsg(String msg) {
            if (msg != null) {
                for (SexType sexType : SexType.values()) {
                    if (sexType.getMsg().equals(msg)) {
                        return sexType;
                    }
                }
            }
            return null;
        }
    }

    /** 游乐园配套设施枚举 */
    enum SupportingFacilitiesType implements BaseEnum<String> {
        CHILDRENS_AMUSEMENT_PARK("1", "儿童游乐园"),
        ANIMALS_THEATER("2", "动物剧场"),
        FOOD_AND_BEVERAGE("3", "餐饮"),
        ZOO_SHOPPING("4", "zoo shopping"),
        ZOO_COFFEE("5", "zoo coffee");

        private String code;
        private String msg;

        SupportingFacilitiesType(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMsg() {
            return msg;
        }

        public static SupportingFacilitiesType getByCode(String code) {
            if (code != null) {
                for (SupportingFacilitiesType supportingFacilitiesType :
                        SupportingFacilitiesType.values()) {
                    if (supportingFacilitiesType.getCode().equals(code)) {
                        return supportingFacilitiesType;
                    }
                }
            }
            return null;
        }

        public static SupportingFacilitiesType getByMsg(String msg) {
            if (msg != null) {
                for (SupportingFacilitiesType supportingFacilitiesType :
                        SupportingFacilitiesType.values()) {
                    if (supportingFacilitiesType.getMsg().equals(msg)) {
                        return supportingFacilitiesType;
                    }
                }
            }
            return null;
        }
    }

    /** 热销商品的枚举类型 */
    enum HotCommodityType implements BaseEnum<String> {
        COURSETYPE("1", "课程"),
        ACTIVITYTYPE("2", "活动"),
        TICKTYPE("3", "门票"),
        CONSUMERCARD("4", "消费卡");

        HotCommodityType(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        private String code;
        private String msg;

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMsg() {
            return msg;
        }

        public static HotCommodityType getByCode(String code) {
            if (code != null) {
                for (HotCommodityType hotCommodityType : HotCommodityType.values()) {
                    if (hotCommodityType.getCode().equals(code)) {
                        return hotCommodityType;
                    }
                }
            }
            return null;
        }

        public static HotCommodityType getByMsg(String msg) {
            if (msg != null) {
                for (HotCommodityType hotCommodityType : HotCommodityType.values()) {
                    if (hotCommodityType.getMsg().equals(msg)) {
                        return hotCommodityType;
                    }
                }
            }
            return null;
        }
    }
}
