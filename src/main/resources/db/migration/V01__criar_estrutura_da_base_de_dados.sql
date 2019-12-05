CREATE TABLE IF NOT EXISTS `caixa` (
  `codigo` bigint(20) NOT NULL,
  `data_hora_abertura` datetime DEFAULT NULL,
  `data_hora_fechamento` datetime DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `valor_abertura` decimal(19,2) DEFAULT NULL,
  `valor_fechamento` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `categoria` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `cliente` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(255) DEFAULT NULL,
  `complemento` varchar(255) DEFAULT NULL,
  `cpf` varchar(255) DEFAULT NULL,
  `data_nascimento` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `estado` tinyblob NOT NULL,
  `limite_compra` decimal(19,2) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `rua` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `cidade_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK217jpcwxw6r3770uvvn1imott` (`cidade_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `conta_pagar` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_compra` datetime DEFAULT NULL,
  `data_vencimento` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_compra` decimal(19,2) DEFAULT NULL,
  `total_pago` decimal(19,2) DEFAULT NULL,
  `fornecedor_codigo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK9melmx9kpvxpxj4jg31r3vn04` (`fornecedor_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `conta_pagar_lancamento` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_pagamento` datetime DEFAULT NULL,
  `total_pago` decimal(19,2) DEFAULT NULL,
  `conta_pagar_codigo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FKj3vit8ddc1kxruy91nc0eoveh` (`conta_pagar_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `conta_receber` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_vencimento` datetime DEFAULT NULL,
  `data_venda` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_recebido` decimal(19,2) DEFAULT NULL,
  `total_venda` decimal(19,2) DEFAULT NULL,
  `cliente_codigo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK277kbtr0sborb3u80vwy0plc5` (`cliente_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `conta_receber_lancamento` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_recebimento` datetime DEFAULT NULL,
  `total_recebido` decimal(19,2) DEFAULT NULL,
  `conta_receber_codigo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK7ar5julv5x8j94cbystxx0c37` (`conta_receber_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `empresa` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(255) DEFAULT NULL,
  `cnpj` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `estado` tinyblob NOT NULL,
  `nome` varchar(24) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `rua` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `cidade_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FKc73qbmj46r1g9jvhnq0i2ypav` (`cidade_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `emprestimo` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(255) DEFAULT NULL,
  `complemento` varchar(255) DEFAULT NULL,
  `estado` tinyblob NOT NULL,
  `marca` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `quantidade` int(11) NOT NULL,
  `referencia` varchar(255) DEFAULT NULL,
  `rua` varchar(255) DEFAULT NULL,
  `cidade_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FKqtsgajcmivwi6p489pm66yck6` (`cidade_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `estado` (
  `codigo` bigint(20) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `sigla` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `fornecedor` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(255) DEFAULT NULL,
  `cnpj` varchar(255) DEFAULT NULL,
  `complemento` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `estado` tinyblob,
  `fixo` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `rua` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `cidade_codigo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `funcionario` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `comissao` decimal(19,2) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `grupo` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `grupo_permissoes` (
  `codigo_grupo` bigint(20) NOT NULL,
  `codigo_permissao` bigint(20) NOT NULL,
  KEY `FK7vlthmg3pq6l7pbl860gfg1c1` (`codigo_permissao`),
  KEY `FK7lo3mm9yfyjw4hb8bfdct0y44` (`codigo_grupo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `item_venda` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `quantidade` decimal(13,3) DEFAULT NULL,
  `valor_unitario` decimal(12,2) DEFAULT NULL,
  `produto_codigo` bigint(20) DEFAULT NULL,
  `venda_codigo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK5hfw16v3yic7depx2e0qosqx1` (`produto_codigo`),
  KEY `FK2hga7ydmrd7vnndgv0k924l5r` (`venda_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `municipio` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `codigo_estado` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK9fw9jwteh8nwic84s5hj7trg7` (`codigo_estado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `permissao` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

CREATE TABLE IF NOT EXISTS `usuario` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `ativo` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `funcionario_codigo` bigint(20) DEFAULT NULL,
  `codigo_grupo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK274xuu2vtcvnjkjhjyp7birnl` (`funcionario_codigo`),
  KEY `FK55cw96aj2dhpy1pn9as3ktda3` (`codigo_grupo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `venda` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_criacao` datetime DEFAULT NULL,
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

ALTER TABLE `cliente`
  ADD CONSTRAINT `FK217jpcwxw6r3770uvvn1imott` FOREIGN KEY (`cidade_codigo`) REFERENCES `municipio` (`codigo`);

ALTER TABLE `conta_pagar`
  ADD CONSTRAINT `FK9melmx9kpvxpxj4jg31r3vn04` FOREIGN KEY (`fornecedor_codigo`) REFERENCES `fornecedor` (`codigo`);

ALTER TABLE `conta_pagar_lancamento`
  ADD CONSTRAINT `FKj3vit8ddc1kxruy91nc0eoveh` FOREIGN KEY (`conta_pagar_codigo`) REFERENCES `conta_pagar` (`codigo`);

ALTER TABLE `conta_receber`
  ADD CONSTRAINT `FK277kbtr0sborb3u80vwy0plc5` FOREIGN KEY (`cliente_codigo`) REFERENCES `cliente` (`codigo`);

ALTER TABLE `conta_receber_lancamento`
  ADD CONSTRAINT `FK7ar5julv5x8j94cbystxx0c37` FOREIGN KEY (`conta_receber_codigo`) REFERENCES `conta_receber` (`codigo`);

ALTER TABLE `empresa`
  ADD CONSTRAINT `FKc73qbmj46r1g9jvhnq0i2ypav` FOREIGN KEY (`cidade_codigo`) REFERENCES `municipio` (`codigo`);

ALTER TABLE `emprestimo`
  ADD CONSTRAINT `FKqtsgajcmivwi6p489pm66yck6` FOREIGN KEY (`cidade_codigo`) REFERENCES `municipio` (`codigo`);

ALTER TABLE `grupo_permissoes`
  ADD CONSTRAINT `FK7lo3mm9yfyjw4hb8bfdct0y44` FOREIGN KEY (`codigo_grupo`) REFERENCES `grupo` (`codigo`),
  ADD CONSTRAINT `FK7vlthmg3pq6l7pbl860gfg1c1` FOREIGN KEY (`codigo_permissao`) REFERENCES `permissao` (`codigo`);

ALTER TABLE `item_venda`
  ADD CONSTRAINT `FK2hga7ydmrd7vnndgv0k924l5r` FOREIGN KEY (`venda_codigo`) REFERENCES `venda` (`codigo`),
  ADD CONSTRAINT `FK5hfw16v3yic7depx2e0qosqx1` FOREIGN KEY (`produto_codigo`) REFERENCES `produto` (`codigo`);

ALTER TABLE `municipio`
  ADD CONSTRAINT `FK9fw9jwteh8nwic84s5hj7trg7` FOREIGN KEY (`codigo_estado`) REFERENCES `estado` (`codigo`);

ALTER TABLE `produto`
  ADD CONSTRAINT `FKpvyafr9m7vpu95rd3uq7fja5g` FOREIGN KEY (`fornecedor_codigo`) REFERENCES `fornecedor` (`codigo`),
  ADD CONSTRAINT `FKtfuf17yvliycysg3vt5h0sp2v` FOREIGN KEY (`categoria_codigo`) REFERENCES `categoria` (`codigo`);

ALTER TABLE `usuario`
  ADD CONSTRAINT `FK274xuu2vtcvnjkjhjyp7birnl` FOREIGN KEY (`funcionario_codigo`) REFERENCES `funcionario` (`codigo`),
  ADD CONSTRAINT `FK55cw96aj2dhpy1pn9as3ktda3` FOREIGN KEY (`codigo_grupo`) REFERENCES `grupo` (`codigo`);

ALTER TABLE `venda`
  ADD CONSTRAINT `FK7fxgfskuip18oucp6s7p0iygu` FOREIGN KEY (`cliente_codigo`) REFERENCES `cliente` (`codigo`),
  ADD CONSTRAINT `FKbpmnv7mqmkeyg3nx4ey7l3lrv` FOREIGN KEY (`usuario_codigo`) REFERENCES `usuario` (`codigo`);
COMMIT;
