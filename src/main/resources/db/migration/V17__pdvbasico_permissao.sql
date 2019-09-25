--
-- Estrutura da tabela `permissao`
--

DROP TABLE IF EXISTS `permissao`;

CREATE TABLE IF NOT EXISTS `permissao` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;