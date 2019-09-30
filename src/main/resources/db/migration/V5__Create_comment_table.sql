create table comment
(
	id bigint auto_increment,
	parent_id bigint not null,
	type int,
	commentator int not null,
	gmt_create BIGINT not null,
	gmt_modified bigint,
	like_count BIGINT default 0,
	constraint comment_pk
		primary key (id)
);