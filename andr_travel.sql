-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 08, 2020 at 08:41 AM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `andr_travel`
--

-- --------------------------------------------------------

--
-- Table structure for table `airplane`
--

CREATE TABLE `airplane` (
  `ID` int(11) NOT NULL,
  `Source` varchar(3) NOT NULL,
  `Dest` varchar(3) NOT NULL,
  `Dept_time` char(4) NOT NULL,
  `Trv_time` char(3) NOT NULL,
  `Price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `airplane`
--

INSERT INTO `airplane` (`ID`, `Source`, `Dest`, `Dept_time`, `Trv_time`, `Price`) VALUES
(1, 'FZB', 'KKT', '0730', '3', 5000),
(2, 'VSK', 'RPM', '0030', '3', 4500),
(3, 'GDG', 'JBP', '1900', '4¼', 5100),
(4, 'HRN', 'ADB', '0650', '4½', 6500),
(5, 'NDR', 'RJK', '0900', '3¼', 3500),
(6, 'OTY', 'PNJ', '2145', '4¾', 7200),
(7, 'TVC', 'RCH', '0800', '5½', 5600),
(8, 'KKT', 'FZB', '0145', '2¼', 2600),
(9, 'RPM', 'VSK', '1530', '¾', 2100),
(10, 'JBP', 'GDG', '1100', '3¼', 5400),
(11, 'ADB', 'HRN', '1645', '3½', 4600),
(12, 'RJK', 'NDR', '2100', '4¼', 4200),
(13, 'PNJ', 'OTY', '2145', '22½', 7390),
(14, 'RCH', 'TVC', '1815', '2¾', 3400),
(15, 'FZB', 'KKT', '1430', '2¼', 4300),
(16, 'VSK', 'RPM', '1815', '3½', 2300),
(17, 'GDG', 'JBP', '2145', '2¾', 4100),
(18, 'HRN', 'ADB', '0545', '4¼', 4200),
(19, 'NDR', 'RJK', '2300', '2½', 4500),
(20, 'OTY', 'PNJ', '0415', '5¾', 5600);

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `BookID` int(11) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Travel_Date` varchar(10) NOT NULL,
  `Mode` varchar(8) NOT NULL,
  `Travel_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`BookID`, `Email`, `Travel_Date`, `Mode`, `Travel_ID`) VALUES
(1, 'ass@hotmail.com', '20/05/2020', 'airplane', 7),
(2, 'karthik.bhat19@gmail.com', '01/05/2020', 'bus', 8),
(3, 'karthik.bhat19@gmail.com', '30/05/2020', 'train', 2),
(4, 'user@mail.com', '30/05/2020', 'bus', 18);

-- --------------------------------------------------------

--
-- Table structure for table `bus`
--

CREATE TABLE `bus` (
  `ID` int(11) NOT NULL,
  `Source` varchar(3) NOT NULL,
  `Dest` varchar(3) NOT NULL,
  `Dept_time` char(4) NOT NULL,
  `Trv_time` char(3) NOT NULL,
  `Price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bus`
--

INSERT INTO `bus` (`ID`, `Source`, `Dest`, `Dept_time`, `Trv_time`, `Price`) VALUES
(1, 'BGL', 'MGL', '1500', '6', 550),
(2, 'MGL', 'BBY', '2310', '14', 1200),
(3, 'TRV', 'EKM', '1900', '14', 500),
(4, 'CHN', 'BGL', '1000', '4', 900),
(5, 'BBY', 'DLH', '0820', '11', 720),
(6, 'DLH', 'CND', '1400', '8½', 860),
(7, 'DJL', 'LDK', '2200', '5½', 1420),
(8, 'MGL', 'BGL', '1230', '8', 540),
(9, 'BBY', 'MGL', '1420', '18', 830),
(10, 'EKM', 'TRV', '1400', '23', 1300),
(11, 'BGL', 'CHN', '1100', '7', 900),
(12, 'DLH', 'BBY', '0000', '5½', 860),
(13, 'CND', 'DLH', '1800', '7', 1320),
(14, 'LDK', 'DJL', '0630', '4½', 570),
(15, 'BGL', 'MGL', '1200', '8½', 620),
(16, 'MGL', 'BBY', '1330', '6½', 895),
(17, 'TRV', 'EKM', '0100', '17½', 1300),
(18, 'CHN', 'BGL', '0200', '6½', 1250),
(19, 'BBY', 'DLH', '0830', '15', 1150),
(20, 'DLH', 'CND', '0500', '23¾', 1500);

-- --------------------------------------------------------

--
-- Table structure for table `locations`
--

CREATE TABLE `locations` (
  `Locname` varchar(20) NOT NULL,
  `Locid` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `locations`
--

INSERT INTO `locations` (`Locname`, `Locid`) VALUES
('Ahmedabad', 'ADB'),
('Bangalore', 'BGL'),
('Bombay', 'BBY'),
('Chandigarh', 'CND'),
('Chattisgarh', 'CTS'),
('Cochin', 'CHN'),
('Darjeeling', 'DJL'),
('Delhi', 'DLH'),
('Ernakulam', 'EKM'),
('Faizabad', 'FZB'),
('Gadag', 'GDG'),
('Harinagar', 'HRN'),
('Indore', 'IDR'),
('Jabalpur', 'JBP'),
('Kolkata', 'KKT'),
('Ladakh', 'LDK'),
('Lucknow', 'LKN'),
('Mangalore', 'MGL'),
('Manipal', 'MPL'),
('Nadora', 'NDR'),
('Ooty', 'OTY'),
('Panaji', 'PNJ'),
('Raichur', 'RCH'),
('Rajkot', 'RJK'),
('Ranipuram', 'RPM'),
('Srinagar', 'SGR'),
('Thiruvananthapuram', 'TRV'),
('Travancore', 'TVC'),
('Udaipur', 'UDP'),
('Vishakhapatnam', 'VSK');

-- --------------------------------------------------------

--
-- Table structure for table `train`
--

CREATE TABLE `train` (
  `ID` int(11) NOT NULL,
  `Source` varchar(3) NOT NULL,
  `Dest` varchar(3) NOT NULL,
  `Dept_time` char(4) NOT NULL,
  `Trv_time` char(3) NOT NULL,
  `Price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `train`
--

INSERT INTO `train` (`ID`, `Source`, `Dest`, `Dept_time`, `Trv_time`, `Price`) VALUES
(1, 'MGL', 'DLH', '0830', '14', 500),
(2, 'BGL', 'BBY', '2000', '4½', 850),
(3, 'DLH', 'KKT', '1400', '8½', 400),
(4, 'TRV', 'MGL', '2230', '7¼', 570),
(5, 'OTY', 'PNJ', '1300', '16', 1490),
(6, 'UDP', 'DLH', '0500', '23½', 600),
(7, 'DJL', 'LDK', '1030', '8½', 900),
(8, 'DLH', 'MGL', '0430', '19½', 1500),
(9, 'BBY', 'BGL', '1430', '5½', 1200),
(10, 'KKT', 'DLH', '1615', '4¼', 980),
(11, 'MGL', 'TRV', '2345', '22', 710),
(12, 'PNJ', 'OTY', '1700', '14¼', 1590),
(13, 'DLH', 'UDP', '0515', '11¾', 1100),
(14, 'LDK', 'DJL', '0245', '¾', 990),
(15, 'MGL', 'DLH', '0300', '12¼', 1150),
(16, 'BGL', 'BBY', '0630', '14', 1200),
(17, 'DLH', 'KKT', '1630', '15¼', 950),
(18, 'TRV', 'MGL', '0530', '21½', 1350),
(19, 'OTY', 'PNJ', '2145', '11¼', 1550),
(20, 'UDP', 'DLH', '0400', '2½', 980);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `Name` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(40) NOT NULL,
  `Mobile` varchar(10) NOT NULL,
  `DOB` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`Name`, `Email`, `Password`, `Mobile`, `DOB`) VALUES
('Karthik', 'karthik.bhat19@gmail.com', '1111', '7899297550', '01/04/2020'),
('Tryagnostack', 'natsux7912@gmail.com', '1111', '1234567890', '20/03/2000'),
('Sindhura', 'sind.kotian@gmail.com', '123457890', '9611039618', '06/06/1999'),
('User', 'user@mail.com', 'user111', '1234560789', '30/05/2020');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `airplane`
--
ALTER TABLE `airplane`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`BookID`);

--
-- Indexes for table `bus`
--
ALTER TABLE `bus`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `locations`
--
ALTER TABLE `locations`
  ADD PRIMARY KEY (`Locid`),
  ADD UNIQUE KEY `Locname` (`Locname`);

--
-- Indexes for table `train`
--
ALTER TABLE `train`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`Email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `airplane`
--
ALTER TABLE `airplane`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `BookID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `bus`
--
ALTER TABLE `bus`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `train`
--
ALTER TABLE `train`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
