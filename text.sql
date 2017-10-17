-- MySQL dump 10.16  Distrib 10.1.19-MariaDB, for Win32 (AMD64)
--
-- Host: localhost    Database: localhost
-- ------------------------------------------------------
-- Server version	10.1.19-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrador`
--

DROP TABLE IF EXISTS `administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrador` (
  `ADMCODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `ADMFECHACREACION` date DEFAULT NULL,
  `ADMNOMBRE` char(100) DEFAULT NULL,
  `ADMFECHANAC` date DEFAULT NULL,
  `ADMGENERO` char(15) DEFAULT NULL,
  `ADMLOGIN` text NOT NULL,
  `ADMCLAVE` text NOT NULL,
  PRIMARY KEY (`ADMCODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrador`
--

LOCK TABLES `administrador` WRITE;
/*!40000 ALTER TABLE `administrador` DISABLE KEYS */;
INSERT INTO `administrador` VALUES (1,'2017-10-05','Administrador','1992-10-02','MASCULINO','admin','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
/*!40000 ALTER TABLE `administrador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ejercicio`
--

DROP TABLE IF EXISTS `ejercicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ejercicio` (
  `EJECODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `ESTCODIGO` int(11) DEFAULT NULL,
  `PROCODIGO` int(11) DEFAULT NULL,
  `EJENOMBRE` char(100) DEFAULT NULL,
  `EJEDESCRIPCION` text,
  `EJEIMAGEN` longblob,
  `EJETEXTO` char(250) DEFAULT NULL,
  `EJEIMAGENRES` longblob,
  `EJETEXTORES` text,
  `EJEFECHACREACION` datetime DEFAULT NULL,
  `EJEFECHARESUELTO` datetime DEFAULT NULL,
  PRIMARY KEY (`EJECODIGO`),
  KEY `FK_ESTUDIANTEEJERCICIO` (`ESTCODIGO`),
  KEY `FK_PROFESOREJERCICIO` (`PROCODIGO`),
  CONSTRAINT `FK_ESTUDIANTEEJERCICIO` FOREIGN KEY (`ESTCODIGO`) REFERENCES `estudiante` (`ESTCODIGO`),
  CONSTRAINT `FK_PROFESOREJERCICIO` FOREIGN KEY (`PROCODIGO`) REFERENCES `profesor` (`PROCODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ejercicio`
--

LOCK TABLES `ejercicio` WRITE;
/*!40000 ALTER TABLE `ejercicio` DISABLE KEYS */;
/*!40000 ALTER TABLE `ejercicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiante`
--

DROP TABLE IF EXISTS `estudiante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estudiante` (
  `ESTCODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `ESTMONEDERO` decimal(10,2) DEFAULT NULL,
  `ESTFECHACREACION` date DEFAULT NULL,
  `ESTLOGIN` char(100) DEFAULT NULL,
  `ESTCLAVE` char(100) DEFAULT NULL,
  `ESTNOMBRE` char(100) DEFAULT NULL,
  `ESTFECHANAC` date DEFAULT NULL,
  `ESTGENERO` char(15) DEFAULT NULL,
  `ESTIDENTIFICA` text NOT NULL,
  `ESTCORREO` text NOT NULL,
  PRIMARY KEY (`ESTCODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiante`
--

LOCK TABLES `estudiante` WRITE;
/*!40000 ALTER TABLE `estudiante` DISABLE KEYS */;
INSERT INTO `estudiante` VALUES (52,9.00,'2017-08-17','jose59','1ec4ed037766aa181d8840ad04b9fc6e195fd37dedc04c98a5767a67d3758ece','Jose Fernando Guijarro','2017-08-17','Femenino','0604369884','guijarrojose59@hotmail.com'),(53,99.00,'2017-06-19','pedro59','1ec4ed037766aa181d8840ad04b9fc6e195fd37dedc04c98a5767a67d3758ece','Pedro Herrera','1992-07-27','Másculino','',''),(54,11.00,'1992-07-27','luis59','b89844ac41454ae315903a35889a60939cfe2399458da333fb1b7d43190d0988','Luis Carpio','1992-07-27','Femenino','0604369874','luiscarpio@hotmail.com'),(55,10.00,'1992-07-27','juan59','jp4OypaJViM9CaEAepRxuqRFSANcHKK8KEESEwsyQENM35vL6xXYCervXqdHiSLM','Juan Guerra','1992-07-27','Femenino','4','4');
/*!40000 ALTER TABLE `estudiante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago`
--

DROP TABLE IF EXISTS `pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pago` (
  `PAGCODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `ADMCODIGO` int(11) DEFAULT NULL,
  `ESTCODIGO` int(11) DEFAULT NULL,
  `PAGFECHA` datetime DEFAULT NULL,
  `PAGTIPOPAGO` char(100) DEFAULT NULL,
  `PAGMONTO` decimal(10,2) DEFAULT NULL,
  `PAGUSUARIO` char(100) DEFAULT NULL,
  `PAGTIPOUSUARIO` char(100) DEFAULT NULL,
  PRIMARY KEY (`PAGCODIGO`),
  KEY `FK_ADMINISTADORPAGO` (`ADMCODIGO`),
  KEY `FK_ESTUDIANTEPAGO` (`ESTCODIGO`),
  CONSTRAINT `FK_ADMINISTADORPAGO` FOREIGN KEY (`ADMCODIGO`) REFERENCES `administrador` (`ADMCODIGO`),
  CONSTRAINT `FK_ESTUDIANTEPAGO` FOREIGN KEY (`ESTCODIGO`) REFERENCES `estudiante` (`ESTCODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago`
--

LOCK TABLES `pago` WRITE;
/*!40000 ALTER TABLE `pago` DISABLE KEYS */;
INSERT INTO `pago` VALUES (3,NULL,52,'2017-10-11 11:30:36','Ejercicio Enviado',1.00,'jose59','Débito'),(4,NULL,52,'2017-10-11 11:40:54','Ejercicio Enviado',1.00,'jose59','Débito'),(5,NULL,52,'2017-10-11 15:00:38','Ejercicio Enviado',1.00,'jose59','Débito'),(6,NULL,52,'2017-10-11 15:01:34','Ejercicio Enviado',1.00,'jose59','Débito'),(7,NULL,52,'2017-10-11 15:57:39','Ejercicio Enviado',1.00,'jose59','Débito'),(8,NULL,52,'2017-10-11 16:01:32','Ejercicio Enviado',1.00,'jose59','Débito'),(9,NULL,52,'2017-10-12 14:45:41','Ejercicio Enviado',1.00,'jose59','Débito'),(10,NULL,52,'2017-10-13 00:20:57','Ejercicio Enviado',1.00,'jose59','Débito'),(11,NULL,52,'2017-10-13 14:50:01','Ejercicio Enviado',1.00,'jose59','Débito'),(12,NULL,52,'2017-10-13 14:50:41','Ejercicio Enviado',1.00,'jose59','Débito'),(13,NULL,52,'2017-10-16 22:27:28','Ejercicio Enviado',1.00,'jose59','Débito');
/*!40000 ALTER TABLE `pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profesor`
--

DROP TABLE IF EXISTS `profesor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profesor` (
  `PROCODIGO` int(11) NOT NULL AUTO_INCREMENT,
  `PROEJERCICIORES` int(11) DEFAULT NULL,
  `PROFECHACREACION` date DEFAULT NULL,
  `PROLOGIN` char(100) DEFAULT NULL,
  `PROCLAVE` char(100) DEFAULT NULL,
  `PRONOMBRE` char(100) DEFAULT NULL,
  `PROFECHANAC` date DEFAULT NULL,
  `PROGENERO` char(15) DEFAULT NULL,
  `PROIDENTIFICA` text NOT NULL,
  `PROCORREO` text NOT NULL,
  PRIMARY KEY (`PROCODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesor`
--

LOCK TABLES `profesor` WRITE;
/*!40000 ALTER TABLE `profesor` DISABLE KEYS */;
INSERT INTO `profesor` VALUES (5,81,'1992-10-05','xavier59','1ec4ed037766aa181d8840ad04b9fc6e195fd37dedc04c98a5767a67d3758ece','Profesor Xavier Moya','1992-10-05','29','0604587221','xaviermoya@hotmail.com');
/*!40000 ALTER TABLE `profesor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-16 23:50:06
