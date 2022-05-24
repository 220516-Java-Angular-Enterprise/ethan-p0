-- DDL: CREATE, DROP, ALTER, COMMENT, RENAME
drop table if exists adventurers;
drop table if exists stores;
drop table if exists orders;
drop table if exists items;
drop table if exists order_history;


create table adventurers (
	id varchar not null,
	advName varchar not null,
	password varchar not null,
	advRole varchar not null,
	usrRole varchar not null,
	
	constraint pk_adventurers_id
		primary key (id)
);
create table stores (
	id varchar not null,
	storeName varchar not null,
	storeType varchar not null,
	
	constraint pk_stores_id
		primary key (id)
);
create table items (
	id varchar not null,
	itemName varchar not null,
	itemType varchar not null,
	cost int not null,
	inventory int not null,
	store_id varchar not null,
	
	constraint pk_items_id
		primary key (id)
);
create table orders (
	id varchar not null,
	time varchar not null,
	purchased boolean not null,
	quantity int not null,
	item_id varchar not null,
	adv_id varchar not null,
	
	constraint pk_orders_id
		primary key (id)
);
create table order_history (
	id varchar not null,
	time varchar not null,
	order_id varchar not null,
	
	constraint pk_order_history_id
		primary key (id)
);


alter table items
	add constraint fk_stores
		foreign key (store_id) references stores (id);
alter table orders
	add constraint fk_items
		foreign key (item_id) references items (id),
	add constraint fk_adv
		foreign key (adv_id) references adventurers (id);	
alter table order_history
	add constraint fk_orders
		foreign key (order_id) references orders (id);


	
		