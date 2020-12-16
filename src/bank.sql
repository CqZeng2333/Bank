-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 2020-12-16 04:17:14
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
(4, 4, 'Dollar', '0.00');

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
(4, 'beauty', 3, '225.00'),
(2, 'car', 2, '100.00'),
(3, 'car', 2, '100.00');

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
(1, 'first_customer', '123456', 1, 1, 1, 1),
(2, 'second_customer', '111111', -1, -1, 2, 2),
(3, 'arandomname', 'aaaaaa', 10, -1, -1, -1),
(4, 'cqzengg', '233333', 7, 4, 3, -1),
(5, 'testagain', '111111', 4, -1, 4, -1),
(6, 'atiredtester', '66666666', 19, -1, -1, -1),
(7, 'verytired', '88888888', 13, -1, -1, -1),
(8, 'tiredagain', '77777777', 16, -1, -1, -1),
(9, 'newone', '11111111', -1, -1, -1, -1);

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
(4, 3, 4, '225.00'),
(2, 2, 2, '80.00'),
(3, 2, 3, '80.00');

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
(2, 2, '160.00'),
(3, 4, '2345570.00'),
(4, 5, '450.00');

-- --------------------------------------------------------

--
-- 表的结构 `manager`
--

CREATE TABLE `manager` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(16) NOT NULL,
  `PASSWORD` varchar(16) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `manager`
--

INSERT INTO `manager` (`ID`, `NAME`, `PASSWORD`) VALUES
(1, 'admin', '000000');

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
(1, 'Dollar', '99999935'),
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
(1, 1, 'Dollar', '3600.00'),
(2, 1, 'RMB', '0.00'),
(3, 1, 'Pound', '0.00'),
(5, 5, 'RMB', '0.00'),
(4, 5, 'Dollar', '41.00'),
(6, 5, 'Pound', '0.00'),
(7, 4, 'Dollar', '300404.00'),
(8, 4, 'RMB', '100.00'),
(9, 4, 'Pound', '2.00'),
(10, 3, 'Dollar', '0.00'),
(11, 3, 'RMB', '0.00'),
(12, 3, 'Pound', '0.00'),
(13, 7, 'Dollar', '0.00'),
(14, 7, 'RMB', '0.00'),
(15, 7, 'Pound', '0.00'),
(16, 8, 'Dollar', '5.00'),
(17, 8, 'RMB', '0.00'),
(18, 8, 'Pound', '0.00'),
(19, 6, 'Dollar', '95.00'),
(20, 6, 'RMB', '100.00'),
(21, 6, 'Pound', '0.00');

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
(1, 1, '900.00'),
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
(2, 'S&P 500', '18.00');

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
(2, 1, 1, '10.00', 10),
(3, 1, 1, '10.00', 10),
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
(30, 6, 19, 'SAVING', 'Dollar', '100.00', '95.00', '2020-12-16 12:15:35');

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
