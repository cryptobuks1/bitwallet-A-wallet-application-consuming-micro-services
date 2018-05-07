/*
SQLyog Ultimate v11.11 (32 bit)
MySQL - 5.5.12 : Database - wallet
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wallet` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `wallet`;

/*Table structure for table `taccount` */

DROP TABLE IF EXISTS `taccount`;

CREATE TABLE `taccount` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `balance_amount` decimal(19,2) NOT NULL,
  `owner` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `taccount` */

insert  into `taccount`(`id`,`balance_amount`,`owner`) values (1,2341234.00,'dave'),(2,10000500.50,'afshar');

/*Table structure for table `ttransaction` */

DROP TABLE IF EXISTS `ttransaction`;

CREATE TABLE `ttransaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) NOT NULL,
  `guid` varchar(255) NOT NULL,
  `amount` decimal(19,2) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `ttransaction` */

insert  into `ttransaction`(`id`,`account_id`,`guid`,`amount`,`created_at`,`type`) values (1,1,'1233',234234.00,'2018-05-07 02:43:33','CREDIT'),(2,1,'1234',200.00,'2018-05-08 10:57:41','DEBIT');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
