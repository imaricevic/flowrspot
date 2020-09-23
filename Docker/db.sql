CREATE DATABASE `flowrspot`;

CREATE TABLE `User` (
  `user_id` bigint NOT NULL,
  `user_email` varchar(55) NOT NULL UNIQUE,
  `user_username` varchar(55) NOT NULL UNIQUE,
  `user_password` varchar(55) NOT NULL,
   PRIMARY KEY (user_id)
);

CREATE TABLE `Flower` (
  `flower_id` bigint NOT NULL,
  `flower_name` varchar(55) NOT NULL,
  `flower_image` varchar(95),
  `flower_description` varchar(255),
   PRIMARY KEY (flower_id)
);

CREATE TABLE `Sighting` (
  `sighting_id` bigint NOT NULL,
  `sighting_long` DECIMAL(9, 6) NOT NULL,
  `sighting_lat` DECIMAL(8, 6) NOT NULL,
  `sighting_user_id` bigint(55) NOT NULL,
  `sighting_flower_id` bigint(55) NOT NULL,
  `sighting_image` varchar(95),
  `sighting_quote` varchar(255),
   PRIMARY KEY (sighting_id),
   CONSTRAINT fk_su FOREIGN KEY (sighting_user_id)
        REFERENCES User(user_id),
   CONSTRAINT fk_sf FOREIGN KEY (sighting_flower_id)
     REFERENCES Flower(flower_id)
);

CREATE TABLE `User_sighting_likes` (
  `like_user_id` bigint NOT NULL,
  `like_sighting_id` bigint NOT NULL,
   PRIMARY KEY (like_user_id, like_sighting_id),
   CONSTRAINT fk_lu FOREIGN KEY (like_user_id)
        REFERENCES User(user_id),
   CONSTRAINT fk_ls FOREIGN KEY (like_sighting_id)
     REFERENCES Sighting(sighting_id)
);
