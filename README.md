# Stump backend.
## Recieves requests from frontend and responds accordingly.

The goals of Stump include providing a safe and on-topic space for people to discuss current events and politics.

Visit the site here: http://stumpa-Publi-Wjhi2fIC1D37-422631532.us-west-1.elb.amazonaws.com

Stump is connected to an aws rds

Password is removed.

Stumpdb table schemas: (use to replicate database)

CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `bio` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `post` (
  `post_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `creator_id` int DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`post_id`),
  KEY `post_ibfk_1` (`creator_id`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`creator_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `sitenews` (
  `post_id` int NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `creator_id` int NOT NULL,
  `post_id` int NOT NULL,
  `create_time` timestamp NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `upvote` int NOT NULL,
  `downvote` int NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `creator_id` (`creator_id`),
  KEY `comment_ibfk_3` (`post_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`creator_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `comment_ibfk_3` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

