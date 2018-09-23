USE test;

DROP TABLE IF EXISTS part;
CREATE TABLE part (
    id INT(11) NOT NULL AUTO_INCREMENT,
    part_name VARCHAR(40) NOT NULL,
    part_need BOOLEAN NOT NULL DEFAULT FALSE,
    part_count INT (11) NOT NULL DEFAULT 0,
    CONSTRAINT part_id_pk PRIMARY KEY(id),
    CONSTRAINT part_name_uq UNIQUE(part_name),
    CONSTRAINT part_name_chk CHECK(part_name != ''),
    CONSTRAINT part_need_chk CHECK(part_need = TRUE OR part_need = FALSE),
    CONSTRAINT part_count_chk CHECK(part_count >= 0)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

INSERT INTO part(part_name, part_need, part_count) VALUES
    ('Материнская плата', TRUE, 24),
    ('Центральный процессор', TRUE, 27),
    ('Видеокарта', FALSE, 18),
    ('Звуковая карта', FALSE, 5),
    ('Монитор', TRUE, 19),
    ('Блок питания', TRUE, 12),
    ('Корпус', TRUE, 22),
    ('Куллер', TRUE, 36),
    ('Клавиатура', TRUE, 35),
    ('Мышь', FALSE, 44),
    ('Оперативная память', TRUE, 104),
    ('SSD накопитель', TRUE, 30),
    ('HDD накопитель', FALSE, 54),
    ('Принтер', FALSE, 7),
    ('Сканнер', FALSE, 1),
    ('МФУ', FALSE, 21),
    ('USB накопитель', FALSE, 56),
    ('ИБП', FALSE, 20),
    ('LAN адаптер', FALSE, 6),
    ('WIFI адаптер', FALSE, 2),
    ('Кардридер', FALSE, 16),
    ('Подсветка корпуса', FALSE, 8);
