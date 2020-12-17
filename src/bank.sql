-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 2020-12-17 04:07:55
-- 服务器版本： 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bank`
--
CREATE DATABASE IF NOT EXISTS `bank` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `bank`;

-- --------------------------------------------------------

--
-- 表的结构 `checking_account`
--

CREATE TABLE `checking_account` (
  `ID` int(11) NOT NULL,
  `CUSTOMER_ID` int(11) NOT NULL,
  `CURRENCY_TYPE` varchar(16) NOT NULL,
  `MONEY_AMOUNT` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `checking_account`
--

INSERT INTO `checking_account` (`ID`, `CUSTOMER_ID`, `CURRENCY_TYPE`, `MONEY_AMOUNT`) VALUES
(1, 1, 'Dollar', '0.00'),
(2, 1, 'RMB', '0.00'),
(3, 1, 'Pound', '0.00'),
(6, 4, 'Pound', '0.00'),
(5, 4, 'RMB', '0.00'),
(4, 4, 'Dollar', '0.00'),
(7, 11, 'Dollar', '0.00'),
(8, 11, 'RMB', '0.00'),
(9, 11, 'Pound', '0.00'),
(10, 12, 'Dollar', '0.00'),
(11, 12, 'RMB', '0.00'),
(12, 12, 'Pound', '0.00'),
(13, 14, 'Dollar', '0.00'),
(14, 14, 'RMB', '0.00'),
(15, 14, 'Pound', '0.00');

-- --------------------------------------------------------

--
-- 表的结构 `collateral`
--

CREATE TABLE `collateral` (
  `ID` int(11) NOT NULL,
  `NAME` text,
  `ACCOUNT_ID` int(11) NOT NULL,
  `VALUE` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `collateral`
--

INSERT INTO `collateral` (`ID`, `NAME`, `ACCOUNT_ID`, `VALUE`) VALUES
(3, 'car', 2, '100.00'),
(4, 'house', 7, '1800.00');

-- --------------------------------------------------------

--
-- 表的结构 `customer`
--

CREATE TABLE `customer` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(16) NOT NULL,
  `PASSWORD` varchar(16) NOT NULL,
  `SAVING_ID` int(11) DEFAULT NULL,
  `CHECKING_ID` int(11) DEFAULT NULL,
  `LOAN_ID` int(11) DEFAULT NULL,
  `STOCK_ID` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `customer`
--

INSERT INTO `customer` (`ID`, `NAME`, `PASSWORD`, `SAVING_ID`, `CHECKING_ID`, `LOAN_ID`, `STOCK_ID`) VALUES
(1, 'Christine', '123456', 1, 1, 1, 1),
(2, 'QingyangBeauty', '111111', 31, -1, 2, 2),
(3, 'AnnanBeauty', 'aaaaaa', 10, -1, -1, -1),
(4, 'Chuqian', '233333', 7, 4, 3, -1),
(5, 'CiciLovely', '111111', 4, -1, 4, -1),
(6, 'KokoSmart', '66666666', -1, -1, -1, -1),
(7, 'GoodFinal', '88888888', 13, -1, -1, -1),
(8, 'ExcellentFinal', '77777777', 16, -1, -1, -1),
(9, 'AlmostDone', '11111111', -1, -1, -1, -1),
(10, 'AllSuccess', '111111', -1, -1, -1, -1),
(11, 'NoBugPlease', '111111', 22, 7, 5, -1),
(12, 'LoveAndPeace', '111111', 25, 10, 6, -1),
(13, 'Cl', '1111111', 28, -1, -1, -1),
(14, 'JennyLi', '000000', 34, 13, 7, -1);

-- --------------------------------------------------------

--
-- 表的结构 `loan`
--

CREATE TABLE `loan` (
  `ID` int(11) NOT NULL,
  `ACCOUNT_ID` int(11) NOT NULL,
  `COLLATERAL_ID` int(11) NOT NULL,
  `LOAN_AMOUNT` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `loan`
--

INSERT INTO `loan` (`ID`, `ACCOUNT_ID`, `COLLATERAL_ID`, `LOAN_AMOUNT`) VALUES
(3, 2, 3, '80.32'),
(4, 7, 4, '1800.00');

-- --------------------------------------------------------

--
-- 表的结构 `loan_account`
--

CREATE TABLE `loan_account` (
  `ID` int(11) NOT NULL,
  `CUSTOMER_ID` int(11) NOT NULL,
  `MONEY_AMOUNT` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `loan_account`
--

INSERT INTO `loan_account` (`ID`, `CUSTOMER_ID`, `MONEY_AMOUNT`) VALUES
(1, 1, '0.00'),
(2, 2, '80.32'),
(3, 4, '0.00'),
(4, 5, '0.00'),
(5, 11, '0.00'),
(6, 12, '0.00'),
(7, 14, '1800.00');

-- --------------------------------------------------------

--
-- 表的结构 `manager`
--

CREATE TABLE `manager` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(16) NOT NULL,
  `PASSWORD` varchar(16) NOT NULL,
  `LAST_UPDATE_DATE` date NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `manager`
--

INSERT INTO `manager` (`ID`, `NAME`, `PASSWORD`, `LAST_UPDATE_DATE`) VALUES
(1, 'admin', '000000', '2020-12-17');

-- --------------------------------------------------------

--
-- 表的结构 `manager_account`
--

CREATE TABLE `manager_account` (
  `ID` int(11) NOT NULL,
  `CURRENCY_TYPE` varchar(16) NOT NULL,
  `MONEY_AMOUNT` decimal(10,0) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `manager_account`
--

INSERT INTO `manager_account` (`ID`, `CURRENCY_TYPE`, `MONEY_AMOUNT`) VALUES
(1, 'Dollar', '99998228'),
(2, 'RMB', '100000000'),
(3, 'Pound', '100000000');

-- --------------------------------------------------------

--
-- 表的结构 `saving_account`
--

CREATE TABLE `saving_account` (
  `ID` int(11) NOT NULL,
  `CUSTOMER_ID` int(11) NOT NULL,
  `CURRENCY_TYPE` varchar(16) NOT NULL,
  `MONEY_AMOUNT` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `saving_account`
--

INSERT INTO `saving_account` (`ID`, `CUSTOMER_ID`, `CURRENCY_TYPE`, `MONEY_AMOUNT`) VALUES
(1, 1, 'Dollar', '3700.00'),
(2, 1, 'RMB', '0.00'),
(3, 1, 'Pound', '0.00'),
(5, 5, 'RMB', '0.00'),
(4, 5, 'Dollar', '41.00'),
(6, 5, 'Pound', '0.00'),
(7, 4, 'Dollar', '300030.00'),
(8, 4, 'RMB', '99.00'),
(9, 4, 'Pound', '0.00'),
(10, 3, 'Dollar', '0.00'),
(11, 3, 'RMB', '0.00'),
(12, 3, 'Pound', '0.00'),
(13, 7, 'Dollar', '0.00'),
(14, 7, 'RMB', '0.00'),
(15, 7, 'Pound', '0.00'),
(16, 8, 'Dollar', '5.00'),
(17, 8, 'RMB', '0.00'),
(18, 8, 'Pound', '0.00'),
(36, 14, 'Pound', '0.00'),
(35, 14, 'RMB', '3000.00'),
(34, 14, 'Dollar', '7761.00'),
(22, 11, 'Dollar', '12.00'),
(23, 11, 'RMB', '49.00'),
(24, 11, 'Pound', '49.00'),
(25, 12, 'Dollar', '100.00'),
(26, 12, 'RMB', '6.00'),
(27, 12, 'Pound', '7.00'),
(28, 13, 'Dollar', '95.00'),
(29, 13, 'RMB', '0.00'),
(30, 13, 'Pound', '0.00'),
(31, 2, 'Dollar', '5195.52'),
(32, 2, 'RMB', '0.00'),
(33, 2, 'Pound', '0.00');

-- --------------------------------------------------------

--
-- 表的结构 `stock_account`
--

CREATE TABLE `stock_account` (
  `ID` int(11) NOT NULL,
  `CUSTOMER_ID` int(11) NOT NULL,
  `MONEY_AMOUNT` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `stock_account`
--

INSERT INTO `stock_account` (`ID`, `CUSTOMER_ID`, `MONEY_AMOUNT`) VALUES
(1, 1, '1060.00'),
(2, 2, '0.00');

-- --------------------------------------------------------

--
-- 表的结构 `stock_list`
--

CREATE TABLE `stock_list` (
  `ID` int(11) NOT NULL,
  `NAME` text NOT NULL,
  `PRICE` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `stock_list`
--

INSERT INTO `stock_list` (`ID`, `NAME`, `PRICE`) VALUES
(1, 'NASDAQ', '10.00'),
(2, 'S&P 500', '18.00'),
(3, 'Shanghai', '12.00'),
(4, 'Japan', '15.00'),
(5, 'America', '10.00');

-- --------------------------------------------------------

--
-- 表的结构 `stock_ownership`
--

CREATE TABLE `stock_ownership` (
  `ID` int(11) NOT NULL,
  `ACCOUNT_ID` int(11) NOT NULL,
  `STOCK_ID` int(11) NOT NULL,
  `PURCHASE_PRICE` decimal(10,2) NOT NULL,
  `HOLDINGS` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `stock_ownership`
--

INSERT INTO `stock_ownership` (`ID`, `ACCOUNT_ID`, `STOCK_ID`, `PURCHASE_PRICE`, `HOLDINGS`) VALUES
(5, 1, 0, '10.00', 10),
(6, 1, 0, '10.00', 5),
(4, 1, 1, '10.00', 10);

-- --------------------------------------------------------

--
-- 表的结构 `transaction`
--

CREATE TABLE `transaction` (
  `ID` int(11) NOT NULL,
  `CUSTOMER_ID` int(11) NOT NULL,
  `ACCOUNT_ID` int(11) NOT NULL,
  `ACCOUNT_TYPE` varchar(16) NOT NULL,
  `CURRENCY_TYPE` varchar(16) NOT NULL,
  `MONEY_CHANGED` decimal(10,2) NOT NULL,
  `CURRENT_BALANCE` decimal(10,2) NOT NULL,
  `TIME` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `transaction`
--

INSERT INTO `transaction` (`ID`, `CUSTOMER_ID`, `ACCOUNT_ID`, `ACCOUNT_TYPE`, `CURRENCY_TYPE`, `MONEY_CHANGED`, `CURRENT_BALANCE`, `TIME`) VALUES
(1, 1, 1, 'STOCK', 'Dollar', '-100.00', '900.00', '2020-12-14 22:07:39'),
(2, 1, 1, 'STOCK', 'Dollar', '-100.00', '800.00', '2020-12-14 22:08:06'),
(3, 1, 1, 'SAVING', 'Dollar', '-200.00', '3800.00', '2020-12-15 00:19:43'),
(4, 2, 2, 'LOAN', 'Dollar', '80.00', '240.00', '2020-12-15 00:22:44'),
(5, 1, 1, 'STOCK', 'Dollar', '-100.00', '700.00', '2020-12-15 00:32:48'),
(6, 1, 1, 'CHECKING', 'Dollar', '100.00', '100.00', '2020-12-15 00:35:53'),
(7, 1, 1, 'SAVING', 'Dollar', '-200.00', '3600.00', '2020-12-15 00:51:20'),
(8, 1, 1, 'STOCK', 'Dollar', '200.00', '900.00', '2020-12-15 00:53:08'),
(9, 1, 1, 'STOCK', 'Dollar', '-100.00', '800.00', '2020-12-15 02:34:41'),
(10, 1, 1, 'STOCK', 'Dollar', '100.00', '900.00', '2020-12-15 04:22:11'),
(11, 4, 4, 'CHECKING', 'Dollar', '-2.00', '93.00', '2020-12-16 01:06:01'),
(12, 4, 4, 'CHECKING', 'Dollar', '-1.00', '92.00', '2020-12-16 01:06:42'),
(13, 4, 3, 'LOAN', 'Dollar', '2110810.50', '2110894.50', '2020-12-16 01:13:53'),
(14, 4, 3, 'LOAN', 'Dollar', '0.00', '2110894.50', '2020-12-16 01:16:39'),
(15, 4, 3, 'LOAN', 'Dollar', '-2345345.00', '65549.50', '2020-12-16 01:17:53'),
(16, 4, 4, 'CHECKING', 'Dollar', '-1.00', '300083.00', '2020-12-16 01:26:21'),
(17, 4, 4, 'CHECKING', 'Dollar', '-1.00', '300082.00', '2020-12-16 01:27:02'),
(18, 4, 4, 'CHECKING', 'Dollar', '-2.00', '300080.00', '2020-12-16 01:27:17'),
(19, 5, 4, 'SAVING', 'Dollar', '100.00', '100.00', '2020-12-16 01:48:27'),
(20, 5, 4, 'SAVING', 'Dollar', '-51.00', '49.00', '2020-12-16 01:49:20'),
(21, 5, 4, 'LOAN', 'Dollar', '0.00', '41.00', '2020-12-16 01:56:53'),
(22, 5, 4, 'LOAN', 'Dollar', '0.00', '41.00', '2020-12-16 01:57:30'),
(23, 5, 4, 'LOAN', 'Dollar', '450.00', '491.00', '2020-12-16 01:57:52'),
(24, 5, 4, 'LOAN', 'Dollar', '0.00', '491.00', '2020-12-16 01:58:14'),
(25, 5, 4, 'LOAN', 'Dollar', '-450.00', '41.00', '2020-12-16 02:01:36'),
(26, 4, 4, 'CHECKING', 'Dollar', '-1.00', '300079.00', '2020-12-16 11:53:47'),
(27, 4, 7, 'SAVING', 'Dollar', '100.00', '300179.00', '2020-12-16 11:57:59'),
(28, 4, 3, 'LOAN', 'Dollar', '225.00', '300404.00', '2020-12-16 12:01:51'),
(29, 6, 19, 'SAVING', 'Dollar', '-5.00', '95.00', '2020-12-16 12:09:28'),
(30, 6, 19, 'SAVING', 'Dollar', '100.00', '95.00', '2020-12-16 12:15:35'),
(31, 4, 4, 'CHECKING', 'Dollar', '-1.00', '300403.00', '2020-12-16 12:32:18'),
(32, 4, 4, 'CHECKING', 'Dollar', '-2.00', '300401.00', '2020-12-16 12:32:26'),
(33, 4, 4, 'CHECKING', 'Dollar', '-1.00', '300549.23', '2020-12-16 20:40:41'),
(34, 4, 4, 'CHECKING', 'Dollar', '50.00', '300499.23', '2020-12-16 20:40:41'),
(35, 4, 4, 'CHECKING', 'Dollar', '-1.00', '300497.23', '2020-12-16 20:42:34'),
(36, 4, 4, 'CHECKING', 'Dollar', '10.00', '300487.23', '2020-12-16 20:42:34'),
(37, 4, 4, 'CHECKING', 'Dollar', '-1.00', '300485.23', '2020-12-16 20:44:19'),
(38, 4, 4, 'CHECKING', 'Dollar', '10.00', '300475.23', '2020-12-16 20:44:19'),
(39, 4, 7, 'SAVING', 'Dollar', '50.00', '300475.23', '2020-12-16 20:45:14'),
(40, 4, 7, 'SAVING', 'Dollar', '-51.00', '300475.23', '2020-12-16 20:46:10'),
(41, 4, 7, 'SAVING', 'Dollar', '0.00', '300475.23', '2020-12-16 20:46:31'),
(42, 4, 7, 'SAVING', 'Dollar', '50.00', '300475.23', '2020-12-16 20:46:39'),
(43, 4, 7, 'SAVING', 'Dollar', '-51.00', '300475.23', '2020-12-16 20:46:48'),
(44, 4, 7, 'SAVING', 'Dollar', '0.00', '300475.23', '2020-12-16 20:47:11'),
(45, 4, 7, 'SAVING', 'Dollar', '50.00', '300475.23', '2020-12-16 20:47:20'),
(46, 4, 7, 'SAVING', 'Dollar', '-51.00', '300475.23', '2020-12-16 20:47:32'),
(47, 4, 3, 'LOAN', 'Dollar', '225.00', '300700.23', '2020-12-16 20:49:08'),
(48, 4, 3, 'LOAN', 'Dollar', '225.00', '300925.23', '2020-12-16 20:49:51'),
(49, 4, 3, 'LOAN', 'Dollar', '225.00', '301150.23', '2020-12-16 20:51:14'),
(50, 4, 3, 'LOAN', 'Dollar', '225.00', '301375.23', '2020-12-16 20:51:31'),
(51, 4, 3, 'LOAN', 'Dollar', '-225.00', '301150.23', '2020-12-16 20:51:49'),
(52, 4, 3, 'LOAN', 'Dollar', '-225.00', '300925.23', '2020-12-16 20:53:48'),
(53, 4, 3, 'LOAN', 'Dollar', '225.00', '301150.23', '2020-12-16 20:54:57'),
(54, 4, 3, 'LOAN', 'Dollar', '225.00', '301375.23', '2020-12-16 20:55:05'),
(55, 4, 3, 'LOAN', 'Dollar', '225.00', '301600.23', '2020-12-16 20:55:20'),
(56, 4, 3, 'LOAN', 'Dollar', '225.00', '301825.23', '2020-12-16 20:55:31'),
(57, 4, 3, 'LOAN', 'Dollar', '225.00', '302050.23', '2020-12-16 20:55:45'),
(58, 4, 3, 'LOAN', 'Dollar', '225.00', '302275.23', '2020-12-16 20:56:00'),
(59, 4, 3, 'LOAN', 'Dollar', '225.00', '302500.23', '2020-12-16 20:56:06'),
(60, 4, 3, 'LOAN', 'Dollar', '-225.00', '302275.23', '2020-12-16 20:56:13'),
(61, 4, 3, 'LOAN', 'Dollar', '-225.00', '302050.23', '2020-12-16 20:56:16'),
(62, 4, 3, 'LOAN', 'Dollar', '-225.00', '301825.23', '2020-12-16 20:56:17'),
(63, 4, 3, 'LOAN', 'Dollar', '-225.00', '301600.23', '2020-12-16 20:56:18'),
(64, 4, 3, 'LOAN', 'Dollar', '-225.00', '301375.23', '2020-12-16 20:56:20'),
(65, 4, 3, 'LOAN', 'Dollar', '-225.00', '301150.23', '2020-12-16 20:56:21'),
(66, 4, 3, 'LOAN', 'Dollar', '-225.00', '300925.23', '2020-12-16 20:56:23'),
(67, 4, 3, 'LOAN', 'Dollar', '-225.00', '300700.23', '2020-12-16 20:56:24'),
(68, 4, 3, 'LOAN', 'Dollar', '-225.00', '300475.23', '2020-12-16 20:56:27'),
(69, 11, 22, 'SAVING', 'Dollar', '-5.00', '0.00', '2020-12-16 20:59:26'),
(70, 11, 22, 'SAVING', 'Dollar', '-5.00', '0.00', '2020-12-16 20:59:31'),
(71, 11, 22, 'SAVING', 'Dollar', '100.00', '0.00', '2020-12-16 21:02:11'),
(72, 11, 22, 'SAVING', 'Dollar', '-51.00', '0.00', '2020-12-16 21:02:57'),
(73, 11, 22, 'SAVING', 'Dollar', '100.00', '0.00', '2020-12-16 21:03:25'),
(74, 11, 22, 'SAVING', 'Dollar', '-51.00', '0.00', '2020-12-16 21:03:32'),
(75, 11, 22, 'SAVING', 'Dollar', '100.00', '100.00', '2020-12-16 21:04:11'),
(76, 11, 22, 'SAVING', 'Dollar', '-51.00', '49.00', '2020-12-16 21:04:18'),
(77, 11, 5, 'LOAN', 'Dollar', '-8.00', '41.00', '2020-12-16 21:04:52'),
(78, 11, 7, 'CHECKING', 'Dollar', '-5.00', '36.00', '2020-12-16 21:06:14'),
(79, 11, 7, 'CHECKING', 'Dollar', '-1.00', '35.00', '2020-12-16 21:36:17'),
(80, 11, 7, 'CHECKING', 'Dollar', '5.00', '30.00', '2020-12-16 21:36:17'),
(81, 11, 7, 'CHECKING', 'Dollar', '-1.00', '29.00', '2020-12-16 21:37:11'),
(82, 11, 7, 'CHECKING', 'RMB', '8.00', '41.00', '2020-12-16 21:37:11'),
(83, 11, 7, 'CHECKING', 'Dollar', '-1.00', '28.00', '2020-12-16 21:37:58'),
(84, 11, 7, 'CHECKING', 'Pound', '8.00', '41.00', '2020-12-16 21:37:58'),
(85, 12, 25, 'SAVING', 'Dollar', '-5.00', '5.00', '2020-12-16 21:51:52'),
(86, 12, 10, 'CHECKING', 'Dollar', '-5.00', '0.00', '2020-12-16 22:01:43'),
(87, 12, 25, 'SAVING', 'Dollar', '10.00', '0.00', '2020-12-16 22:02:18'),
(88, 12, 25, 'SAVING', 'Dollar', '10.00', '0.00', '2020-12-16 22:02:22'),
(89, 12, 25, 'SAVING', 'Dollar', '10.00', '10.00', '2020-12-16 22:03:57'),
(90, 12, 10, 'CHECKING', 'Dollar', '-1.00', '9.00', '2020-12-16 22:05:16'),
(91, 12, 10, 'CHECKING', 'RMB', '1.00', '9.00', '2020-12-16 22:05:16'),
(92, 12, 10, 'CHECKING', 'Dollar', '-1.00', '8.00', '2020-12-16 22:05:30'),
(93, 12, 10, 'CHECKING', 'RMB', '3.00', '6.00', '2020-12-16 22:05:30'),
(94, 12, 10, 'CHECKING', 'Dollar', '-1.00', '7.00', '2020-12-16 22:06:20'),
(95, 12, 10, 'CHECKING', 'Pound', '3.00', '7.00', '2020-12-16 22:06:20'),
(96, 12, 25, 'SAVING', 'Dollar', '1.00', '8.00', '2020-12-16 22:07:00'),
(97, 12, 6, 'LOAN', 'Dollar', '-8.00', '0.00', '2020-12-16 22:07:06'),
(98, 12, 6, 'LOAN', 'Dollar', '900.00', '900.00', '2020-12-16 22:08:52'),
(99, 12, 6, 'LOAN', 'Dollar', '900.00', '1800.00', '2020-12-16 22:09:25'),
(100, 12, 25, 'SAVING', 'Dollar', '100.00', '1900.00', '2020-12-16 22:09:49'),
(101, 12, 6, 'LOAN', 'Dollar', '-900.00', '1000.00', '2020-12-16 22:10:36'),
(102, 12, 6, 'LOAN', 'Dollar', '-900.00', '100.00', '2020-12-16 22:11:08'),
(103, 12, 6, 'LOAN', 'Dollar', '0.00', '100.00', '2020-12-16 22:14:54'),
(104, 12, 25, 'SAVING', 'Dollar', '900.00', '1000.00', '2020-12-16 22:15:21'),
(105, 12, 6, 'LOAN', 'Dollar', '-900.00', '100.00', '2020-12-16 22:15:34'),
(106, 4, 7, 'SAVING', 'Dollar', '200.00', '300678.23', '2020-12-16 23:14:31'),
(107, 4, 3, 'LOAN', 'Dollar', '225.00', '300903.23', '2020-12-16 23:18:39'),
(108, 4, 3, 'LOAN', 'Dollar', '-225.00', '300678.23', '2020-12-16 23:18:51'),
(109, 13, 28, 'SAVING', 'Dollar', '-5.00', '95.00', '2020-12-16 23:38:37'),
(110, 2, 31, 'SAVING', 'Dollar', '-5.00', '95.00', '2020-12-17 08:09:17'),
(111, 2, 31, 'SAVING', 'Dollar', '100.00', '195.00', '2020-12-17 08:20:45'),
(112, 2, 31, 'SAVING', 'Dollar', '5000.00', '5175.00', '2020-12-17 08:22:53'),
(113, 1, 1, 'STOCK', 'Dollar', '-100.00', '3600.00', '2020-12-17 08:41:39'),
(114, 1, 1, 'STOCK', 'Dollar', '0.00', '3600.00', '2020-12-17 08:42:49'),
(115, 1, 1, 'SAVING', 'Dollar', '100.00', '3690.00', '2020-12-17 08:59:06'),
(116, 1, 1, 'STOCK', 'Dollar', '0.00', '3680.00', '2020-12-17 09:00:59'),
(117, 6, 19, 'SAVING', 'Dollar', '0.00', '95.00', '2020-12-17 09:12:21'),
(118, 6, 19, 'SAVING', 'Dollar', '-91.80', '3.20', '2020-12-17 09:12:48'),
(119, 6, 19, 'SAVING', 'Dollar', '0.00', '3.20', '2020-12-17 09:12:56'),
(120, 6, 19, 'SAVING', 'Dollar', '0.00', '3.20', '2020-12-17 09:13:03'),
(121, 6, 19, 'SAVING', 'Dollar', '-3.06', '0.14', '2020-12-17 09:13:09'),
(122, 6, 19, 'SAVING', 'Dollar', '0.00', '0.14', '2020-12-17 09:13:14'),
(123, 6, 19, 'SAVING', 'Dollar', '0.00', '0.14', '2020-12-17 09:14:08'),
(124, 6, 19, 'SAVING', 'Dollar', '5.00', '5.14', '2020-12-17 09:14:23'),
(125, 6, 19, 'SAVING', 'Dollar', '0.00', '5.14', '2020-12-17 09:14:28'),
(126, 6, 19, 'SAVING', 'Dollar', '0.00', '5.14', '2020-12-17 09:14:31'),
(127, 6, 19, 'SAVING', 'Dollar', '0.00', '5.14', '2020-12-17 09:14:57'),
(128, 6, 19, 'SAVING', 'Dollar', '0.00', '5.14', '2020-12-17 09:16:36'),
(129, 6, 19, 'SAVING', 'Dollar', '0.00', '5.14', '2020-12-17 09:16:48'),
(130, 6, 19, 'SAVING', 'Dollar', '-96.90', '5.14', '2020-12-17 09:16:54'),
(131, 6, 19, 'SAVING', 'Dollar', '-3.06', '5.14', '2020-12-17 09:17:15'),
(132, 6, 19, 'SAVING', 'Dollar', '-5.00', '0.14', '2020-12-17 09:17:26'),
(133, 1, 1, 'STOCK', 'Dollar', '-120.00', '3700.00', '2020-12-17 09:32:05'),
(134, 1, 1, 'STOCK', 'Dollar', '-180.00', '3700.00', '2020-12-17 09:32:25'),
(135, 1, 1, 'STOCK', 'Dollar', '-80.00', '3700.00', '2020-12-17 09:35:24'),
(136, 1, 1, 'STOCK', 'Dollar', '60.00', '3700.00', '2020-12-17 09:35:34'),
(137, 1, 1, 'STOCK', 'Dollar', '-50.00', '3700.00', '2020-12-17 09:36:14'),
(138, 1, 1, 'STOCK', 'Dollar', '-60.00', '3700.00', '2020-12-17 09:36:28'),
(139, 1, 1, 'STOCK', 'Dollar', '30.00', '3700.00', '2020-12-17 09:36:37'),
(140, 1, 1, 'STOCK', 'Dollar', '-60.00', '3700.00', '2020-12-17 10:04:15'),
(141, 1, 1, 'STOCK', 'Dollar', '60.00', '3700.00', '2020-12-17 10:04:42'),
(142, 1, 1, 'STOCK', 'Dollar', '-60.00', '3700.00', '2020-12-17 10:04:53'),
(143, 1, 1, 'STOCK', 'Dollar', '60.00', '3700.00', '2020-12-17 10:06:17'),
(144, 1, 1, 'STOCK', 'Dollar', '-90.00', '3700.00', '2020-12-17 10:06:31'),
(145, 1, 1, 'STOCK', 'Dollar', '90.00', '3700.00', '2020-12-17 10:07:10'),
(146, 1, 1, 'STOCK', 'Dollar', '-50.00', '3700.00', '2020-12-17 10:09:50'),
(147, 1, 1, 'STOCK', 'Dollar', '50.00', '3700.00', '2020-12-17 10:10:04'),
(148, 1, 1, 'STOCK', 'Dollar', '-20.00', '3700.00', '2020-12-17 10:14:06'),
(149, 1, 1, 'STOCK', 'Dollar', '20.00', '3700.00', '2020-12-17 10:14:25'),
(150, 14, 34, 'SAVING', 'Dollar', '-5.00', '3995.00', '2020-12-17 10:38:10'),
(151, 14, 13, 'CHECKING', 'Dollar', '-5.00', '3990.00', '2020-12-17 10:38:26'),
(152, 14, 13, 'CHECKING', 'Dollar', '-1.00', '3989.00', '2020-12-17 10:39:05'),
(153, 14, 13, 'CHECKING', 'Dollar', '1000.00', '2989.00', '2020-12-17 10:39:05'),
(154, 14, 34, 'SAVING', 'Dollar', '3000.00', '5989.00', '2020-12-17 10:40:35'),
(155, 14, 34, 'SAVING', 'Dollar', '3000.00', '5989.00', '2020-12-17 10:41:28'),
(156, 14, 7, 'LOAN', 'Dollar', '-8.00', '5961.00', '2020-12-17 10:42:44'),
(157, 14, 7, 'LOAN', 'Dollar', '1800.00', '7761.00', '2020-12-17 10:43:07'),
(158, 1, 1, 'STOCK', 'Dollar', '-180.00', '3700.00', '2020-12-17 10:47:40'),
(159, 1, 1, 'STOCK', 'Dollar', '180.00', '3700.00', '2020-12-17 10:47:49');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `checking_account`
--
ALTER TABLE `checking_account`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `collateral`
--
ALTER TABLE `collateral`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `loan`
--
ALTER TABLE `loan`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `loan_account`
--
ALTER TABLE `loan_account`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `manager`
--
ALTER TABLE `manager`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `manager_account`
--
ALTER TABLE `manager_account`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `saving_account`
--
ALTER TABLE `saving_account`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `stock_account`
--
ALTER TABLE `stock_account`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `stock_list`
--
ALTER TABLE `stock_list`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `stock_ownership`
--
ALTER TABLE `stock_ownership`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
