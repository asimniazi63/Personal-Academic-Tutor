-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 28, 2016 at 11:55 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `pat`
--

-- --------------------------------------------------------

--
-- Table structure for table `authentication`
--

CREATE TABLE IF NOT EXISTS `authentication` (
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `authentication`
--

INSERT INTO `authentication` (`username`, `password`) VALUES
('admin', '123abc');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE IF NOT EXISTS `students` (
  `uob` int(8) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `year` int(4) DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`uob`, `name`, `email`, `year`, `department`, `address`) VALUES
(14031254, 'Asim', 'asim2014@namal', 3, 'Cs', 'Miawnali'),
(14031253, 'Bilal Ahmad Khan', 'bilal2014@namal.edu.pk', 3, 'CS', 'Mianwali'),
(14031256, 'Abdur Rehman Khan', 'rehman2014@namal.edu.pk', 3, 'CS', 'Mianwali'),
(14031257, 'M Jaleel', 'jaleel2013@namal.edu.pk', 3, 'CS', 'Mianwali'),
(14031258, 'Hasnain', 'hasnain2014@namal.edu.pk', 3, 'CS', 'Mianwali'),
(14031259, 'Usman', 'usman2014@namal.edu.pk', 3, 'CS', 'Mianwali'),
(14031260, 'Ameer Abdullah', 'ameer2014@namale.edu.pk', 3, 'CS', 'Mianwali'),
(14031247, 'Najaf Khan', 'najaf2014@namal.edu.k', 3, 'CS', 'Mianwali'),
(14031251, 'Sana Ullah', 'sana2014@namal.edu.pk', 3, 'Computer Science', 'Mianwali');

-- --------------------------------------------------------

--
-- Table structure for table `teachers`
--

CREATE TABLE IF NOT EXISTS `teachers` (
  `id` int(8) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `contact` varchar(11) DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `pat_load` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `teachers`
--

INSERT INTO `teachers` (`id`, `name`, `email`, `contact`, `department`, `pat_load`) VALUES
(1, 'Sarmad Ali', 'sarmad.ali@namal.edu.pk', '3011234567', 'Computer Sciences', 'h'),
(2, 'Adnan', 'adnan@namal.edu.pk', '03011234567', 'Computer Sciences', 'f'),
(3, 'Noman Javed', 'noman@namal.edu.pk', '03011234567', 'Computer Sciences', 'f'),
(4, 'Adil Raja', 'adil.raja@naml.edu.pk', '03011234567', 'Computer Science', 'f');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
