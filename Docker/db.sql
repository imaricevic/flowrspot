CREATE DATABASE `flowrspot`;

CREATE TABLE `user_account` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `user_email` varchar(55) NOT NULL UNIQUE,
  `user_username` varchar(55) NOT NULL UNIQUE,
  `user_password` varchar(62) NOT NULL,
   PRIMARY KEY (user_id)
);

CREATE TABLE `flower` (
  `flower_id` bigint NOT NULL AUTO_INCREMENT,
  `flower_name` varchar(55) NOT NULL,
  `flower_image` varchar(95),
  `flower_description` varchar(255),
   PRIMARY KEY (flower_id)
);

CREATE TABLE `sighting` (
  `sighting_id` bigint NOT NULL AUTO_INCREMENT,
  `sighting_long` DECIMAL(9, 6) NOT NULL,
  `sighting_lat` DECIMAL(8, 6) NOT NULL,
  `sighting_user_id` bigint(55) NOT NULL,
  `sighting_flower_id` bigint(55) NOT NULL,
  `sighting_image` varchar(95),
  `sighting_quote` varchar(255),
   PRIMARY KEY (sighting_id),
   CONSTRAINT fk_su FOREIGN KEY (sighting_user_id)
        REFERENCES user_account(user_id),
   CONSTRAINT fk_sf FOREIGN KEY (sighting_flower_id)
     REFERENCES flower(flower_id)
);

CREATE TABLE `user_sighting_likes` (
  `like_user_id` bigint NOT NULL,
  `like_sighting_id` bigint NOT NULL,
   PRIMARY KEY (like_user_id, like_sighting_id),
   CONSTRAINT fk_lu FOREIGN KEY (like_user_id)
        REFERENCES user_account(user_id),
   CONSTRAINT fk_ls FOREIGN KEY (like_sighting_id)
     REFERENCES sighting(sighting_id)
);
