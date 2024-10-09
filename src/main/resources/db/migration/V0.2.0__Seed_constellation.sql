-- 별자리 데이터 seeding

DELIMITER //

DROP PROCEDURE IF EXISTS InsertConstellationIfNotExists //

CREATE PROCEDURE InsertConstellationIfNotExists()
BEGIN
    DECLARE constellation_row_count INT;

    SELECT
        COUNT(*) INTO constellation_row_count
    FROM
        tars.constellation;

    IF constellation_row_count = 0 THEN

        INSERT INTO
            tars.constellation_type (constellation_type_id, constellation_type_name)
        VALUES
            (1, '육체활동'),
            (2, '마음강화'),
            (3, '지식습득'),
            (4, '기록 습관'),
            (5, '휴식ㅣ취미');

        INSERT INTO
            tars.constellation (
            constellation_id,
            constellation_name,
            constellation_star_count,
            constellation_identity,
            constellation_story,
            constellation_character_image,
            constellation_image,
            constellation_main_image,
            constellation_type_id
        )
        VALUES
            (
                1,
                '타스',
                7,
                '돕는 일에 행복을 느끼는 별',
                '올해 2.4억살로 아직 어린이 별이다. 친구들과 맺는 약속 에너지를 모아 어른 별로 진화를 기다리고 있다. 앞으로 40억년만 더 힘내자 :D',
                'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/character/Tars.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/Tars.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/grey/Tars_grey.webp',
				5
			),
			(
				2,
				'치타코기',
				10,
				'달릴 때 행복한 치타',
				'달리는 순간엔 행복하지만, 다리가 짧아 슬프다. 끄뉵치에게 영업 당해 다리 길어지는 운동을 배운다고 하는데 효과는 그다지 없는 듯 하다.',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/character/Cheetah.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/Cheetah.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/grey/Cheetah_grey.webp',
				1
			),
			(
				3,
				'제트부기',
				10,
				'한계에 도전하는 거북이',
				'빠른 세상을 동경해서 자전거를 타기 시작했다. 겁이 많아 10년 째 세 발 자전거를 벗어나지 못하지만 "내년엔 두발 자전거 탈거야!"가 입버릇이다.',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/character/Turtle.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/Turtle.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/grey/Turtle_grey.webp',
				1
			),
			(
				4,
				'잉어퀸',
				7,
				'알뜰히 소비하는 금붕어',
				'많이들 그녀의 기억력을 30초라고 알고 있지만, 사실 3 달 가량의 기억력을 가지고 있다. 분기별로 가계부를 쓰는 그보다 경제 관념이 좋은 친구는 흔치 않다.',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/character/Goldfish.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/Goldfish.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/grey/Goldfish_grey.webp',
				2
			),
			(
				5,
				'용용이',
				8,
				'노는 걸 좋아하는 공룡',
				'예전엔 펭귄이랑 자주 놀았었는데 자격증 공부를 시작하고서 얼굴을 잘 비추지 않는 그에게 서운하다. 먼 마을에 공룡이랑 잘 놀아주는 소드마스터 고길X이 산다는 소식이 그를 설레게 한다.',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/character/Dino.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/Dino.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/grey/Dino_grey.webp',
				5
			),
			(
				6,
				'스터비',
				8,
				'꾸준히 공부하는 꿀벌',
				'꿀의 경제학을 공부하는 대학원생이다. 연구 주제는 잠든 사이 매번 꿀을 털어가는 "보이지 않는 손"이라고 한다. 쌍쌀벌 교수의 노예라는 소문이 돈다.',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/character/Bee.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/Bee.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/grey/Bee_grey.webp',
				3
			),
			(
				7,
				'메라텔',
				9,
				'마음을 다스리는 오소리',
				'일단 들이받고 보는 불같은 성격을 가지고 있다. 거친 성정과 달리 마음씨가 따듯해 무당벌레를 가장 많이 구해주었다. 최근에는 명상을 시작했다고 한다.',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/character/Mandarin.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/Mandarin.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/grey/Mandarin_grey.webp',
				4
			),
			(
				8,
				'따라앵',
				11,
				'모든 언어를 알고 싶은 앵무새',
				'호기심이 많고 따라하길 좋아한다. 아는 언어가 30개가 넘는다고 자랑하는데 막상 할 줄 아는 건 안녕하세요 말고 별로 없는 것 같다. 놀랍게도 고래앵과 친척이다.',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/character/Parrot.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/Parrot.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/grey/Parrot_grey.webp',
				3
			),
			(
				9,
				'닭둘기',
				10,
				'여유롭고 느긋한 비둘기',
				'날개가 있으면서 굳이 걷는 걸 더 좋아한다. 살이 쪄서 날기 힘든 게 아닐까? 정신줄을 놓고 있다가 미어캣한테 잡혀 산으로 종종 끌려 간다',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/character/Pigeon.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/Pigeon.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/grey/Pigeon_grey.webp',
				1
			),
			(
				10,
				'누으마',
				11,
				'안락함에 취한 말',
				'어디서든 드러눕고 본다. 눕고도 싶고 달리고도 싶을 때는 옆으로 누워 허공에 발길질 하는 모습을 볼 수 있다. 멸치를 볼 때마다 운동 해야겠다고 생각하지만 막상 누우면 아무 생각이 없어진다.',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/character/Horse.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/Horse.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/grey/Horse_grey.webp',
				5
			);

		INSERT INTO
			tars.constellation (
				constellation_id,
				constellation_name,
				constellation_star_count,
				constellation_identity,
				constellation_story,
				constellation_character_image,
				constellation_image,
				constellation_main_image,
				constellation_type_id
			)
		VALUES
			(
				11,
				'뀨르 14세',
				8,
				'세상을 정복하고 싶은 귤',
				'뀨르뀨르 행성에서 온 외계귤. 인간들은 생각한다. 겨울철이 되면 따듯한 이불 안에서 우리를 자바먹는 게 최고라고. 하지만 너희가 귤을 자바머글 수록 우리는 퍼져나가 너희를 지배하게 될 거시다! 햐햐햐!',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/character/Octopus.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/Octopus.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/grey/Octopus_grey.webp',
				5
			),
			(
				12,
				'기프트',
				7,
				'친절을 베푸는 선물 상자',
				'한 달 동안 감사 표현을 가장 많이한 친구에게 선물을 준다. 울고 있을 때 매번 친구들에게 구해지는 무당벌레가 제일 많이 받지 않았을까 싶다.',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/character/Gift.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/Gift.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/grey/Gift_grey.webp',
				4
			),
			(
				13,
				'귄귄이',
				13,
				'포기를 모르는 펭귄',
				'딱따구리를 따라 나무 파기 기사를 준비하고 있다. 3년째 실기 낙방 중인데 크고 단단한 부리를 다른 곳에 쓰면 더 좋지 않을까? 누군가 알려주면 좋겠다.',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/character/Penguin.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/Penguin.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/grey/Penguin_grey.webp',
				3
			),
			(
				14,
				'고래앵',
				10,
				'춤 출 때 살아있음을 느끼는 고래',
				'친구들이 하도 칭찬을 많이 해서 이젠 댄스 크루의 수장이 되었다. 그의 춤은 멋지지만, 튀는 물에 온 몸이 젖을 각오를 하고 봐야 한다. 친척인 따라앵이 매번 공연에 와 응원 해준다.',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/character/Whale.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/Whale.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/grey/Whale_grey.webp',
				4
			),
			(
				15,
				'고든람쥐',
				10,
				'미식을 사랑하는 다람쥐',
				'요리를 좋아하지만 매번 레시피를 잊어서 기록을 시작했다. 맛없는 걸 먹으면 욕부터 하고 보는 성격에 받아주는 식당이 거의 없다.',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/character/Squirrel.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/Squirrel.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/grey/Squirrel_grey.webp',
				2
			),
			(
				16,
				'Dr.타코',
				14,
				'감정을 탐구하는 문어',
				'로봇 공학의 천재라고 불린다. 과학적이고 이성적인 것에는 자신 있지만, 감정은 도통 어렵다. 그는 최근 관심이 가는 친구가 있는데 이것이 사랑인지 혼란스러워 하고 있다.',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/character/Octopus.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/Octopus.png',
				'https://tars-image.s3.ap-northeast-2.amazonaws.com/constellation/grey/Octopus_grey.webp',
				2
			);

		INSERT INTO tars.constellation_svg (constellation_id,fill,stroke,stroke_width,opacity,width,height,view_box,`path`) VALUES
			 (1,'#0176F9','#fff',2.00,0.60,360.00,494.00,'0 0 360 494','M127 99.6738L182 252.174L325.5 169.674 M33.5 282.674L182 252.174L192 290.174L179.5 394.674 M192 290.674L296.5 371.674'),
			 (2,'#0176F9','#fff',2.00,0.60,360.00,565.00,'0 0 360 565','M205.5 361.5L182 288.5M182 288.5L239 212.5L241.5 103M182 288.5L167.5 395.5M167.5 395.5L182 288.5 M82 348L182 288.176L258.5 301.5L293.5 265.5 M182 288.5L293 265.5 M167.5 396L205.5 361.5 M74.5 295.5L82 348L47.5 369.5'),
			 (3,'#0176F9','#fff',2.00,0.60,312.00,483.00,'0 0 360 483','M197 100.5L162.5 222.5L239 242 M113.5 271.5L162.5 222.5L209.5 288.5L179.5 389.676 M113.5 271.5L209.5 288.5 M108.5 59.5L197 100'),
			 (4,'#0176F9','#fff',2.00,0.60,360.00,494.00,'0 0 360 494','M239.5 242L268.5 192 M117.5 295.5L64 230 M326 169.5L268.5 192 M62 261.5L89.25 278.75L116.5 296L203 270.5L239.5 242 M64.5 230L61.5 262'),
			 (5,'#0176F9','#fff',2.00,0.60,360.00,423.00,'0 0 360 423','M218.5 76.5L165 204.5L145.5 241 M145.5 241L192 255.174L115.5 356.5 M192 255.674L208.5 367.5 M192 255.674L165 204 M301 357L208 367.5 M301 357L268 267.5L192 255.674'),
			 (6,'#0176F9','#fff',2.00,0.60,360.00,494.00,'0 0 360 494','M182 252.174L284.5 207.5 M182 252.174L192 290.174L132.5 366.5 M192 290.674L204 374.5 M102 305L142.5 312.5'),
			 (7,'#0176F9','#fff',2.00,0.60,360.00,460.00,'0 0 360 460','M192.5 176.5L182 271L303 192.5 M99.5 294.5L182 271L246.5 329.5L189.5 351.5 M246.5 329.5L314 343.5 M99.5 294.5L142 342L190 351.5'),
			 (8,'#0176F9','#fff',2.00,0.60,360.00,535.00,'0 0 360 535','M82 124L182 272.5L300 320 M107.5 376.5L182 273.174L238.5 401L184.5 431.5 M240 399.5L300 320 M310 299L305 168 M310 299L300.5 320'),
			 (9,'#0176F9','#fff',2.00,0.60,360.00,494.00,'0 0 360 494','M172.5 106.5L182 252.174L313.5 239.5 M77.5 273.5L182 252.174L249.5 291L226.5 340.5 M226.5 340.5L202 386.5 M79 223L77.5 274 M79.5 223L182 252 M172.5 106.5L104.5 126.5'),
			 (10,'#0176F9','#fff',2.00,0.60,360.00,536.00,'0 0 360 536','M171.5 116.5L191.5 311 M197.5 404L165.5 372.5L192 311.176L118.5 301 M280 357L265.5 384.5 M277.5 250.5L314.5 326 M277.5 250.5L289 238.5 M165 372.5L118.5 301 M76.5 150.5L118.5 301');
        INSERT INTO tars.constellation_svg (constellation_id, fill,stroke,stroke_width,opacity,width,height,view_box,`path`) VALUES
                                                                                                                                 (11,'#0176F9','#fff',2.00,0.60,360.00,494.00,'0 0 360 494','M182 252.174L333.5 229.5 M35.5 273.5L182 252.174L110.5 378.5 M182 252.5L273.5 359.5 M181 109L184.5 171.5 M333.5 230L274 359.5'),
                                                                                                                                 (12,'#0176F9','#fff',2.00,0.60,360.00,444.00,'0 0 360 444','M57.5 183.5L179.5 213L314 149 M57.5 183.5L180.5 118L314 149 M179.5 213L194.5 351.5L82.5 311.5 M314 151L314.5 291.5 M195 351.5L314.5 291.5 M57 183L82.5 311.5'),
                                                                                                                                 (13,'#0176F9','#fff',2.00,0.60,360.00,509.00,'0 0 360 509','M45.5 283.5L56.5 267.5 M228.5 392.5L311 328 M185.5 97H254L266.5 121.5L309.5 144.5L287 146.5 M185.5 97V141L266.5 121.5L309.5 144.5L287 146.5 M45.5 283.5L192 298.174L152 387 M192 298.674L310.5 328.5'),
                                                                                                                                 (14,'#0176F9','#fff',2.00,0.60,360.00,474.00,'0 0 360 474','M182.5 242.5L255.5 84.5 M182.5 242.5L115.5 239.5L160 313.5L220.5 385 M221 385L269.5 417.5 M221 385L210.5 434.5 M202.5 40L255.5 84.5'),
                                                                                                                                 (15,'#0176F9','#fff',2.00,0.60,360.00,494.00,'0 0 360 494','M100 122L127 120.5L189.5 123.5 M38.5 236.5L127.5 121L172.5 246.5L140.5 291 M140 291L261 363.5 M140 291L122.5 404.5 M140 291L100 266 M39.5 237L100 266'),
                                                                                                                                 (16,'#0176F9','#fff',2.00,0.60,360.00,452.00,'0 0 360 452','M127 87.6758L257.5 42.5L312.5 125 M197 189L137.5 314L165 360 M17 220.5L43 138L82 109.5 M198.5 190.5L281.5 278.674 M281.5 278.674L226.5 332.5L165.5 360.5 M313 125L293.5 163.5 M127.5 86L197 190.5 M82.5 109.5L126 87M17 220.5L91.5 294');

        INSERT INTO tars.constellation_circle (constellation_circle_id, constellation_id, cx, cy, r)
        VALUES
            (1, 1, 127.00, 99.17, 6.00),
            (2, 1, 182.00, 252.17, 6.00),
            (3, 1, 192.00, 290.17, 6.00),
            (4, 1, 325.50, 169.67, 3.50),
            (5, 1, 296.50, 371.67, 3.50),
            (6, 1, 179.50, 394.67, 3.50),
            (7, 1, 33.50, 282.67, 3.50),
            (8, 2, 82.00, 348.00, 6.00),
            (9, 2, 182.00, 288.18, 6.00),
            (10, 2, 239.00, 212.00, 6.00);

        INSERT INTO
            tars.constellation_circle (constellation_circle_id, constellation_id, cx, cy, r)
        VALUES
            (11, 2, 241.50, 102.50, 3.50),
            (12, 2, 293.50, 265.50, 3.50),
            (13, 2, 258.50, 301.50, 3.50),
            (14, 2, 205.50, 361.50, 3.50),
            (15, 2, 167.50, 395.50, 3.50),
            (16, 2, 47.50, 369.50, 3.50),
            (17, 2, 74.50, 295.50, 3.50),
            (18, 3, 197.00, 100.00, 6.00),
            (19, 3, 92.00, 201.00, 6.00),
            (20, 3, 239.00, 242.00, 6.00);

        INSERT INTO
            tars.constellation_circle (constellation_circle_id, constellation_id, cx, cy, r)
        VALUES
            (21, 3, 180.00, 390.00, 6.00),
            (22, 3, 162.50, 222.50, 3.50),
            (23, 3, 97.50, 78.50, 3.50),
            (24, 3, 108.50, 59.50, 3.50),
            (25, 3, 113.50, 271.50, 3.50),
            (26, 3, 209.50, 288.50, 3.50),
            (27, 3, 71.50, 328.50, 3.50),
            (28, 4, 239.00, 242.00, 6.00),
            (29, 4, 64.00, 230.00, 6.00),
            (30, 4, 117.00, 296.00, 6.00);

        INSERT INTO
            tars.constellation_circle (constellation_circle_id, constellation_id, cx, cy, r)
        VALUES
            (31, 4, 325.50, 169.67, 3.50),
            (32, 4, 202.50, 270.50, 3.50),
            (33, 4, 268.50, 191.50, 3.50),
            (34, 4, 61.50, 261.50, 3.50),
            (35, 5, 165.00, 204.00, 6.00),
            (36, 5, 301.00, 357.00, 6.00),
            (37, 5, 192.00, 255.17, 6.00),
            (38, 5, 218.50, 76.50, 3.50),
            (39, 5, 145.50, 240.50, 3.50),
            (40, 5, 267.50, 267.50, 3.50);

        INSERT INTO
            tars.constellation_circle (constellation_circle_id, constellation_id, cx, cy, r)
        VALUES
            (41, 5, 208.50, 367.50, 3.50),
            (42, 5, 115.50, 356.50, 3.50),
            (43, 6, 122.00, 105.00, 6.00),
            (44, 6, 182.00, 252.00, 6.00),
            (45, 6, 182.00, 90.00, 6.00),
            (46, 6, 102.00, 305.00, 6.00),
            (47, 6, 284.50, 207.50, 3.50),
            (48, 6, 204.50, 374.50, 3.50),
            (49, 6, 132.50, 366.50, 3.50),
            (50, 6, 142.50, 312.50, 3.50);

        INSERT INTO
            tars.constellation_circle (constellation_circle_id, constellation_id, cx, cy, r)
        VALUES
            (51, 7, 97.00, 145.00, 6.00),
            (52, 7, 304.00, 192.00, 6.00),
            (53, 7, 182.00, 271.00, 6.00),
            (54, 7, 314.00, 343.00, 6.00),
            (55, 7, 192.50, 176.50, 3.50),
            (56, 7, 246.50, 329.50, 3.50),
            (57, 7, 189.50, 351.50, 3.50),
            (58, 7, 142.50, 342.50, 3.50),
            (59, 7, 99.50, 294.50, 3.50),
            (60, 8, 187.00, 188.00, 6.00);

        INSERT INTO
            tars.constellation_circle (constellation_circle_id, constellation_id, cx, cy, r)
        VALUES
            (61, 8, 305.00, 168.00, 6.00),
            (62, 8, 310.00, 299.00, 6.00),
            (63, 8, 300.00, 320.00, 6.00),
            (64, 8, 239.00, 401.00, 6.00),
            (65, 8, 182.00, 273.17, 6.00),
            (66, 8, 36.00, 178.00, 6.00),
            (67, 8, 184.50, 212.50, 3.50),
            (68, 8, 107.50, 376.50, 3.50),
            (69, 8, 184.50, 431.50, 3.50),
            (70, 8, 81.50, 123.50, 3.50);

        INSERT INTO
            tars.constellation_circle (constellation_circle_id, constellation_id, cx, cy, r)
        VALUES
            (71, 9, 182.00, 252.17, 6.00),
            (72, 9, 79.00, 223.00, 6.00),
            (73, 9, 250.00, 291.00, 6.00),
            (74, 9, 202.00, 386.00, 6.00),
            (75, 9, 313.50, 239.50, 3.50),
            (76, 9, 252.50, 362.50, 3.50),
            (77, 9, 226.50, 340.50, 3.50),
            (78, 9, 77.50, 273.50, 3.50),
            (79, 9, 172.50, 106.50, 3.50),
            (80, 9, 104.50, 126.50, 3.50);

        INSERT INTO
            tars.constellation_circle (constellation_circle_id, constellation_id, cx, cy, r)
        VALUES
            (81, 10, 172.00, 116.00, 6.00),
            (82, 10, 118.00, 301.00, 6.00),
            (83, 10, 192.00, 311.18, 6.00),
            (84, 10, 165.00, 372.00, 6.00),
            (85, 10, 289.00, 238.00, 6.00),
            (86, 10, 280.00, 357.00, 6.00),
            (87, 10, 277.50, 250.50, 3.50),
            (88, 10, 314.50, 326.50, 3.50),
            (89, 10, 265.50, 384.50, 3.50),
            (90, 10, 197.50, 403.50, 3.50);

        INSERT INTO
            tars.constellation_circle (constellation_circle_id, constellation_id, cx, cy, r)
        VALUES
            (91, 10, 76.50, 150.50, 3.50),
            (92, 11, 181.00, 109.00, 6.00),
            (93, 11, 182.00, 252.17, 6.00),
            (94, 11, 122.00, 156.00, 6.00),
            (95, 11, 333.50, 229.50, 3.50),
            (96, 11, 273.50, 359.50, 3.50),
            (97, 11, 184.50, 171.50, 3.50),
            (98, 11, 110.50, 378.50, 3.50),
            (99, 11, 35.50, 273.50, 3.50),
            (100, 12, 57.00, 183.00, 6.00);

        INSERT INTO
            tars.constellation_circle (constellation_circle_id, constellation_id, cx, cy, r)
        VALUES
            (101, 12, 314.00, 150.00, 6.00),
            (102, 12, 180.00, 118.00, 6.00),
            (103, 12, 179.50, 212.50, 3.50),
            (104, 12, 314.50, 291.50, 3.50),
            (105, 12, 194.50, 351.50, 3.50),
            (106, 12, 82.50, 311.50, 3.50),
            (107, 13, 185.00, 97.00, 6.00),
            (108, 13, 185.00, 141.00, 6.00),
            (109, 13, 192.00, 299.00, 6.00),
            (110, 13, 287.00, 146.00, 6.00);

        INSERT INTO
            tars.constellation_circle (constellation_circle_id, constellation_id, cx, cy, r)
        VALUES
            (111, 13, 309.50, 144.50, 3.50),
            (112, 13, 254.50, 96.50, 3.50),
            (113, 13, 266.50, 121.50, 3.50),
            (114, 13, 282.50, 224.50, 3.50),
            (115, 13, 310.50, 328.50, 3.50),
            (116, 13, 151.50, 387.50, 3.50),
            (117, 13, 45.50, 283.50, 3.50),
            (118, 13, 228.50, 392.50, 3.50),
            (119, 13, 56.50, 267.50, 3.50),
            (120, 14, 122.00, 212.00, 6.00);

        INSERT INTO
            tars.constellation_circle (constellation_circle_id, constellation_id, cx, cy, r)
        VALUES
            (121, 14, 160.00, 313.00, 6.00),
            (122, 14, 202.00, 40.00, 6.00),
            (123, 14, 221.00, 385.00, 6.00),
            (124, 14, 255.50, 84.50, 3.50),
            (125, 14, 210.50, 434.50, 3.50),
            (126, 14, 269.50, 417.50, 3.50),
            (127, 14, 115.50, 239.50, 3.50),
            (128, 14, 21.50, 171.50, 3.50),
            (129, 14, 182.50, 242.50, 3.50),
            (130, 15, 127.00, 121.00, 6.00);

        INSERT INTO
            tars.constellation_circle (constellation_circle_id, constellation_id, cx, cy, r)
        VALUES
            (131, 15, 140.00, 291.00, 6.00),
            (132, 15, 325.00, 204.00, 6.00),
            (133, 15, 38.00, 237.00, 6.00),
            (134, 15, 189.50, 123.50, 3.50),
            (135, 15, 260.50, 363.50, 3.50),
            (136, 15, 122.50, 404.50, 3.50),
            (137, 15, 99.50, 121.50, 3.50),
            (138, 15, 172.50, 246.50, 3.50),
            (139, 15, 99.50, 265.50, 3.50),
            (140, 16, 127.00, 87.18, 6.00);

        INSERT INTO
            tars.constellation_circle (constellation_circle_id, constellation_id, cx, cy, r)
        VALUES
            (141, 16, 67.00, 350.00, 6.00),
            (142, 16, 197.00, 190.00, 6.00),
            (143, 16, 282.00, 279.00, 6.00),
            (144, 16, 165.00, 360.00, 6.00),
            (145, 16, 257.50, 42.50, 3.50),
            (146, 16, 312.50, 124.50, 3.50),
            (147, 16, 293.50, 163.50, 3.50),
            (148, 16, 226.50, 332.50, 3.50),
            (149, 16, 137.50, 313.50, 3.50),
            (150, 16, 82.50, 109.50, 3.50);

        INSERT INTO
            tars.constellation_circle (constellation_circle_id, constellation_id, cx, cy, r)
        VALUES
            (151, 16, 16.50, 220.50, 3.50),
            (152, 16, 42.50, 137.50, 3.50),
            (153, 16, 91.50, 294.50, 3.50);

    END IF;
END //

DELIMITER ;

CALL InsertConstellationIfNotExists();
