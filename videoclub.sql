-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-11-2016 a las 22:01:37
-- Versión del servidor: 10.1.9-MariaDB
-- Versión de PHP: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `videoclub`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `cod_cte` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Ap_Paterno` varchar(50) NOT NULL,
  `Ap_Materno` varchar(50) DEFAULT NULL,
  `membresia` int(11) NOT NULL,
  `usuario` varchar(10) NOT NULL,
  `contraseña` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`cod_cte`, `Nombre`, `Ap_Paterno`, `Ap_Materno`, `membresia`, `usuario`, `contraseña`) VALUES
(1, 'julio', 'castro', 'beltran', 1, 'julio94', '123456'),
(2, 'julio', 'castro', 'beltran', 1, 'julio10', '199425');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `membresias`
--

CREATE TABLE `membresias` (
  `id` int(11) NOT NULL,
  `Nombre` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `membresias`
--

INSERT INTO `membresias` (`id`, `Nombre`) VALUES
(1, 'Normal'),
(2, 'Gold');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rentas`
--

CREATE TABLE `rentas` (
  `id` int(11) NOT NULL,
  `fecha` datetime NOT NULL,
  `cod_cte` int(11) NOT NULL,
  `Total` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rentas_videos`
--

CREATE TABLE `rentas_videos` (
  `id` int(11) NOT NULL,
  `video` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `total` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipos_video`
--

CREATE TABLE `tipos_video` (
  `id` int(11) NOT NULL,
  `Nombre` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tipos_video`
--

INSERT INTO `tipos_video` (`id`, `Nombre`) VALUES
(1, 'Infantil'),
(2, 'Adolescentes'),
(3, 'Adultos');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `videos`
--

CREATE TABLE `videos` (
  `id` int(11) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Precio` decimal(10,0) NOT NULL,
  `Tipo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `videos`
--

INSERT INTO `videos` (`id`, `Nombre`, `Precio`, `Tipo`) VALUES
(1, 'Blanca nieves y los 7 enanos ', '35', 1),
(2, 'La cenicienta', '35', 1),
(3, 'El libro de la selva', '35', 1),
(4, 'La Bella durmiente', '35', 1),
(5, 'Fantasia', '35', 1),
(6, 'Bambi ', '35', 1),
(7, 'La dama y el vagabundo', '35', 1),
(8, 'Enredados', '35', 1),
(9, 'Valiente', '35', 1),
(10, 'Frozen', '35', 1),
(11, 'La bella y la bestia', '35', 1),
(12, 'Bolt', '35', 1),
(13, 'Ralph el demoledor', '35', 1),
(14, 'La princesa y el sapo', '35', 1),
(15, 'El rey león ', '35', 1),
(16, 'Crepusculo', '35', 2),
(17, 'Nueva luna', '35', 2),
(18, 'Eclipse', '35', 2),
(19, 'Amanecer parte 1', '35', 2),
(20, 'Amanecer parte 2', '35', 2),
(21, 'Divergente', '35', 2),
(22, 'Insurgente', '35', 2),
(23, 'Leal', '35', 2),
(24, 'Los guardianes de la galaxia', '35', 2),
(25, 'Los vengadores', '35', 2),
(26, 'X-Men Los dias del futuro pasado', '35', 2),
(27, 'Thor', '35', 2),
(28, 'Los juegos del hambre', '35', 2),
(29, 'Los juegos del hambre "En llamas"', '35', 2),
(30, 'Los juegos del hambre "Sinsajo parte 1"', '35', 2),
(31, 'Anabelle', '35', 3),
(32, 'Saw', '35', 3),
(33, 'A Nightmare on elm street', '35', 3),
(34, 'Scream', '35', 3),
(35, 'The final destination', '35', 3),
(36, 'The cabin in the woods', '35', 3),
(37, 'El conjuro', '35', 3),
(38, 'The grudge', '35', 3),
(39, 'The uninvited ', '35', 3),
(40, 'Sinister ', '35', 3),
(41, 'El exorcista', '35', 3),
(42, 'El aro', '35', 3),
(43, 'The purge', '35', 3),
(44, 'El niño', '35', 3),
(45, 'Puente de espias', '35', 3);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`cod_cte`);

--
-- Indices de la tabla `membresias`
--
ALTER TABLE `membresias`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `rentas`
--
ALTER TABLE `rentas`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `rentas_videos`
--
ALTER TABLE `rentas_videos`
  ADD PRIMARY KEY (`id`,`video`);

--
-- Indices de la tabla `tipos_video`
--
ALTER TABLE `tipos_video`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `videos`
--
ALTER TABLE `videos`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `cod_cte` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `membresias`
--
ALTER TABLE `membresias`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `rentas`
--
ALTER TABLE `rentas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `tipos_video`
--
ALTER TABLE `tipos_video`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `videos`
--
ALTER TABLE `videos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
