-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 30, 2021 at 11:31 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

-- 
-- Database: `xyzinternship`
--

-- --------------------------------------------------------

--
-- Table structure for table `projects`
--

CREATE TABLE `projects` (
  `idproject` int(11) NOT NULL,
  `namaproject` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `projects`
--

INSERT INTO `projects` (`idproject`, `namaproject`) VALUES
(1, 'Website Showroom Mobil Sinergi'),
(2, 'Manajemen Warung Nasi Bu Ijuk'),
(3, 'Manajemen Klinik Usada Insani');

-- --------------------------------------------------------

--
-- Table structure for table `userpro`
--

CREATE TABLE `userpro` (
  `idrelasi` int(11) NOT NULL,
  `idproject` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  `jobdesk` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `userpro`
--

INSERT INTO `userpro` (`idrelasi`, `idproject`, `iduser`, `jobdesk`) VALUES
(1, 1, 2, 'Capek'),
(2, 2, 12, 'Capek 1'),
(3, 3, 6, 'Capek 2'),
(4, 1, 10, 'xxx'),
(5, 1, 13, 'saaxaxaxaxa');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `nama` text NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `alamat` text NOT NULL,
  `email` text NOT NULL,
  `notelp` text NOT NULL,
  `divisi` text NOT NULL,
  `status` text NOT NULL,
  `foto` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `nama`, `username`, `password`, `alamat`, `email`, `notelp`, `divisi`, `status`, `foto`) VALUES
(1, 'Sipalingadmin', 'admin', 'admin123', 'tangerang kota', 'admin@xyz.com', '081232949506', 'admin', 'Karyawan Tetap', ''),
(2, 'Filbert Amadea Shan Noel', 'filbertxamadea', 'filbert321', 'Regensi Tangerang Poris 2', 'filbertxamadea@gmail.com', '0823234354654', 'IT Web', 'Magang', ''),
(3, 'DonixWawan', 'doniawan', 'doni123', 'Pantai mutiara', 'doniwan@gmail.com', '082345211234', 'IT Web', 'Magang', ''),
(4, 'Agus Yudhoyono', 'agusyudo', 'agus123', 'Pik 2 elit', 'agusyud@gmail.com', '0873647596723', 'IT Web', 'Magang', ''),
(5, 'Afiyah S. Arief', 'afiyahs', 'afi1234', 'Cimone Indah', 'afiyah@gmail.com', '082345234521', 'IT UX / UI', 'Magang', ''),
(6, 'Prisilia I. C. B. C.', 'prisiliaxd', 'prisil123', 'Cina town Square', 'prisilia@gmail.com', '025763845217', 'IT UX / UI', 'Magang', ''),
(7, 'Nobita Nobi', 'nobitaxxx', 'nobinobi123', 'Takaoka, Toyama', 'nobitanobi@gmail.com', '086738490323', 'Akutansi', 'Magang', ''),
(8, 'Shizuka Minamoto', 'shizukaaaeee', 'shizukaka12', 'Takaoka, Toyama', 'shizukaee@gmail.com', '0865544332211', 'Akutansi', 'Magang', ''),
(9, 'Suneo Honekawa', 'suneooox', 'suneo123', 'Takaoka, Toyama', 'suneoneo@gmail.com', '089876345234', 'Akutansi', 'Magang', ''),
(10, 'Takeshi Gouda', 'giantxxx', 'Giant123', 'Takaoka, Toyama', 'giantdor@gmail.com', '081234567890', 'Manajemen', 'Magang', ''),
(11, 'Dekisugi Hidetoshi', 'dekisugigi', 'deki345', 'Takaoka, Toyama', 'dekisugi@gmail.com', '0876523231451', 'Manajemen', 'Magang', ''),
(12, 'Hashirama Senju', 'hashiramaaa', 'hashirama324', 'Konohagakure, Banten', 'hashiramarama@gmail.com', '085423485923', 'Manajemen', 'Magang', ''),
(13, 'mekel', 'mekel', '12345', 'Jakarta', 'mekel@gmail.com', '085863599547', 'admin', 'Magang', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `projects`
--
ALTER TABLE `projects`
  ADD PRIMARY KEY (`idproject`);

--
-- Indexes for table `userpro`
--
ALTER TABLE `userpro`
  ADD PRIMARY KEY (`idrelasi`),
  ADD KEY `idproject` (`idproject`),
  ADD KEY `iduser` (`iduser`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `userpro`
--
ALTER TABLE `userpro`
  MODIFY `idrelasi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `userpro`
--
ALTER TABLE `userpro`
  ADD CONSTRAINT `userpro_ibfk_1` FOREIGN KEY (`iduser`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `userpro_ibfk_2` FOREIGN KEY (`idproject`) REFERENCES `projects` (`idproject`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
