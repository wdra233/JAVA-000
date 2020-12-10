DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
     `member_id` bigint DEFAULT NULL COMMENT 'member_id',
     `order_sn` char(32) DEFAULT NULL COMMENT '订单号',
     `coupon_id` bigint DEFAULT NULL COMMENT '使用的优惠券',
     `create_time` datetime DEFAULT NULL COMMENT 'create_time',
     `member_username` varchar(200) DEFAULT NULL COMMENT '用户名',
     `total_amount` decimal(18,4) DEFAULT NULL COMMENT '订单总额',
     `pay_amount` decimal(18,4) DEFAULT NULL COMMENT '应付总额',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8mb4 COMMENT='订单';
