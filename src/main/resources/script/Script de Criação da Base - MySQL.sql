/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     21/07/2016 13:36:43                          */
/*==============================================================*/


drop table if exists IMOBILIARIA.TB_USUARIO;

drop table if exists IMOBILIARIA.TB_GRUPO;

drop table if exists IMOBILIARIA.TB_USUARIO_GRUPO;

drop table if exists IMOBILIARIA.TB_BAIRRO;

drop table if exists IMOBILIARIA.TB_CONTRATO;

drop table if exists IMOBILIARIA.TB_CONTRATO_MODIFICADOR;

drop table if exists IMOBILIARIA.TB_ENDERECO_PESSOA;

drop table if exists IMOBILIARIA.TB_IMOVEL;

drop table if exists IMOBILIARIA.TB_LOCADOR;

drop table if exists IMOBILIARIA.TB_LOCATARIO;

drop table if exists IMOBILIARIA.TB_MODIFICADOR;

drop table if exists IMOBILIARIA.TB_MUNICIPIO;

/*drop index IDX_PESSOA_JURIDICA on IMOBILIARIA.TB_PESSOA;*/

/*drop index IDX_PESSOA_FISICA on IMOBILIARIA.TB_PESSOA;*/

drop table if exists IMOBILIARIA.TB_PESSOA;

drop table if exists IMOBILIARIA.TB_PESSOA_FISICA;

drop table if exists IMOBILIARIA.TB_PESSOA_JURIDICA;

drop table if exists IMOBILIARIA.TB_PESSOA_TELEFONE;

/*==============================================================*/
/* User: IMOBILIARIA                                            */
/*==============================================================*/
/*create user IMOBILIARIA;*/

/*==============================================================*/
/* Table: TB_USUARIO                                              */
/*==============================================================*/
create table IMOBILIARIA.TB_USUARIO(
   ID_USUARIO bigint(20) not null auto_increment comment 'Chave primaria da tabela com numeracao automatica.',
   NOME varchar(100),
   EMAIL varchar(100),
   SENHA varchar(100),
   primary key (ID_USUARIO)
);

/*==============================================================*/
/* Table: TB_GRUPO                                              */
/*==============================================================*/
create table IMOBILIARIA.TB_GRUPO(
   ID_GRUPO bigint(20) not null auto_increment comment 'Chave primaria da tabela com numeracao automatica.',
   NOME varchar(100),
   DESCRICAO varchar(100),
   primary key (ID_GRUPO)
);

/*==============================================================*/
/* Table: TB_USUARIO_GRUPO                                      */
/*==============================================================*/
create table IMOBILIARIA.TB_USUARIO_GRUPO(
    USUARIO_ID bigint(20),
    GRUPO_ID bigint(20)
);

alter table IMOBILIARIA.TB_USUARIO_GRUPO add constraint fk_usuario_grupo_usuario foreign key (USUARIO_ID) references IMOBILIARIA.TB_USUARIO (ID_USUARIO);
alter table IMOBILIARIA.TB_USUARIO_GRUPO add constraint fk_usuario_grupo_grupo foreign key (GRUPO_ID) references IMOBILIARIA.TB_GRUPO (ID_GRUPO);

/*==============================================================*/
/* Table: TB_BAIRRO                                             */
/*==============================================================*/
create table IMOBILIARIA.TB_BAIRRO
(
   ID_BAIRRO            bigint(20) not null auto_increment comment 'Chave primaria da tabela com numeracao automatica.',
   ID_MUNICIPIO         bigint(20) not null comment 'FK que identifica o municipio e UF, na tabela TB_MUNICIPIO.',
   NO_BAIRRO            varchar(100) not null comment 'Nome do bairro.',
   primary key (ID_BAIRRO)
);

alter table IMOBILIARIA.TB_BAIRRO comment 'Tabela que armazena os bairros.
A constraint unica UK_';

/*==============================================================*/
/* Table: TB_CONTRATO                                           */
/*==============================================================*/
create table IMOBILIARIA.TB_CONTRATO
(
   ID_CONTRATO          bigint(20) not null auto_increment comment 'Chave primaria da tabela com numeracao automatica.',
   ID_LOCATARIO         bigint(20) not null comment 'FK que identifica o locatario na tabela TB_LOCATARIO.',
   ID_LOCADOR           bigint(20) not null comment 'FK que identifica o locador na tabela TB_LOCADOR.',
   ID_PESSOA_FIADOR     bigint(20) not null comment 'FK que identifica o fiador na tabela TB_PESSOA, porque pode ser pessoa Fisica ou Juridica.',
   DT_INICIO            datetime not null comment 'Data de inicio do contrato.',
   NU_DURACAO           int(2) not null comment 'Duracao do contrato em meses.',
   NU_DIA_VENCIMENTO    int(2) not null comment 'Numero do dia de vencimento',
   TX_MULTA_POR_ATRASO  decimal(5,2) not null comment 'Valor percentual de multa por atraso.',
   NU_PARCELA_ANTERIOR  int(2) not null comment 'Numero da ultima parcela paga.',
   TX_COMISSAO          decimal(5,2) not null comment 'Valor percentual da comissao do escritorio.',
   ST_CONTRATO_ATIVO    char(1) not null comment 'Indica se o contrato esta ativo. S=Sim; N=Nao',
   primary key (ID_CONTRATO)
);

alter table IMOBILIARIA.TB_CONTRATO comment 'Tabela que contem os contratos de locacao de imovel.';

/*==============================================================*/
/* Table: TB_CONTRATO_MODIFICADOR                               */
/*==============================================================*/
create table IMOBILIARIA.TB_CONTRATO_MODIFICADOR
(
   ID_MODIFICADOR       bigint(20) not null comment 'FK que identifica o modificador na tabela TB_MODIFICADOR. Parte da chave primaria da tabela.',
   ID_CONTRATO          bigint(20) not null comment 'FK que identifica o contrato na tabela TB_CONTRATO. Parte da chave primaria da tabela.',
   NU_MES_ANO_INICIAL   varchar(6) not null comment 'Numero do mes e ano referentes ao periodo inicial do reajuste impactante. Nao possui mascara.',
   NU_MES_ANO_FINAL     varchar(6) not null comment 'Numero do mes e ano referentes ao periodo final do reajuste impactante. Nao possui mascara.',
   TX_REAJUSTE          decimal(5,2) comment 'Valor percentual referente ao reajuste, quando for uma Receita.
            Esta coluna sera obrigatoria quando o valor da coluna TP_MODIFICADOR for igual a "R". Esta restricao e controlada pela CK_TBDESPESACONTRATO_TPMODIFIC.',
   VL_VALOR             decimal(8,2) not null comment 'Valor do item modificador da mensalidade do contrato.',
   TP_MODIFICADOR       char(1) not null comment 'Tipo do modificador. Dominio: R=Receita; D=Despesa.',
   primary key (ID_MODIFICADOR, ID_CONTRATO)
);

alter table IMOBILIARIA.TB_CONTRATO_MODIFICADOR comment 'Tabela de controle de despesas e receitas relativos aos cont';

/*==============================================================*/
/* Table: TB_ENDERECO_PESSOA                                    */
/*==============================================================*/
create table IMOBILIARIA.TB_ENDERECO_PESSOA
(
   ID_ENDERECO_PESSOA   bigint(20) not null auto_increment comment 'Chave primaria da tabela com numeracao automatica.',
   ID_PESSOA            bigint(20) not null comment 'FK que identifica a pessoa na tabela TB_PESSOA.',
   ID_MUNICIPIO         bigint(20) not null comment 'FK que identifica o municipio e UF na tabela TB_MUNICIPIO.',
   ID_BAIRRO            bigint(20) comment 'FK que identifica o bairro na tabela TB_BAIRRO. O relacionamento e atraves da UK_BAIRRO_MUNICIPIO para garantir que o municipio seja obrigatoriamente o mesmo do endereco.',
   NU_CEP               int(8) not null comment 'Numero do CEP do endereco da pessoa.',
   DS_ENDERECO          varchar(200) not null comment 'Descricao do endereco da pessoa.',
   NU_ENDERECO          int(5) not null comment 'Numero da casa, apartamento, lote, etc.',
   DS_COMPLEMENTO       varchar(200) comment 'Descricao do complemento, informacao extra para identificacao do endereco.',
   NU_DDD               varchar(2) comment 'Numero do DDD do telefone, referente ao endereco.',
   NU_TELEFONE          int(9) comment 'Numero do telefone referente ao endereco.',
   TP_ENDERECO          char(1) not null default 'R' comment 'Tipo do endereco da pessoa. Dominio: R=Residencia; C=Cobranca.',
   primary key (ID_ENDERECO_PESSOA)
);

alter table IMOBILIARIA.TB_ENDERECO_PESSOA comment 'Tabela que armazena os enderecos da pessoa.';

/*==============================================================*/
/* Table: TB_IMOVEL                                             */
/*==============================================================*/
create table IMOBILIARIA.TB_IMOVEL
(
   ID_IMOVEL            bigint(20) not null auto_increment comment 'Chave primaria da tabela com numeracao automatica.',
   ID_LOCATARIO         bigint(20) null comment 'FK que identifica o locatario na tabela TB_LOCATARIO.',
   ID_LOCADOR			bigint(20) not null comment 'FK que identifica o locador na tabela TB_LOCADOR.',
   ID_MUNICIPIO         bigint(20) not null comment 'FK que identifica o municipio e UF na tabela TB_MUNICIPIO.',
   ID_BAIRRO            bigint(20) comment 'FK que identifica o bairro na tabela TB_BAIRRO. O relacionamento e atraves da UK_BAIRRO_MUNICIPIO para garantir que o municipio seja obrigatoriamente o mesmo do endereco.',
   DS_IMOVEL            varchar(200) not null comment 'Descricao do imovel.',
   NU_CEP               varchar(8) not null comment 'Numero do cep do endereco do imovel.',
   DS_ENDERECO          varchar(200) not null comment 'Descricao do endereco do imovel.',
   NU_ENDERECO          int(6) not null comment 'Numero da casa, apartamento, lote, etc.',
   DS_COMPLEMENTO       varchar(200) comment 'Descricao do complemento, informacao extra para identificacao do endereco.',
   DS_OBSERVACOES       varchar(2000) comment 'Observacoes gerais sobre o imovel.',
   primary key (ID_IMOVEL)
);

alter table IMOBILIARIA.TB_IMOVEL comment 'Tabela que contem os imoveis utilizados nas locacoes.';

/*==============================================================*/
/* Table: TB_LOCADOR                                            */
/*==============================================================*/
create table IMOBILIARIA.TB_LOCADOR
(
   ID_LOCADOR           bigint(20) not null auto_increment comment 'Chave primaria da tabela com numeracao automatica.',
   ID_PESSOA            bigint(20) not null comment 'FK da pessoa relacionada com o locatario, na tabela TB_PESSOA.',
   DT_CADASTRO          datetime not null comment 'Data do cadastro do locador.',
   primary key (ID_LOCADOR)
);

alter table IMOBILIARIA.TB_LOCADOR comment 'Tabela que contem os locadores.';

/*==============================================================*/
/* Table: TB_LOCATARIO                                          */
/*==============================================================*/
create table IMOBILIARIA.TB_LOCATARIO
(
   ID_LOCATARIO         bigint(20) not null auto_increment comment 'Chave primaria da tabela com numeracao automatica.',
   ID_PESSOA            bigint(20) not null comment 'FK da pessoa relacionada com o locatario, na tabela TB_PESSOA.',
   DT_CADASTRO          datetime not null comment 'Data do cadastro do locatario.',
   primary key (ID_LOCATARIO)
);

alter table IMOBILIARIA.TB_LOCATARIO comment 'Tabela que armazena os locatarios.';

/*==============================================================*/
/* Table: TB_MODIFICADOR                                        */
/*==============================================================*/
create table IMOBILIARIA.TB_MODIFICADOR
(
   ID_MODIFICADOR       bigint(20) not null auto_increment comment 'Chave primaria da tabela com numeracao automatica.',
   NO_MODIFICADOR       varchar(100) not null comment 'Nome do modificador.',
   DS_MODIFICADOR       varchar(2000) comment 'Descricao detalhada do modificador.',
   primary key (ID_MODIFICADOR)
);

alter table IMOBILIARIA.TB_MODIFICADOR comment 'Tabela de dominio que contem os modificadores possiveis de s';

/*==============================================================*/
/* Table: TB_MUNICIPIO                                          */
/*==============================================================*/
create table IMOBILIARIA.TB_MUNICIPIO
(
   ID_MUNICIPIO         bigint(20) not null auto_increment comment 'Chave primaria da tabela com numeracao automatica.',
   NO_MUNICIPIO         varchar(100) not null comment 'Nome do municipio.',
   SG_UF                char(2) not null comment 'Sigla da Unidade Federativa.',
   primary key (ID_MUNICIPIO)
);

alter table IMOBILIARIA.TB_MUNICIPIO comment 'Tabela que armazena os municipios.';

/*==============================================================*/
/* Table: TB_PESSOA                                             */
/*==============================================================*/
create table IMOBILIARIA.TB_PESSOA
(
   ID_PESSOA            bigint(20) not null auto_increment comment 'Chave primaria da tabela com numeracao automatica.',
   NU_CNPJ              varchar(14) comment 'Numero do CNPJ. Identifica a pessoa juridica. Nao e obrigatorio porque e gerenciado pela constraint CK_PESSOA_FISICA_JURIDICA e indice IDX_PESSOA_JURIDICA.',
   NU_CPF               varchar(11) comment 'Numero do CPF. Identifica a pessoa fisica. Nao e obrigatorio porque e gerenciado pela constraint CK_PESSOA_FISICA_JURIDICA e indice IDX_PESSOA_FISICA.',
   DS_EMAIL             varchar(200) not null comment 'E-mail para contato com a pessoa.',
   DT_ULTIMA_ALTERACAO  datetime not null comment 'Data da ultima alteracao.',
   DS_OBSERVACAO        varchar(2000) comment 'Observacoes gerais da pessoa.',
   primary key (ID_PESSOA),
   check (( NU_CNPJ IS NULL AND NU_CPF IS NOT NULL) OR ( NU_CNPJ IS NOT NULL AND NU_CPF IS NULL))
);

alter table IMOBILIARIA.TB_PESSOA comment 'Tabela principal que identifica as pessoas dentro do modelo.';

/*==============================================================*/
/* Index: IDX_PESSOA_FISICA                                     */
/*==============================================================*/
/*create unique index IDX_PESSOA_FISICA on IMOBILIARIA.TB_PESSOA(NU_CPF);*/

/*==============================================================*/
/* Index: IDX_PESSOA_JURIDICA                                   */
/*==============================================================*/
/*create unique index IDX_PESSOA_JURIDICA on IMOBILIARIA.TB_PESSOA(NU_CNPJ);*/

/*==============================================================*/
/* Table: TB_PESSOA_FISICA                                      */
/*==============================================================*/
create table IMOBILIARIA.TB_PESSOA_FISICA
(
   NU_CPF               varchar(11) not null comment 'Numero do CPF. Identifica a pessoa fisica como unica.',
   NO_PESSOA_FISICA     varchar(200) not null comment 'Nome da pessoa fisica.',
   NU_RG				varchar(11) null comment 'Numero da Carteira de Identidade',
   ORG_EXP				varchar(10) null comment 'Orgao expedidor da carteira de identidade',
   DS_NACIONALIDADE		varchar(50) null comment 'Nacionalidade da Pessoa fisica.',
   DS_PROFISSAO			varchar(50) null comment 'Profissao da pessoa fisica',
   DS_ESTADO_CIVIL      varchar(20) null comment 'Estado Civil da pessoa fisica',
   primary key (NU_CPF)
);

alter table IMOBILIARIA.TB_PESSOA_FISICA comment 'Tabela que contem as dados exclusivos de pessoas fisicas.';

/*==============================================================*/
/* Table: TB_PESSOA_JURIDICA                                    */
/*==============================================================*/
create table IMOBILIARIA.TB_PESSOA_JURIDICA
(
   NU_CNPJ              varchar(14) not null comment 'Numero do CNPJ. Identifica a pessoa juridica.',
   NO_RAZAO_SOCIAL      varchar(200) not null comment 'Nome da Razao Social da Pessoa Juridica.',
   NO_FANTASIA          varchar(200) not null comment 'Nome Fantasia da Pessoa Juridica.',
   NU_INSCRICAO_ESTADUAL varchar(13) not null comment 'Numero da incricao estadual da empresa.',
   NO_CONTATO           varchar(200) not null comment 'Nome do contato, principal responsavel para tratar qualquer assunto com a empresa.',
   primary key (NU_CNPJ)
);

alter table IMOBILIARIA.TB_PESSOA_JURIDICA comment 'Tabela que contem as dados exclusivos de pessoas juridicas.';

/*==============================================================*/
/* Table: TB_PESSOA_TELEFONE                                    */
/*==============================================================*/
create table IMOBILIARIA.TB_PESSOA_TELEFONE
(
   ID_PESSOA_TELEFONE   bigint(20) not null auto_increment comment 'Chave primaria da tabela com numeracao automatica.',
   ID_PESSOA            bigint(20) not null comment 'FK da tabela TB_PESSOA.',
   NU_TELEFONE_DDD      varchar(2) not null comment 'Numero do DDD do telefone.',
   NU_TELEFONE          int(9) not null comment 'Numero do telefone sem mascara.',
   TP_TELEFONE          char(1) not null comment 'Identifica o tipo do telefone. Dominio: P=Principal; F=Fax; C=Celular ',
   primary key (ID_PESSOA_TELEFONE)
);

alter table IMOBILIARIA.TB_PESSOA_TELEFONE comment 'Tabela que contem os telefones da pessoa.';

alter table IMOBILIARIA.TB_CONTRATO
      add ID_IMOVEL bigint(20) not null comment 'FK que identifica o imovel na tabela TB_IMOVEL.';
      
alter table IMOBILIARIA.TB_CONTRATO add constraint FK_CONTRATO_IMOVEL foreign key (ID_IMOVEL)
      references IMOBILIARIA.TB_IMOVEL (ID_IMOVEL) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_BAIRRO add constraint FK_BAIRRO_MUNICIPIO foreign key (ID_MUNICIPIO)
      references IMOBILIARIA.TB_MUNICIPIO (ID_MUNICIPIO) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_CONTRATO add constraint FK_CONTRATO_LOCADOR foreign key (ID_LOCADOR)
      references IMOBILIARIA.TB_LOCADOR (ID_LOCADOR) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_CONTRATO add constraint FK_CONTRATO_LOCATARIO foreign key (ID_LOCATARIO)
      references IMOBILIARIA.TB_LOCATARIO (ID_LOCATARIO) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_CONTRATO add constraint FK_CONTRATO_PESSOA foreign key (ID_PESSOA_FIADOR)
      references IMOBILIARIA.TB_PESSOA (ID_PESSOA) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_CONTRATO_MODIFICADOR add constraint FK_DESPESACONTRATO_CONTRATO foreign key (ID_CONTRATO)
      references IMOBILIARIA.TB_CONTRATO (ID_CONTRATO) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_CONTRATO_MODIFICADOR add constraint FK_DESPESACONTRATO_MODIFICADOR foreign key (ID_MODIFICADOR)
      references IMOBILIARIA.TB_MODIFICADOR (ID_MODIFICADOR) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_ENDERECO_PESSOA add constraint FK_ENDERECOPESSOA_BAIRRO foreign key (ID_BAIRRO)
      references IMOBILIARIA.TB_BAIRRO (ID_BAIRRO) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_ENDERECO_PESSOA add constraint FK_ENDERECOPESSOA_MUNICIPIO foreign key (ID_MUNICIPIO)
      references IMOBILIARIA.TB_MUNICIPIO (ID_MUNICIPIO) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_ENDERECO_PESSOA add constraint FK_ENDERECOPESSOA_PESSOA foreign key (ID_PESSOA)
      references IMOBILIARIA.TB_PESSOA (ID_PESSOA) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_IMOVEL add constraint FK_IMOVEL_BAIRRO foreign key (ID_BAIRRO)
      references IMOBILIARIA.TB_BAIRRO (ID_BAIRRO) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_IMOVEL add constraint FK_IMOVEL_LOCATARIO foreign key (ID_LOCATARIO)
      references IMOBILIARIA.TB_LOCATARIO (ID_LOCATARIO) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_IMOVEL add constraint FK_IMOVEL_LOCADOR foreign key (ID_LOCADOR)
      references IMOBILIARIA.TB_LOCADOR (ID_LOCADOR) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_IMOVEL add constraint FK_IMOVEL_MUNICIPIO foreign key (ID_MUNICIPIO)
      references IMOBILIARIA.TB_MUNICIPIO (ID_MUNICIPIO) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_LOCADOR add constraint FK_LOCADOR_PESSOA foreign key (ID_PESSOA)
      references IMOBILIARIA.TB_PESSOA (ID_PESSOA) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_LOCATARIO add constraint FK_LOCATARIO_PESSOA foreign key (ID_PESSOA)
      references IMOBILIARIA.TB_PESSOA (ID_PESSOA) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_PESSOA add constraint FK_PESSOA_PESSOAFISICA foreign key (NU_CPF)
      references IMOBILIARIA.TB_PESSOA_FISICA (NU_CPF) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_PESSOA add constraint FK_PESSOA_PESSOAJURIDICA foreign key (NU_CNPJ)
      references IMOBILIARIA.TB_PESSOA_JURIDICA (NU_CNPJ) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_PESSOA_TELEFONE add constraint FK_PESSOATELEFONE_PESSOA foreign key (ID_PESSOA)
      references IMOBILIARIA.TB_PESSOA (ID_PESSOA) on delete restrict on update restrict;
