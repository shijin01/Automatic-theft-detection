/*
SQLyog Community v13.0.1 (64 bit)
MySQL - 5.5.20-log : Database - automatic theif detection
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`automatic theif detection` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `automatic theif detection`;

/*Table structure for table `camera` */

DROP TABLE IF EXISTS `camera`;

CREATE TABLE `camera` (
  `camera_id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  PRIMARY KEY (`camera_id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=latin1;

/*Data for the table `camera` */

insert  into `camera`(`camera_id`,`userid`) values 
(1,4),
(2,4),
(3,4),
(4,4),
(5,4),
(6,4),
(7,4),
(8,4),
(9,25),
(10,4),
(11,4),
(97,4);

/*Table structure for table `cameranotification` */

DROP TABLE IF EXISTS `cameranotification`;

CREATE TABLE `cameranotification` (
  `notification_id` int(11) NOT NULL AUTO_INCREMENT,
  `camera_id` int(11) DEFAULT NULL,
  `image` text,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`notification_id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=latin1;

/*Data for the table `cameranotification` */

insert  into `cameranotification`(`notification_id`,`camera_id`,`image`,`date`) values 
(55,1,'20220319_164225.jpg','2022-03-19'),
(56,1,'20220319_164234.jpg','2022-03-19'),
(63,1,'20220421_101419.jpg','2022-04-21'),
(64,1,'20220421_105545.jpg','2022-04-21');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `complaint` varchar(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `reply` varchar(100) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `p_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

/*Data for the table `complaint` */

insert  into `complaint`(`c_id`,`complaint`,`date`,`reply`,`user_id`,`p_id`) values 
(18,'stranger','2022-03-07','oke',4,12),
(19,'A weird looking person','2022-03-07','pending',4,11),
(20,'Robary attempt','2022-03-10','pending',4,12),
(24,'killed a man','2022-04-21','oke',4,12),
(25,'robary','2022-04-21','pending',4,11);

/*Table structure for table `criminal` */

DROP TABLE IF EXISTS `criminal`;

CREATE TABLE `criminal` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) DEFAULT NULL,
  `place` varchar(25) DEFAULT NULL,
  `gender` varchar(6) DEFAULT NULL,
  `photo` text,
  `crime` varchar(20) DEFAULT NULL,
  `station` varchar(20) DEFAULT NULL,
  `details` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `criminal` */

insert  into `criminal`(`c_id`,`name`,`place`,`gender`,`photo`,`crime`,`station`,`details`) values 
(1,'raju','dfasf','male','photo_2022-04-27_17-52-22.jpg','daFD','11','DFadfA'),
(2,'karthi','cgm','male','photo_2022-03-19_16-39-41.jpg','fff','12','jhjgjh');

/*Table structure for table `familymember` */

DROP TABLE IF EXISTS `familymember`;

CREATE TABLE `familymember` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `f_name` varchar(150) DEFAULT NULL,
  `photo` text,
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

/*Data for the table `familymember` */

insert  into `familymember`(`f_id`,`user_id`,`f_name`,`photo`) values 
(7,24,' Fyxtd','storage_emulated_0_WhatsApp_Media_WhatsApp_Images_IMG-20220306-WA0015.jpg'),
(12,4,'shijin','storage_emulated_0_Untitled-2.jpg'),
(16,4,'safa','storage_emulated_0_WhatsApp_Media_WhatsApp_Images_IMG-20220312-WA0010.jpg'),
(17,4,'sahla','storage_emulated_0_WhatsApp_Media_WhatsApp_Images_IMG-20220312-WA0009.jpg'),
(18,4,'Amarnath ','storage_emulated_0_WhatsApp_Media_WhatsApp_Images_IMG-20220312-WA0008.jpg');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `l_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(25) NOT NULL,
  `password` varchar(25) DEFAULT NULL,
  `type` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`l_id`),
  UNIQUE KEY `UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`l_id`,`username`,`password`,`type`) values 
(1,'admin','admin','Admin'),
(4,'user','user','user'),
(11,'sahla','123456','Police'),
(12,'police','police','Police'),
(14,'shgkhdf','S,hgjh344','Police'),
(16,'hena','SA4@t234','Police'),
(17,'swwww','swwww','user'),
(19,'swww','swwww','user'),
(20,'sww','swwww','user'),
(21,'sdddd','sdddd','user'),
(22,'sddd','sdddd','user'),
(23,'sdddf','sdddd','user'),
(24,'Werty','Werty','user'),
(25,'Jumi','Jumi1234','user'),
(26,'456','5','user'),
(27,'Sana','Sana123','user');

/*Table structure for table `lookoutnotice` */

DROP TABLE IF EXISTS `lookoutnotice`;

CREATE TABLE `lookoutnotice` (
  `l_id` int(11) NOT NULL AUTO_INCREMENT,
  `topic` varchar(25) DEFAULT NULL,
  `details` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`l_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `lookoutnotice` */

insert  into `lookoutnotice`(`l_id`,`topic`,`details`) values 
(1,'kknsd','dfszdgzggvzs'),
(3,'vxgnn','nvh,vhj,h'),
(4,'chjkl','sartyuilkj');

/*Table structure for table `notify_police` */

DROP TABLE IF EXISTS `notify_police`;

CREATE TABLE `notify_police` (
  `noti_id` int(11) NOT NULL AUTO_INCREMENT,
  `cam_not_id` int(11) NOT NULL,
  `police_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`noti_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=latin1;

/*Data for the table `notify_police` */

insert  into `notify_police`(`noti_id`,`cam_not_id`,`police_id`) values 
(41,56,11),
(42,55,12),
(55,55,14);

/*Table structure for table `policestation` */

DROP TABLE IF EXISTS `policestation`;

CREATE TABLE `policestation` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `station` varchar(25) DEFAULT NULL,
  `place` varchar(25) DEFAULT NULL,
  `post` varchar(25) DEFAULT NULL,
  `pin` int(11) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `lid` int(11) DEFAULT NULL,
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Data for the table `policestation` */

insert  into `policestation`(`s_id`,`station`,`place`,`post`,`pin`,`phone`,`lid`) values 
(6,'pstation','station','wdwd',784512,'8884519632',12),
(9,'mukkam','mkm','fghj',673582,'1234567897',11),
(11,'kunnamangalam','kgm','ghejk',784514,'7845127845',14),
(12,'calicut','clct','kjgg',673601,'9523685747',16);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) NOT NULL,
  `first_name` varchar(8) DEFAULT NULL,
  `Lastname` varchar(5) DEFAULT NULL,
  `gender` varchar(6) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `place` varchar(20) DEFAULT NULL,
  `post` varchar(20) DEFAULT NULL,
  `pin` bigint(20) DEFAULT NULL,
  `phone` bigint(20) DEFAULT NULL,
  `email` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`id`,`lid`,`first_name`,`Lastname`,`gender`,`dob`,`place`,`post`,`pin`,`phone`,`email`) values 
(1,4,'safa','pk','femal','2001-01-10','katamfkm','dasdvasd',679584,7485963214,'safa@gmail.com'),
(2,23,'hdhdhx',' ddhh','male','2000-06-06','hdbbsbs','578889',0,9876543210,'gshsh@gmail.com '),
(3,24,'Hshs',' Bsbs','female','2001-10-05','Vdbdb','445677',0,9876453322,'Ydydh@gmail.com'),
(4,25,'Jumana',' Asin','female','0000-00-00','Thamassery','673601',0,7788943221,'Jumi@Gmail. Com'),
(5,26,'9','8','female','0000-00-00','4','6',8,447856866668,'Dyfhc@gmail.com'),
(6,27,'Sana','C','female','0000-00-00','Omy','673582',0,9207098185,'Sana@gmail.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
