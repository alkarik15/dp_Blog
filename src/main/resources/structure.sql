CREATE TABLE tags (
  id int(11) NOT NULL,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AVG_ROW_LENGTH = 8192,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

--
-- Создать индекс `UK_t48xdq560gs3gap9g7jg36kgc` для объекта типа таблица `tags`
--
ALTER TABLE tags
ADD UNIQUE INDEX UK_t48xdq560gs3gap9g7jg36kgc (name);

--
-- Создать таблицу `users`
--
CREATE TABLE users (
  id int(11) NOT NULL,
  code varchar(255) DEFAULT NULL,
  email varchar(255) NOT NULL,
  is_moderator bit(1) NOT NULL,
  name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  photo text DEFAULT NULL,
  reg_time datetime NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AVG_ROW_LENGTH = 8192,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

--
-- Создать таблицу `posts`
--
CREATE TABLE posts (
  id int(11) NOT NULL,
  is_active bit(1) NOT NULL,
  moderation_status varchar(255) NOT NULL,
  text text NOT NULL,
  time datetime NOT NULL,
  title varchar(255) NOT NULL,
  view_count int(11) NOT NULL,
  moderator_id int(11) DEFAULT NULL,
  user_id int(11) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AVG_ROW_LENGTH = 8192,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

--
-- Создать внешний ключ
--
ALTER TABLE posts
ADD CONSTRAINT FK5lidm6cqbc7u4xhqpxm898qme FOREIGN KEY (user_id)
REFERENCES users (id);

--
-- Создать внешний ключ
--
ALTER TABLE posts
ADD CONSTRAINT FK6m7nr3iwh1auer2hk7rd05riw FOREIGN KEY (moderator_id)
REFERENCES users (id);

--
-- Создать таблицу `tag2post`
--
CREATE TABLE tag2post (
  post_id int(11) NOT NULL,
  tag_id int(11) NOT NULL,
  PRIMARY KEY (post_id, tag_id)
)
ENGINE = INNODB,
AVG_ROW_LENGTH = 8192,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

--
-- Создать внешний ключ
--
ALTER TABLE tag2post
ADD CONSTRAINT FKjou6suf2w810t2u3l96uasw3r FOREIGN KEY (tag_id)
REFERENCES tags (id);

--
-- Создать внешний ключ
--
ALTER TABLE tag2post
ADD CONSTRAINT FKpjoedhh4h917xf25el3odq20i FOREIGN KEY (post_id)
REFERENCES posts (id);

--
-- Создать таблицу `post_votes`
--
CREATE TABLE post_votes (
  id int(11) NOT NULL,
  time datetime NOT NULL,
  value tinyint(4) NOT NULL,
  post_id int(11) DEFAULT NULL,
  user_id int(11) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AVG_ROW_LENGTH = 16384,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

--
-- Создать внешний ключ
--
ALTER TABLE post_votes
ADD CONSTRAINT FK9jh5u17tmu1g7xnlxa77ilo3u FOREIGN KEY (post_id)
REFERENCES posts (id);

--
-- Создать внешний ключ
--
ALTER TABLE post_votes
ADD CONSTRAINT FK9q09ho9p8fmo6rcysnci8rocc FOREIGN KEY (user_id)
REFERENCES users (id);

--
-- Создать таблицу `post_comments`
--
CREATE TABLE post_comments (
  id int(11) NOT NULL,
  text varchar(4096) DEFAULT NULL,
  time datetime NOT NULL,
  parent_id int(11) DEFAULT NULL,
  post_id int(11) DEFAULT NULL,
  user_id int(11) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AVG_ROW_LENGTH = 2048,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

--
-- Создать внешний ключ
--
ALTER TABLE post_comments
ADD CONSTRAINT FKaawaqxjs3br8dw5v90w7uu514 FOREIGN KEY (post_id)
REFERENCES posts (id);

--
-- Создать внешний ключ
--
ALTER TABLE post_comments
ADD CONSTRAINT FKc3b7s6wypcsvua2ycn4o1lv2c FOREIGN KEY (parent_id)
REFERENCES post_comments (id);

--
-- Создать внешний ключ
--
ALTER TABLE post_comments
ADD CONSTRAINT FKsnxoecngu89u3fh4wdrgf0f2g FOREIGN KEY (user_id)
REFERENCES users (id);

--
-- Создать таблицу `hibernate_sequence`
--
CREATE TABLE hibernate_sequence (
  next_val bigint(20) DEFAULT NULL
)
ENGINE = INNODB,
AVG_ROW_LENGTH = 2340,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

--
-- Создать таблицу `global_settings`
--
CREATE TABLE global_settings (
  id int(11) NOT NULL,
  code varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AVG_ROW_LENGTH = 8192,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

--
-- Создать таблицу `captcha_codes`
--
CREATE TABLE captcha_codes (
  id int(11) NOT NULL,
  code varchar(255) NOT NULL,
  secret_code varchar(255) NOT NULL,
  time datetime NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AVG_ROW_LENGTH = 5461,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

-- 
-- Вывод данных для таблицы tags
--
INSERT INTO tags VALUES
(25, '2'),
(29, 'ss'),
(51, 'Короновирус'),
(52, 'Пентагон'),
(54, 'Призывник'),
(24, 'Публикация');

-- 
-- Вывод данных для таблицы users
--
INSERT INTO users VALUES
(13, NULL, 'alkarik@hiik.ru', False, 'alkarik@hiik.ru', 'password', 'avatar.jpg', '2020-02-13 01:51:28'),
(20, NULL, 'alkarik@ya.ru', True, 'alkarik@ya.ru', 'password', 'default.jpg', '2020-02-13 02:23:28'),
(60, NULL, 'test@liferay.com', False, 'test@liferay.com', 'test@liferay.com', 'avatar.jpg', '2020-02-14 05:31:25');

-- 
-- Вывод данных для таблицы posts
--
INSERT INTO posts VALUES
(18, False, 'NEW', 'Заголовок4 Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;Заголовок&nbsp;', '2020-02-13 04:59:35', 'Заголовок4 Заголовок Заголовок Заголовок Заголовок Заголовок Заголовок Заголовок ', 0, NULL, 13),
(26, True, 'ACCEPTED', '<div class="article__block" data-type="text" style="max-height: 1e+06px; box-sizing: inherit; margin-top: 0px; font-family: Montserrat, Arial, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);"><div class="article__text" style="max-height: 1e+06px; box-sizing: inherit; line-height: 26px; font-family: NotoSans, Arial, sans-serif;"><span style="max-height: 1e+06px; box-sizing: inherit; font-weight: 700;">МОСКВА, 14 фев - РИА Новости.</span>&nbsp;Новым&nbsp;<a href="https://ria.ru/category_rasprostranenie-novogo-koronavirusa/" target="_blank" style="max-height: 1e+06px; box-sizing: inherit; text-decoration-line: none; cursor: pointer; color: rgb(79, 41, 140); border-radius: 2px; padding: 0px 5px; white-space: pre-wrap; background: rgba(79, 41, 140, 0.12); position: relative;">коронавирусом</a>&nbsp;COVID-19 могут заразиться две трети населения Земли. Такое мнение, как сообщает&nbsp;<a href="https://www.bloomberg.com/news/articles/2020-02-13/coronavirus-could-infect-two-thirds-of-globe-researcher-says?srnd=premium-europe" target="_blank" style="max-height: 1e+06px; box-sizing: inherit; text-decoration-line: none; cursor: pointer; color: rgb(79, 41, 140); border-radius: 2px; padding: 0px 5px; white-space: pre-wrap; background: rgba(79, 41, 140, 0.12); position: relative;">Bloomberg</a>, высказал советник&nbsp;<a href="https://ria.ru/organization_Vsemirnaja_organizacija_zdravookhranenija" target="_blank" style="max-height: 1e+06px; box-sizing: inherit; text-decoration-line: none; cursor: pointer; color: rgb(79, 41, 140); border-radius: 2px; padding: 0px 5px; white-space: pre-wrap; background: rgba(79, 41, 140, 0.12); position: relative; margin-bottom: 0px;">Всемирной организации здравоохранения</a>, биостатист и эпидемиолог Айра Лонджини.</div></div><div class="article__block" data-type="text" style="max-height: 1e+06px; box-sizing: inherit; margin-top: 15px; font-family: Montserrat, Arial, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);"><div class="article__text" style="max-height: 1e+06px; box-sizing: inherit; line-height: 26px; font-family: NotoSans, Arial, sans-serif;">Модель, построенная Лонджини, основана на данных, согласно которым каждый инфицированный заражает двух или трех человек.</div></div><div class="article__block" data-type="text" style="max-height: 1e+06px; box-sizing: inherit; margin-top: 15px; font-family: Montserrat, Arial, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);"><div class="article__text" style="max-height: 1e+06px; box-sizing: inherit; line-height: 26px; font-family: NotoSans, Arial, sans-serif;">При этом, даже если будет найден способ вдвое замедлить скорость распространения вируса, инфицированными рискуют оказаться около 30% людей на планете, утверждает ученый.</div></div><div class="article__block" data-type="banner" data-position="desktop" style="max-height: 1e+06px; box-sizing: inherit; margin-top: 15px; position: relative; font-family: Montserrat, Arial, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);"><div class="banner m-article-body m-article-desktop" data-position="article_desktop_content-1" data-changed-id="adfox_desktop_body_1564684921" style="max-height: 1e+06px; box-sizing: inherit; position: relative;"><div class="banner__content" style="max-height: 1e+06px; box-sizing: inherit; position: relative;"></div></div><div class="banner__hidden" style="max-height: 40px; box-sizing: inherit; position: absolute; top: 0px; right: 0px; overflow: hidden; width: 40px; height: 0px; pointer-events: none;"><a class="banner__hidden-button" style="max-height: 1e+06px; box-sizing: inherit; cursor: pointer; color: rgb(51, 51, 51); display: block; position: absolute; top: 14px; right: 14px; width: 25px; height: 25px; border: 1px solid rgb(172, 172, 172); border-radius: 2px; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; z-index: 100; pointer-events: all;"></a></div></div><div class="article__block" data-type="text" style="max-height: 1e+06px; box-sizing: inherit; margin-top: 15px; font-family: Montserrat, Arial, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);"><div class="article__text" style="max-height: 1e+06px; box-sizing: inherit; line-height: 26px; font-family: NotoSans, Arial, sans-serif;">Карантинные меры могут замедлить распространение вируса. Однако эксперт отмечает, что коронавирус мог распространиться не только в Китае, но и за пределами страны, прежде чем власти КНР ввели необходимые меры.</div></div><div class="article__block" data-type="banner" data-position="1" style="max-height: 1e+06px; box-sizing: inherit; margin-top: 15px; position: relative; font-family: Montserrat, Arial, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);"></div><div class="article__block" data-type="text" style="max-height: 1e+06px; box-sizing: inherit; margin-top: 15px; font-family: Montserrat, Arial, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);"><div class="article__text" style="max-height: 1e+06px; box-sizing: inherit; line-height: 26px; font-family: NotoSans, Arial, sans-serif;">Точку зрения Лонджини подтвердил профессор Габриэль Ленг из Университета Гонконга, который также считает, что коронавирус может затронуть две трети человечества.</div></div><div class="article__block" data-type="text" style="max-height: 1e+06px; box-sizing: inherit; margin-top: 15px; font-family: Montserrat, Arial, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);"><div class="article__text" style="max-height: 1e+06px; box-sizing: inherit; line-height: 26px; font-family: NotoSans, Arial, sans-serif;">Нил Фергюсон из Имперского колледжа Лондона подсчитал, что только в Китае каждый день могут заражаться до 50 тысяч человек.</div></div>', '2020-02-14 04:51:08', 'В ВОЗ допустили заражение коронавирусом двух третей населения Земли', 15, 20, 13),
(53, True, 'NEW', '<div class="article__block" data-type="text" style="max-height: 1e+06px; box-sizing: inherit; margin-top: 0px; font-family: Montserrat, Arial, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);"><div class="article__text" style="max-height: 1e+06px; box-sizing: inherit; line-height: 26px; font-family: NotoSans, Arial, sans-serif;"><span style="max-height: 1e+06px; box-sizing: inherit; font-weight: 700;">ВАШИНГТОН, 14 фев – РИА Новости.</span>&nbsp;<a href="https://ria.ru/location_United_States/" target="_blank" style="max-height: 1e+06px; box-sizing: inherit; text-decoration-line: none; cursor: pointer; color: rgb(79, 41, 140); border-radius: 2px; padding: 0px 5px; white-space: pre-wrap; background: rgba(79, 41, 140, 0.12); position: relative;">США</a>&nbsp;должны сохранять потенциал ядерного сдерживания и модернизировать триаду перед лицом новейшего российского вооружения, такого как гиперзвуковые ракеты "Авангард", заявил в четверг замглавы&nbsp;<a href="https://ria.ru/organization_Ministerstvo_oborony_SSHA/" target="_blank" style="max-height: 1e+06px; box-sizing: inherit; text-decoration-line: none; cursor: pointer; color: rgb(79, 41, 140); border-radius: 2px; padding: 0px 5px; white-space: pre-wrap; background: rgba(79, 41, 140, 0.12); position: relative; margin-bottom: 0px;">Пентагона</a>&nbsp;по закупкам и техобеспечению Алан Шаффер.</div></div><div class="article__block" data-type="quote" style="max-height: 1e+06px; box-sizing: inherit; margin-top: 25px; font-family: Montserrat, Arial, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);"><div class="article__quote" style="max-height: 1e+06px; box-sizing: inherit; position: relative; overflow: hidden; padding: 30px 30px 30px 100px; border-radius: 6px; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; box-shadow: rgba(0, 0, 0, 0.25) 0px 2px 9px;"><div class="article__quote-bg" style="max-height: 1e+06px; box-sizing: inherit; position: absolute; top: 0px; left: 0px; width: 138px; height: 269px;"><div class="article__quote-bg-desk" style="max-height: 1e+06px; box-sizing: inherit; width: 138px; height: 269px;"><svg xmlns="http://www.w3.org/2000/svg" class="svg-graphic" width="138" height="270" viewBox="0 0 138 270" fill="none"><path class="svg-accent-color" fill-rule="evenodd" clip-rule="evenodd" d="M0 208.5L49 159.5H0V208.5Z" fill="#0075FF"></path><path class="svg-accent-color" fill-rule="evenodd" clip-rule="evenodd" d="M8.53125 70.4639C6.27539 65.0391 5.11523 59.2236 5.11523 53.3506C5.11523 47.4775 6.27539 41.6621 8.53125 36.2363C10.7871 30.8105 14.0918 25.8799 18.2598 21.7275C22.4277 17.5742 27.377 14.2803 32.8223 12.0322C38.2676 9.78516 44.1055 8.5 50 8.5V0.5H0V125.747C14.6055 135.848 32.0195 141.5 50 141.5V98.5C44.1055 98.5 38.2695 96.9121 32.8242 94.665C27.377 92.418 22.4297 89.124 18.2617 84.9717C14.0938 80.8193 10.7871 75.8896 8.53125 70.4639Z" fill="#0075FF"></path><path class="svg-accent-color" d="M18 54.3055L38.9778 29.501L49.8242 35.1805L33.3758 55.001L49.8242 74.7055L38.9778 80.501L18 55.8123V54.3055ZM45.2949 54.3055L66.2727 29.501L77 35.1805L60.6707 55.001L77 74.7055L66.2727 80.501L45.2949 55.8123V54.3055Z" fill="#0075FF"></path><path opacity="0.06" fill-rule="evenodd" clip-rule="evenodd" d="M0 269.5L49 220.5V159.5L0 208.5V269.5Z" fill="black"></path><path opacity="0.06" fill-rule="evenodd" clip-rule="evenodd" d="M91.3867 70.6455C93.6387 65.2109 94.7969 59.3857 94.7969 53.5029C94.7988 47.6201 93.6387 41.7939 91.3887 36.3584C89.1367 30.9238 85.8379 25.9844 81.6777 21.8242C77.5176 17.6641 72.5801 14.3643 67.1445 12.1133C61.709 9.86133 55.8828 8.5 50 8.5V0.5H120.25C131.703 15.6797 138 34.2656 138 53.5C138 76.8389 128.729 99.2227 112.225 115.726C95.7227 132.229 73.3398 141.5 50 141.5V98.5C55.8828 98.5 61.709 97.1396 67.1426 94.8887C72.5781 92.6377 77.5176 89.3379 81.6758 85.1787C85.8359 81.0186 89.1367 76.0811 91.3867 70.6455Z" fill="black"></path><path opacity="0.06" fill-rule="evenodd" clip-rule="evenodd" d="M0 190.885L50 141.5V98.5L0 147.5V190.885Z" fill="black"></path><path opacity="0.1" fill-rule="evenodd" clip-rule="evenodd" d="M0 147.5L50 98.5H0V147.5Z" fill="black"></path><path opacity="0.1" fill-rule="evenodd" clip-rule="evenodd" d="M88.7031 0.5H0V88.5967L88.7031 0.5Z" fill="black"></path></svg></div></div><div class="article__quote-text m-small" style="max-height: 1e+06px; box-sizing: inherit; position: relative; padding: 0px 0px 30px; line-height: 25px; font-family: NotoSans, Arial, sans-serif;">"Это (новое оружие – ред.) оказывающая давление угроза для нашей обороны. Это оказывающая давление угроза для нашей триады. И это то, почему модернизация триады настолько важна. И это то, почему минобороны ставит приоритетом гиперзвуковое оружие и защиту от гиперзвуковых вооружений. Это конкретный вызов", - сказал он на конференции по ядерному сдерживанию.</div><div class="article__quote-share" style="max-height: 1e+06px; box-sizing: inherit; position: absolute; bottom: 22px; right: 20px;"><span class="share" data-url="https://ria.ru/20200214/1564683459.html?share-block=1564683461" data-title="&quot;Это (новое оружие – ред.) оказывающая давление угроза для нашей обороны. Это оказывающая давление угроза для нашей триады. И это то, почему модернизация триады настолько важна. И это то, почему минобороны ставит приоритетом гиперзвуковое оружие и защиту от гиперзвуковых вооружений. Это конкретный вызов&quot;, - сказал он на конференции по ядерному сдерживанию." style="max-height: 1e+06px; box-sizing: inherit; position: relative; display: block; z-index: 150;"><a data-name="facebook" style="max-height: 1e+06px; box-sizing: inherit; cursor: pointer; color: rgb(51, 51, 51); width: 26px; height: 26px; float: left; display: block;"><svg class="svg-icon"><use xlink:href="#social-facebook_alt"><svg viewBox="0 0 40 40" id="social-facebook_alt" xmlns="http://www.w3.org/2000/svg"><path d="M31 20.002C31 13.925 26.076 9 20 9S9 13.925 9 20.002c0 5.492 4.022 10.043 9.281 10.869v-7.689h-2.793v-3.18h2.793v-2.424c0-2.757 1.642-4.28 4.155-4.28 1.204 0 2.462.214 2.462.214v2.708h-1.387c-1.367 0-1.792.849-1.792 1.719v2.063h3.05l-.487 3.18h-2.563v7.689C26.979 30.045 31 25.494 31 20z"></path></svg></use></svg></a><a data-name="vkontakte" style="max-height: 1e+06px; box-sizing: inherit; cursor: pointer; color: rgb(51, 51, 51); width: 26px; height: 26px; float: left; display: block;"><svg class="svg-icon"><use xlink:href="#social-vkontakte"><svg viewBox="0 0 40 40" id="social-vkontakte" xmlns="http://www.w3.org/2000/svg"><path d="M27.805 27.083h-.147a3.434 3.434 0 0 1-1.612-.7 9.104 9.104 0 0 1-1.257-1.2l-.769-.917a1.038 1.038 0 0 0-1.035-.444.654.654 0 0 0-.532.385 2.17 2.17 0 0 0-.177.754c-.02.286-.035.576-.045.872 0 .259-.066.513-.192.74a1.364 1.364 0 0 1-1.242.473c-.63 0-1.222-.02-1.774-.06a7.574 7.574 0 0 1-3.977-1.448 15.365 15.365 0 0 1-2.616-2.62 36.69 36.69 0 0 1-4.022-6.535c-.177-.375-.364-.775-.562-1.2-.2-.431-.302-.9-.3-1.375a1.56 1.56 0 0 1 1.035-.31c.414.009.838.014 1.271.014.376 0 .824-.01 1.346-.03.399-.04.8.02 1.168.178.24.156.417.392.5.666.14.328.299.649.473.96.296.65.592 1.232.887 1.745.296.512.631 1.035 1.006 1.568.138.216.33.482.576.8a.907.907 0 0 0 .843.414c.258-.06.424-.36.5-.9.076-.542.126-1.03.148-1.464 0-.493-.015-1.02-.045-1.582a3.056 3.056 0 0 0-.25-1.227 1.043 1.043 0 0 0-.592-.488c-.26-.08-.51-.194-.74-.34a1.55 1.55 0 0 1 1.168-.754 11.513 11.513 0 0 1 1.858-.14c.592 0 1.222.005 1.892.015a1.6 1.6 0 0 1 1.39.576 2.51 2.51 0 0 1 .207 1.538 14.104 14.104 0 0 0-.118 1.745c-.02.61-.03 1.187-.03 1.73a1.4 1.4 0 0 0 .591 1.227c.31-.037.593-.197.784-.444l.577-.71c.371-.496.712-1.015 1.02-1.552.286-.504.586-1.09.9-1.76.138-.275.281-.6.43-.975.081-.301.292-.55.575-.68.412-.17.859-.23 1.3-.178.534.04 1.046.06 1.538.059.217 0 .463-.01.74-.03.266-.02.533-.024.8-.014.234.005.467.038.693.1.172.031.32.14.4.295.108.369.054.766-.148 1.094a7.92 7.92 0 0 1-.5.976 15.15 15.15 0 0 1-1.138 1.627c-.385.473-.794.985-1.228 1.537-.176.216-.393.492-.65.828-.24.29-.367.658-.355 1.035.025.284.157.549.37.739.226.216.438.423.635.62.434.395.834.765 1.2 1.11.395.38.747.801 1.05 1.257.202.235.39.481.562.739.189.222.284.509.266.8a.757.757 0 0 1-.636.68 5.407 5.407 0 0 1-1.33.148c-.503 0-1.005-.015-1.509-.045a3.691 3.691 0 0 0-1.17.078z"></path></svg></use></svg></a><a data-name="odnoklassniki" style="max-height: 1e+06px; box-sizing: inherit; cursor: pointer; color: rgb(51, 51, 51); width: 26px; height: 26px; float: left; display: block;"><svg class="svg-icon"><use xlink:href="#social-odnoklassniki"><svg viewBox="0 0 40 40" id="social-odnoklassniki" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" clip-rule="evenodd" d="M19.99 20.134a6.47 6.47 0 0 1-2.384-.443 6.194 6.194 0 0 1-1.956-1.208 5.68 5.68 0 0 1-1.313-1.81 5.202 5.202 0 0 1-.483-2.207 5.28 5.28 0 0 1 .483-2.239 6.075 6.075 0 0 1 1.313-1.844 5.928 5.928 0 0 1 1.956-1.24 6.467 6.467 0 0 1 2.384-.44 6.608 6.608 0 0 1 2.423.443 5.644 5.644 0 0 1 1.941 1.24 6.263 6.263 0 0 1 1.288 1.844c.323.702.488 1.466.483 2.24a5.199 5.199 0 0 1-.483 2.205 5.732 5.732 0 0 1-3.229 3.02 6.609 6.609 0 0 1-2.423.44zm0-8.487a3.1 3.1 0 0 0-2.134.814 2.585 2.585 0 0 0-.91 2c.004.758.336 1.478.91 1.972a3.145 3.145 0 0 0 4.292 0c.57-.497.899-1.216.9-1.972.01-.767-.319-1.5-.9-2a3.115 3.115 0 0 0-2.158-.814zm7.117 10.584a1.15 1.15 0 0 0-.017-1.384l.001-.004a1.518 1.518 0 0 0-.924-.717 1.811 1.811 0 0 0-1.666.475 5.28 5.28 0 0 1-1.44.717c-.48.157-.97.28-1.466.367-.378.067-.76.11-1.143.13l-.467.015-.467-.016a9.139 9.139 0 0 1-1.144-.129c-.493-.086-.98-.207-1.457-.362a5.27 5.27 0 0 1-1.44-.717 1.755 1.755 0 0 0-1.644-.475 1.6 1.6 0 0 0-.95.717c-.296.418-.283.98.032 1.384a7.878 7.878 0 0 0 1.6 1.257c.692.41 1.45.698 2.238.853a21.2 21.2 0 0 0 2.045.322l-.966.918a99.62 99.62 0 0 1-1.949 1.8c-.644.59-1.186 1.095-1.626 1.513a1.34 1.34 0 0 0-.435.967c0 .351.16.683.435.9l.161.161a1.486 1.486 0 0 0 1.965 0l3.613-3.378c.706.633 1.372 1.25 2 1.852a37.629 37.629 0 0 0 1.683 1.53c.27.24.62.371.982.37.358.003.703-.13.966-.37l.177-.162a1.16 1.16 0 0 0 .42-.9 1.365 1.365 0 0 0-.42-.967l-3.623-3.317-1-.918a16.317 16.317 0 0 0 2.093-.362 8.472 8.472 0 0 0 2.19-.813 7.068 7.068 0 0 0 1.643-1.257z"></path></svg></use></svg></a><a class="share__more" style="max-height: 1e+06px; box-sizing: inherit; cursor: pointer; color: rgb(51, 51, 51); width: 26px; height: 26px; float: left; display: block;"><span class="share__more-desktop" style="max-height: 1e+06px; box-sizing: inherit; width: 26px; height: 26px; display: block;"><svg class="svg-icon"><use xlink:href="#icon-more"><svg viewBox="0 0 40 40" id="icon-more" xmlns="http://www.w3.org/2000/svg"><circle cx="20" cy="19.667" r="1.999"></circle><circle cx="27.998" cy="19.667" r="1.999"></circle><circle cx="12.002" cy="19.667" r="1.999"></circle></svg></use></svg></span></a></span></div></div></div><div class="article__block" data-type="text" style="max-height: 1e+06px; box-sizing: inherit; margin-top: 25px; font-family: Montserrat, Arial, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);"><div class="article__text" style="max-height: 1e+06px; box-sizing: inherit; line-height: 26px; font-family: NotoSans, Arial, sans-serif;">По утверждению Шаффера, Китай и Россия ведут подобные разработки, несмотря на то, что располагают арсеналом межконтинентальных баллистических ракет, достаточным для решения задач сдерживания.</div></div><div class="article__block" data-type="banner" data-position="desktop" style="max-height: 1e+06px; box-sizing: inherit; margin-top: 15px; position: relative; font-family: Montserrat, Arial, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);"><div class="banner m-article-body m-article-desktop" data-position="article_desktop_content-1" data-changed-id="adfox_desktop_body_1564683459" style="max-height: 1e+06px; box-sizing: inherit; position: relative;"><div class="banner__content" style="max-height: 1e+06px; box-sizing: inherit; position: relative;"></div></div><div class="banner__hidden" style="max-height: 40px; box-sizing: inherit; position: absolute; top: 0px; right: 0px; overflow: hidden; width: 40px; height: 0px; pointer-events: none;"><a class="banner__hidden-button" style="max-height: 1e+06px; box-sizing: inherit; cursor: pointer; color: rgb(51, 51, 51); display: block; position: absolute; top: 14px; right: 14px; width: 25px; height: 25px; border: 1px solid rgb(172, 172, 172); border-radius: 2px; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; z-index: 100; pointer-events: all;"></a></div></div><div class="article__block" data-type="text" style="max-height: 1e+06px; box-sizing: inherit; margin-top: 15px; font-family: Montserrat, Arial, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);"><div class="article__text" style="max-height: 1e+06px; box-sizing: inherit; line-height: 26px; font-family: NotoSans, Arial, sans-serif;">"Китай и Россия размещают новые системы вооружения в пространстве ядерного сдерживания, в стратегическом пространстве. Они бросят нам вызов. Мы должны быть готовы обороняться от этих вооружений, и мы должны быть готовы предложить системы ответного удара", - добавил замглавы Пентагона.</div></div>', '2020-02-14 04:55:00', 'Пентагон признал новое российское оружие угрозой своей ядерной триаде', 2, NULL, 13),
(55, False, 'NEW', '<div class="article__header" style="max-height: 1e+06px; box-sizing: inherit; margin: 0px 0px 10px; font-family: Montserrat, Arial, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);"><div class="article__announce" style="max-height: 1e+06px; box-sizing: inherit; position: relative;"><div class="media" data-media-tipe="ar16x9" style="max-height: 1e+06px; box-sizing: inherit; position: relative; padding-top: 20px;"><div class="media__size" style="max-height: 1e+06px; box-sizing: inherit; position: relative; margin: 0px;"><div class="photoview__open" data-photoview-group="1564647896" data-photoview-src="https://cdn23.img.ria.ru/images/07e4/02/0d/1564648884_0:0:3072:2048_1440x900_80_1_1_b3cb27d0ecc91ee8c8a25eed1afd9c53.jpg" data-photoview-image-id="1564648946" data-photoview-sharelink="https://ria.ru/services/article/infinity/1564647896.html?share-img=1564648880" style="max-height: 1e+06px; box-sizing: inherit; cursor: zoom-in;"><img media-type="ar16x9" alt="Призывники в сборном пункте военного комиссариата в городе Сызрань " title="Призывники в сборном пункте военного комиссариата в городе Сызрань " src="https://cdn22.img.ria.ru/images/07e4/02/0d/1564648884_0:15:3072:1743_600x0_80_0_0_16f7c5bbb3999b276cbaab837f9059b1.jpg" style="max-height: 1e+06px; box-sizing: inherit; vertical-align: middle; display: block; position: relative; max-width: 100%; margin: 0px auto;"></div></div><div class="media__copyright " style="max-height: 1e+06px; box-sizing: inherit; font-family: NotoSans, Arial, sans-serif; color: rgb(173, 173, 173); padding-top: 10px; line-height: 1.1; display: flex; flex-wrap: wrap; overflow: hidden;"><div class="media__copyright-item m-copyright" style="max-height: 1e+06px; box-sizing: inherit; display: inline-block; position: relative; margin-right: 9px; margin-left: 0px; padding-left: 0px; font-size: 10px; line-height: 13px; vertical-align: top;"><a href="https://ria.ru/docs/about/copyright.html" style="max-height: 1e+06px; box-sizing: inherit; text-decoration-line: none; cursor: pointer; color: rgb(173, 173, 173);">© РИА Новости / Николай Хижняк</a></div><div class="media__copyright-item m-buy" style="max-height: 1e+06px; box-sizing: inherit; display: inline-block; position: relative; margin-right: 0px; margin-left: -9px; padding-left: 9px; font-size: 10px; line-height: 13px; vertical-align: top;"><a href="http://visualrian.ru/images/item/2972001" target="_blank" style="max-height: 1e+06px; box-sizing: inherit; text-decoration-line: none; cursor: pointer; color: rgb(173, 173, 173);">Перейти в фотобанк</a></div></div></div></div></div><div class="article__body js-mediator-article mia-analytics" style="max-height: 1e+06px; box-sizing: inherit; position: relative; padding: 20px 0px; font-family: Montserrat, Arial, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);"><div class="article__block" data-type="text" style="max-height: 1e+06px; box-sizing: inherit; margin-top: 0px;"><div class="article__text" style="max-height: 1e+06px; box-sizing: inherit; line-height: 26px; font-family: NotoSans, Arial, sans-serif;"><span style="max-height: 1e+06px; box-sizing: inherit; font-weight: 700;">МОСКВА, 13 фев — РИА Новости</span>.&nbsp;<a href="https://ria.ru/organization_Gosudarstvennaja_Duma_RF" target="_blank" style="max-height: 1e+06px; box-sizing: inherit; text-decoration-line: none; cursor: pointer; color: rgb(79, 41, 140); border-radius: 2px; padding: 0px 5px; white-space: pre-wrap; background: rgba(79, 41, 140, 0.12); position: relative; margin-bottom: 0px;">Государственная дума</a>&nbsp;приняла законопроект о выплате денежного довольствия призывникам в размере двух тысяч рублей.</div></div><div class="article__block" data-type="text" style="max-height: 1e+06px; box-sizing: inherit; margin-top: 15px;"><div class="article__text" style="max-height: 1e+06px; box-sizing: inherit; line-height: 26px; font-family: NotoSans, Arial, sans-serif;">Эта сумма включает оклад, ежемесячные надбавки за выполнение задач, связанных с риском для жизни и здоровья, за руководство воинским подразделением и работу с документами, представляющими гостайну. Закон также предусматривает получение единовременного пособия, равного одному окладу, при увольнении со службы.</div></div></div>', '2020-02-14 05:02:00', 'Госдума приняла закон о выплате призывникам довольствия в 2000 рублей', 0, NULL, 13);

-- 
-- Вывод данных для таблицы tag2post
--
INSERT INTO tag2post VALUES
(26, 24),
(18, 29),
(26, 51),
(53, 52),
(55, 54);

-- 
-- Вывод данных для таблицы post_votes
--
INSERT INTO post_votes VALUES
(23, '2020-02-13 02:32:31', -1, 18, 13),
(56, '2020-02-14 05:03:46', 1, 26, 13);

-- 
-- Вывод данных для таблицы post_comments
--
INSERT INTO post_comments VALUES
(33, 'комментарий', '2020-02-13 06:01:11', NULL, 18, 13),
(34, '<strong>alkarik@hiik.ru</strong>, отвечаю', '2020-02-13 06:14:52', NULL, 26, 20),
(35, '<strong>alkarik@hiik.ru</strong>, второй раз отвечаю', '2020-02-13 06:21:02', 33, 26, 20),
(36, '<strong>alkarik@ya.ru</strong>, отвечаю на отвечаю', '2020-02-13 06:22:16', 34, 26, 13),
(57, '<strong>alkarik@ya.ru</strong>, ответ под комментарием', '2020-02-14 05:04:04', 34, 26, 13),
(58, 'Добавить комментарий', '2020-02-14 05:08:42', NULL, 26, 13);

-- 
-- Вывод данных для таблицы hibernate_sequence
--
INSERT INTO hibernate_sequence VALUES
(61),
(61),
(61),
(61),
(61),
(61),
(61);

-- 
-- Вывод данных для таблицы global_settings
--
INSERT INTO global_settings VALUES
(1, 'MULTIUSER_MODE', 'Многопользовательский режим', 'NO'),
(2, 'POST_PREMODERATION', 'Премодерация постов', 'NO'),
(3, 'STATISTICS_IS_PUBLIC', 'Показывать всем статистику блога', 'YES');

-- 
-- Включение внешних ключей
-- 
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;