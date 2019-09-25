--
-- Estrutura da tabela `produto`
--

DROP TABLE IF EXISTS `produto`;

CREATE TABLE IF NOT EXISTS `produto` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo_barras` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `nome` varchar(24) DEFAULT NULL,
  `preco_compra` decimal(12,2) NOT NULL,
  `preco_venda` decimal(12,2) NOT NULL,
  `quantidade` decimal(13,3) NOT NULL,
  `unidade` varchar(255) NOT NULL,
  `categoria_codigo` bigint(20) NOT NULL,
  `fornecedor_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FKtfuf17yvliycysg3vt5h0sp2v` (`categoria_codigo`),
  KEY `FKpvyafr9m7vpu95rd3uq7fja5g` (`fornecedor_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;