drop database if exists sigem;
create database sigem;
use sigem;

create table sala(
salid int primary key auto_increment,
salnome varchar(20) not null
);
insert into sala(salnome) values('LAB V'), ('LAB VII');


create table materia(
matid int primary key auto_increment,
matnome varchar(50) not null
);
insert into materia(matnome) values('Indefinido'),('ATP'), ('ED');


create table horario(
horhora varchar(15) primary key
);
insert into horario values ('11:00-12:00'), ('12:00-13:00'), ('13:00-14:00'), ('14:00-15:00'), ('15:00-16:00');

# Isso sim é uma modelo de verdade.
#Nao coloca só o horario inicial
# coloca os dois assim `11:00-12:00`
--                   -Neto, Izaltino

create table diadasemana(
diaid int primary key auto_increment,
dianome varchar (20) not null
);
insert into diadasemana(dianome) values ('Segunda'), ('Terça'), ('Quarta'), ('Quinta'), ('Sexta');

create table usuario(
usucpf varchar(15) primary key,
usunome varchar(50) not null
);
insert into usuario values('','Indefinido'),('111.111.111-11', 'Sandro'), ('222.222.222-22', 'Izaltino'), ('333.333.333-33', 'Ruy'),
('444.444.444-44', 'Estaife'), ('555.555.555-55', 'Gustavo');

create table funcao(
fcoid int primary key auto_increment,
fconome varchar(20) not null
);
insert into funcao(fconome) values('Aluno'), ('Coordenador'), ('Monitor');

create table tipousuario(
tususucpf varchar(15),
tusfcoid int,
tusdatainicio date not null,
tusdatafim date,
tusmatid int,
foreign key (tususucpf) references usuario(usucpf),
foreign key (tusfcoid) references funcao(fcoid),
foreign key (tusmatid) references materia(matid),
primary key(tususucpf,tusfcoid, tusdatainicio)
);
insert into tipousuario (tususucpf, tusfcoid, tusdatainicio, tusmatid) 
values ('111.111.111-11', 1, '2018-10-17', null),
('222.222.222-22', 1, '2018-10-17', null),
('333.333.333-33', 1, '2018-10-17', null),
('444.444.444-44', 2, '2018-10-17', 3),
('555.555.555-55', 2, '2018-10-17', 2);

create table monitoria(
miaid int primary key auto_increment,
miavagas tinyint default 12,
miasalid int not null,
miausucpf varchar(15) not null default '', 
miadiaid int not null,
miahorhora varchar(15) not null,
foreign key (miasalid) references sala(salid),
foreign key (miausucpf) references usuario(usucpf),
foreign key (miadiaid) references diadasemana(diaid),
foreign key (miahorhora) references horario(horhora)
);
insert into monitoria(miasalid,miadiaid,miausucpf,miahorhora) values
(1, 1, '', '11:00-12:00'),
(1, 1, '', '12:00-13:00'),
(2, 2, '555.555.555-55', '11:00-12:00'),
(2, 2, '555.555.555-55', '12:00-13:00'),
(1, 2, '444.444.444-44', '12:00-13:00');


create table inscricao(
insusucpf varchar(15) not null,
insmiaid int not null,
primary key (insusucpf, insmiaid),
foreign key (insusucpf) references usuario(usucpf),
foreign key (insmiaid) references monitoria(miaid)
);
insert into inscricao values
('111.111.111-11', 3),
('111.111.111-11', 5);

select * from inscricao;

/*create table materiamonitor(
mmtusucpf varchar(15) not null,
mmtmatid int,
mmtativo boolean default true,
foreign key (mmtusucpf) references usuario(usucpf),
foreign key (mmtmatid) references materia(matid),
primary key(mmtusucpf, mmtmatid)
);
insert into materiamonitor(mmtusucpf, mmtmatid)values
('444.444.444-44', 3), ('555.555.555-55', 2);*/


-- Procedure que retorna o ID da aula da monitoria que está gerando conflito de horário durante a inscrição
-- MonitoriaDAO >>>> verificaConflito()
delimiter #
create function f_verificaconflito(p_miadiaid int, p_miahorhora varchar(15), p_alucpf varchar(15))returns int
begin
	declare v_retorno int default 0;
    
    set v_retorno=(select miaid from monitoria
					inner join inscricao on miaid = insmiaid
                    inner join diadasemana on miadiaid = diaid
                    where miadiaid = p_miadiaid and insusucpf = p_alucpf
                    and p_miahorhora = miahorhora);
	if(v_retorno is null)then
		set v_retorno=0;
	end if;
	return v_retorno;
end#
delimiter ;
select f_verificaconflito(2, '11:00-12:00', '111.111.111-11');


-- Procedure para consultar por sala quais aulas de monitorias existem
-- MonitoriaDAO >>>> consultarMonitoria() utilizando a sql
delimiter #
create procedure p_consultamonitoriacoord(p_salnome varchar(20))
begin
	SELECT salid, salnome, horhora, diaid, dianome,
    monitor.usucpf 'moncpf', monitor.usunome 'monnome',
    matid, matnome, insusucpf 'insalucpf',
    miaid, miavagas
    FROM monitoria
	INNER JOIN sala ON salid = miasalid
	INNER JOIN horario ON horhora = miahorhora
	INNER JOIN diadasemana ON diaid = miadiaid
	LEFT OUTER JOIN usuario monitor ON monitor.usucpf = miausucpf
    LEFT OUTER JOIN tipousuario ON tususucpf = monitor.usucpf
    LEFT OUTER JOIN funcao ON fcoid = tusfcoid
    LEFT OUTER JOIN materia on matid = tusmatid
    LEFT OUTER JOIN inscricao ON miaid = insmiaid
	WHERE salnome LIKE concat('%', p_salnome,'%')
    
	order by diaid asc, matnome asc, horhora asc;
end#
delimiter ;
call p_consultamonitoriacoord('LAB V');


-- Procedure para consultar por matéria quais aulas de monitorias estão disponíveis para inscrição
-- MonitoriaDAO >>>> consultarMonitoria() utilizando a sql2
delimiter #
create procedure p_consultamonitoriasdisponiveis(p_matnome varchar(50), p_alucpf varchar(15))
begin
    SELECT salid, salnome, horhora, diaid, dianome,
    monitor.usucpf 'moncpf', monitor.usunome 'monnome',
    matid, matnome, miaid, miavagas, insusucpf 'insalucpf'
    FROM monitoria
	INNER JOIN sala ON salid = miasalid
	INNER JOIN horario ON horhora = miahorhora
	INNER JOIN diadasemana ON diaid = miadiaid
	INNER JOIN usuario monitor ON monitor.usucpf = miausucpf
    INNER JOIN tipousuario ON tususucpf = monitor.usucpf
    INNER JOIN materia ON matid = tusmatid
	LEFT OUTER JOIN
    (SELECT * FROM inscricao WHERE insusucpf LIKE p_alucpf) as inscricao
    on miaid = insmiaid
	WHERE matnome LIKE p_matnome AND matid != 1 AND miavagas > 0
	order by diaid asc, matnome asc, horhora asc;
end#
delimiter ;
call p_consultamonitoriasdisponiveis('ATP', '111.111.111-11');


-- Procedure para consultar por matéria quais aulas de monitorias estou inscrito
-- MonitoriaDAO >>>> consultarMonitoria() utilizando a sql3
delimiter #
create procedure p_consultamonitoriasinscrito(p_matnome varchar(50), p_alucpf varchar(15))
begin
	SELECT salid, salnome, horhora, diaid, dianome,
    monitor.usucpf 'moncpf', monitor.usunome 'monnome',
    matid, matnome, insusucpf 'insalucpf',
    miaid, miavagas
	FROM monitoria
	INNER JOIN sala ON salid = miasalid
	INNER JOIN horario ON horhora = miahorhora
	INNER JOIN diadasemana ON diaid = miadiaid
    INNER JOIN inscricao ON miaid = insmiaid
	INNER JOIN usuario aluno ON aluno.usucpf = insusucpf
	INNER JOIN usuario monitor ON monitor.usucpf = miausucpf
    INNER JOIN tipousuario ON tususucpf = miausucpf
    INNER JOIN materia ON matid = tusmatid
	
	WHERE matnome LIKE p_matnome AND insusucpf LIKE  p_alucpf
	order by diaid asc, matnome asc, horhora asc;
end#
delimiter ;
call p_consultamonitoriasinscrito('ED', '111.111.111-11');


-- Procedure para consultar por matéria quais aulas de monitorias estou inscrito
-- MonitoriaDAO >>>> consultarMonitoria() utilizando a sql3
delimiter #
create procedure p_consultamonitoriaslivres(p_salnome varchar(20))
begin
	SELECT salid, salnome, horhora, diaid, dianome,
    monitor.usucpf 'moncpf', monitor.usunome 'monnome',
    matid, matnome, insusucpf 'insalucpf',
    miaid, miavagas
    FROM monitoria
	INNER JOIN sala ON salid = miasalid
	INNER JOIN horario ON horhora = miahorhora
	INNER JOIN diadasemana ON diaid = miadiaid
	LEFT OUTER JOIN usuario monitor ON monitor.usucpf = miausucpf
    LEFT OUTER JOIN tipousuario ON tususucpf = monitor.usucpf
    LEFT OUTER JOIN funcao ON fcoid = tusfcoid
    LEFT OUTER JOIN materia on matid = tusmatid
    LEFT OUTER JOIN inscricao ON miaid = insmiaid
	WHERE salnome LIKE concat('%', p_salnome,'%')
    AND monitor.usucpf = ''
	order by diaid asc, matnome asc, horhora asc;
end#
delimiter ;
call p_consultamonitoriaslivres('LAB V');


-- Procedure para consultar as horas disponíveis para uma sala em um determinado dia
-- HorarioDAO >>> consultarHora()
delimiter #
create procedure p_consultarhorasdisponiveis(p_salnome varchar(20), p_dianome varchar(20))
begin
	SELECT * FROM horario
	where horhora not in (select miahorhora from monitoria
							inner join sala on salid = miasalid
							inner join diadasemana on diaid = miadiaid
							where salnome = p_salnome and dianome = p_dianome);
end#
delimiter ;
call p_consultarhorasdisponiveis('LAB V', 'Segunda');

