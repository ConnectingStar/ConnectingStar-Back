create table IF not exists tars.delete_account_reason
(
    delete_account_reason_id
    integer
    not
    null
    auto_increment,
    age_range
    enum
(
    'AGE_15',
    'AGE_20',
    'AGE_25',
    'AGE_30',
    'AGE_35',
    'AGE_40',
    'AGE_45',
    'AGE_50',
    'AGE_55'
), content varchar
(
    255
), created_dt varchar
(
    255
) not null, deleted_dt varchar
(
    255
) not null, gender_type enum
(
    'FEMALE',
    'MALE',
    'NONE'
), reason varchar
(
    255
), primary key
(
    delete_account_reason_id
) ) engine = InnoDB;
create table IF not exists tars.user_constellation
(
    user_constellation_id
    integer
    not
    null
    auto_increment,
    reg_yn
    bit
    not
    null,
    star_count
    integer
    not
    null,
    constellation_id
    integer,
    user_id
    integer,
    primary
    key
(
    user_constellation_id
) ) engine = InnoDB;
create table IF not exists alert_stop
(
    aler_stop_id
    integer
    not
    null
    auto_increment,
    end_date
    date
    not
    null,
    start_date
    date
    not
    null,
    primary
    key
(
    aler_stop_id
) ) engine = InnoDB;
create table IF not exists constellation
(
    constellation_id
    integer
    not
    null
    auto_increment,
    constellation_character_image
    varchar
(
    255
) not null, constellation_identity varchar
(
    255
) not null, constellation_image varchar
(
    255
) not null, constellation_main_image varchar
(
    255
) not null, constellation_name varchar
(
    255
) not null, constellation_star_count integer not null, constellation_story varchar
(
    255
) not null, constellation_type_id integer, primary key
(
    constellation_id
) ) engine = InnoDB;
create table IF not exists constellation_circle
(
    constellation_circle_id
    integer
    not
    null
    auto_increment,
    cx
    decimal
(
    38,
    2
) not null, cy decimal
(
    38,
    2
) not null, r decimal
(
    38,
    2
) not null, constellation_id integer, primary key
(
    constellation_circle_id
) ) engine = InnoDB;
create table IF not exists constellation_svg
(
    constellation_id
    integer
    not
    null,
    fill
    varchar
(
    255
) not null, height decimal
(
    38,
    2
) not null, opacity decimal
(
    38,
    2
) not null, path varchar
(
    255
) not null, stroke varchar
(
    255
) not null, stroke_width decimal
(
    38,
    2
) not null, view_box varchar
(
    255
) not null, width decimal
(
    38,
    2
) not null, primary key
(
    constellation_id
) ) engine = InnoDB;
create table IF not exists constellation_type
(
    constellation_type_id
    integer
    not
    null
    auto_increment,
    constellation_type_name
    varchar
(
    255
) not null, primary key
(
    constellation_type_id
) ) engine = InnoDB;
create table IF not exists habit_alert
(
    habit_alert_id
    integer
    not
    null
    auto_increment,
    alert_order
    integer
    not
    null,
    alert_status
    bit
    not
    null,
    alert_time
    time
(
    6
) not null, run_habit_id integer, primary key
(
    habit_alert_id
) ) engine = InnoDB;
create table IF not exists habit_history
(
    habit_history_id
    integer
    not
    null
    auto_increment,
    created_at
    datetime
(
    6
), updated_at datetime
(
    6
), achievement integer not null, is_rest bit not null, review varchar
(
    400
) not null, run_date datetime
(
    6
) not null, run_place varchar
(
    255
) not null, run_value integer not null, run_habit_id integer not null, user_id integer not null, primary key
(
    habit_history_id
) ) engine = InnoDB;
create table IF not exists quit_habit
(
    quit_habit_id
    integer
    not
    null
    auto_increment,
    created_at
    datetime
(
    6
), updated_at datetime
(
    6
), action varchar
(
    255
) not null, place varchar
(
    255
) not null, quit_date datetime
(
    6
) not null, reason_of_quit varchar
(
    400
) not null, rest_value integer not null, run_time time
(
    6
) not null, start_date datetime
(
    6
) not null, unit varchar
(
    255
) not null, value integer not null, user_id integer not null, primary key
(
    quit_habit_id
) ) engine = InnoDB;
create table IF not exists run_habit
(
    run_habit_id
    integer
    not
    null
    auto_increment,
    created_at
    datetime
(
    6
), updated_at datetime
(
    6
), action varchar
(
    255
) not null, identity varchar
(
    255
) not null, place varchar
(
    255
) not null, run_time time
(
    6
) not null, unit varchar
(
    255
) not null, value integer not null, user_id integer not null, primary key
(
    run_habit_id
) ) engine = InnoDB;
create table IF not exists user
(
    user_id
    integer
    not
    null
    auto_increment,
    created_dt
    datetime
(
    6
) not null, updated_dt datetime
(
    6
) not null, age_range varchar
(
    255
), email varchar
(
    255
) not null, gender varchar
(
    255
), identity varchar
(
    255
), nickname varchar
(
    255
), onboard bit, referrer varchar
(
    255
), social_type varchar
(
    255
) not null, star integer not null, constellation_id integer, primary key
(
    user_id
) ) engine = InnoDB;
alter table user;
-- drop index UK_d45g5isp3f1w95fu571f99buk;
alter table user
    add constraint UK_d45g5isp3f1w95fu571f99buk unique (constellation_id);
alter table tars.user_constellation
    add constraint FK6eik7q0582mvv0w2btma0f8sq foreign key (constellation_id) references constellation (constellation_id);
alter table tars.user_constellation
    add constraint FK32t1xgkt2qs2w65r1i3krijal foreign key (user_id) references user (user_id);
alter table constellation
    add constraint FK3b526nsrrhbxm6bd4omdt34j6 foreign key (constellation_type_id) references constellation_type (constellation_type_id);
alter table constellation_circle
    add constraint FKqrvkmnk5fqwx8x2tsjrdiwfef foreign key (constellation_id) references constellation_svg (constellation_id);
alter table constellation_svg
    add constraint FKpgiucsjy7d0khmmq7an3kp1li foreign key (constellation_id) references constellation (constellation_id);
alter table habit_alert
    add constraint FKdw40ojiqjhb8xmg588h80ings foreign key (run_habit_id) references run_habit (run_habit_id);
alter table habit_history
    add constraint FKdrsnfc1pv9r3g62jnjwop0s0y foreign key (run_habit_id) references run_habit (run_habit_id);
alter table habit_history
    add constraint FKmmp83tus4efymfn4tye41wjt2 foreign key (user_id) references user (user_id);
alter table quit_habit
    add constraint FKhxvw6qq7em6yscivtj745dqun foreign key (user_id) references user (user_id);
alter table run_habit
    add constraint FK9a8fxdobvp1feeg2g3andrfd3 foreign key (user_id) references user (user_id);
alter table user
    add constraint FK88qg0xtwooam77oop2mx67v0k foreign key (constellation_id) references constellation (constellation_id);