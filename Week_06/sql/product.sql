drop table if exists e_commerce.t_product_brand;
drop table if exists e_commerce.t_product_category;
drop table if exists e_commerce.t_category_brand_relation;
create table e_commerce.t_product_brand
(
   brand_id             bigint not null auto_increment comment '品牌id',
   name                 char(50) comment '品牌名',
   logo                 varchar(2000) comment '品牌logo地址',
   descript             longtext comment '介绍',
   show_status          tinyint comment '显示状态[0-不显示；1-显示]',
   first_letter         char(1) comment '检索首字母',
   sort                 int comment '排序',
   primary key (brand_id)
);

alter table e_commerce.t_product_brand comment '品牌';

/*==============================================================*/
/* Table: t_product_category                                         */
/*==============================================================*/
create table e_commerce.t_product_category
(
   cat_id               bigint not null auto_increment comment '分类id',
   name                 char(50) comment '分类名称',
   parent_cid           bigint comment '父分类id',
   cat_level            int comment '层级',
   show_status          tinyint comment '是否显示[0-不显示，1显示]',
   sort                 int comment '排序',
   icon                 char(255) comment '图标地址',
   product_unit         char(50) comment '计量单位',
   product_count        int comment '商品数量',
   primary key (cat_id)
);

alter table e_commerce.t_product_category comment '商品三级分类';

/*==============================================================*/
/* Table: e_commerce.t_category_brand_relation                         */
/*==============================================================*/
create table e_commerce.t_category_brand_relation
(
   id                   bigint not null auto_increment,
   brand_id             bigint comment '品牌id',
   catelog_id           bigint comment '分类id',
   brand_name           varchar(255),
   catelog_name         varchar(255),
   primary key (id)
);

alter table e_commerce.t_category_brand_relation comment '品牌分类关联';
