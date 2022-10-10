DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `first_name` varchar(80) NOT NULL,
                          `last_name` varchar(80) NOT NULL,
                          `address` varchar(100) NOT NULL,
                          `gender` varchar(6) NOT NULL,
                          `enabled` BIT(1) NOT NULL DEFAULT b'1',
                           PRIMARY KEY (`id`)
);


