--
-- Estrutura da tabela `grupo`
--

DROP TABLE IF EXISTS `grupo`;

CREATE TABLE IF NOT EXISTS `grupo` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;