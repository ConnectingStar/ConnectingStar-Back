-- device.user_id (비정규화된 컬럼) 이 없는 기존 유저의 habitAlert.user_id를 업데이트한다.
-- 원본인 habit_alert.user_id에서 값을 가져온다

UPDATE habit_alert habitAlert
    SET user_id = (SELECT user_id
                   FROM run_habit runHabit
                   WHERE runHabit.run_habit_id = habitAlert.run_habit_id)
    WHERE user_id IS NULL