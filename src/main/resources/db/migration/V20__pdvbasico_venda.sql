--
-- Estrutura da tabela `venda`
--

DROP TABLE IF EXISTS `venda`;

CREATE TABLE IF NOT EXISTS `venda` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_criacao` date DEFAULT NULL,
  `forma_pagamento` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `valor_desconto` decimal(19,2) DEFAULT NULL,
  `valor_total` decimal(19,2) DEFAULT NULL,
  `cliente_codigo` bigint(20) DEFAULT NULL,
  `usuario_codigo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK7fxgfskuip18oucp6s7p0iygu` (`cliente_codigo`),
  KEY `FKbpmnv7mqmkeyg3nx4ey7l3lrv` (`usuario_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `FK217jpcwxw6r3770uvvn1imott` FOREIGN KEY (`cidade_codigo`) REFERENCES `municipio` (`codigo`);

--
-- Limitadores para a tabela `conta_pagar`
--
ALTER TABLE `conta_pagar`
  ADD CONSTRAINT `FK9melmx9kpvxpxj4jg31r3vn04` FOREIGN KEY (`fornecedor_codigo`) REFERENCES `fornecedor` (`codigo`);

--
-- Limitadores para a tabela `conta_pagar_lancamento`
--
ALTER TABLE `conta_pagar_lancamento`
  ADD CONSTRAINT `FKj3vit8ddc1kxruy91nc0eoveh` FOREIGN KEY (`conta_pagar_codigo`) REFERENCES `conta_pagar` (`codigo`);

--
-- Limitadores para a tabela `conta_receber`
--
ALTER TABLE `conta_receber`
  ADD CONSTRAINT `FK277kbtr0sborb3u80vwy0plc5` FOREIGN KEY (`cliente_codigo`) REFERENCES `cliente` (`codigo`);

--
-- Limitadores para a tabela `conta_receber_lancamento`
--
ALTER TABLE `conta_receber_lancamento`
  ADD CONSTRAINT `FK7ar5julv5x8j94cbystxx0c37` FOREIGN KEY (`conta_receber_codigo`) REFERENCES `conta_receber` (`codigo`);

--
-- Limitadores para a tabela `empresa`
--
ALTER TABLE `empresa`
  ADD CONSTRAINT `FKc73qbmj46r1g9jvhnq0i2ypav` FOREIGN KEY (`cidade_codigo`) REFERENCES `municipio` (`codigo`);

--
-- Limitadores para a tabela `grupo_permissoes`
--
ALTER TABLE `grupo_permissoes`
  ADD CONSTRAINT `FK7lo3mm9yfyjw4hb8bfdct0y44` FOREIGN KEY (`codigo_grupo`) REFERENCES `grupo` (`codigo`),
  ADD CONSTRAINT `FK7vlthmg3pq6l7pbl860gfg1c1` FOREIGN KEY (`codigo_permissao`) REFERENCES `permissao` (`codigo`);

--
-- Limitadores para a tabela `item_venda`
--
ALTER TABLE `item_venda`
  ADD CONSTRAINT `FK2hga7ydmrd7vnndgv0k924l5r` FOREIGN KEY (`venda_codigo`) REFERENCES `venda` (`codigo`),
  ADD CONSTRAINT `FK5hfw16v3yic7depx2e0qosqx1` FOREIGN KEY (`produto_codigo`) REFERENCES `produto` (`codigo`);

--
-- Limitadores para a tabela `municipio`
--
ALTER TABLE `municipio`
  ADD CONSTRAINT `FK9fw9jwteh8nwic84s5hj7trg7` FOREIGN KEY (`codigo_estado`) REFERENCES `estado` (`codigo`);

--
-- Limitadores para a tabela `produto`
--
ALTER TABLE `produto`
  ADD CONSTRAINT `FKpvyafr9m7vpu95rd3uq7fja5g` FOREIGN KEY (`fornecedor_codigo`) REFERENCES `fornecedor` (`codigo`),
  ADD CONSTRAINT `FKtfuf17yvliycysg3vt5h0sp2v` FOREIGN KEY (`categoria_codigo`) REFERENCES `categoria` (`codigo`);

--
-- Limitadores para a tabela `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `FK55cw96aj2dhpy1pn9as3ktda3` FOREIGN KEY (`codigo_grupo`) REFERENCES `grupo` (`codigo`);

--
-- Limitadores para a tabela `venda`
--
ALTER TABLE `venda`
  ADD CONSTRAINT `FK7fxgfskuip18oucp6s7p0iygu` FOREIGN KEY (`cliente_codigo`) REFERENCES `cliente` (`codigo`),
  ADD CONSTRAINT `FKbpmnv7mqmkeyg3nx4ey7l3lrv` FOREIGN KEY (`usuario_codigo`) REFERENCES `usuario` (`codigo`);
COMMIT;

-- /*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
-- /*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
-- /*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;