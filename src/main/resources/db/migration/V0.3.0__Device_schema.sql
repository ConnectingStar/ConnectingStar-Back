create table device
(
    device_id              integer not null auto_increment,
    created_at             datetime(6),
    updated_at             datetime(6),
    fcm_registration_token varchar(255),
    is_fcm_token_active    bit default 1,
    owning_user_id         integer not null,
    primary key (device_id)
) engine = InnoDB;

alter table habit_alert
    add column user_id integer;

alter table user
    add column fcm_token varchar(255);

alter table device
    add constraint UK_2fsomn7q5rn7as2l1680rn990 unique (owning_user_id);

create index IX_HabitAlert_UserId on habit_alert (user_id);

create index IX_HabitAlert_AlertTime on habit_alert (alert_time);

create index IX_HabitHistory_RunHabitId_RunDate on habit_history (run_habit_id, run_date);