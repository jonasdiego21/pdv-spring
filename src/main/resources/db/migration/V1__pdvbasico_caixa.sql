-- Versão do servidor: 5.7.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


-- /*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
-- /*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
-- /*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
-- /*!40101 SET NAMES utf8_default_collection */;

--
-- Database: `pdvbasico`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `caixa`
--

DROP TABLE IF EXISTS `caixa`;
CREATE TABLE IF NOT EXISTS `caixa` (
  `codigo` bigint(20) NOT NULL,
  `data_hora_abertura` datetime DEFAULT NULL,
  `data_hora_fechamento` datetime DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `valor_abertura` decimal(19,2) DEFAULT NULL,
  `valor_fechamento` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------