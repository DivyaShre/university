-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 02, 2018 at 01:03 AM
-- Server version: 5.5.44-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `imagineer`
--

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `role`, `roleName`, `createdTS`, `createdBy`, `updateTS`, `updatedBy`, `status`) VALUES
(1, 'ADMIN', 'Admin', '2018-03-26 11:23:14', 1, NULL, 0, 1);


--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `fname`, `mname`, `lname`, `emailId`, `mobileNo`, `alternateMobile`, `password`, `type`, `roleId`, `status`, `createdBy`, `createdTS`, `updatedBy`, `updatedTS`) VALUES
(1, 'ADMIN', NULL, NULL, 'admin@gmail.com', '8056987896', NULL, 'welcome', 0, 1, 1, 1, '2018-03-19 18:30:00', 0, NULL);

