drop database if exists sigem;
create database sigem;
use sigem;

create table sala(
salid int primary key auto_increment,
salnome varchar(20) not null,
salativa boolean default true
);
insert into sala(salnome) values('LAB V'), ('LAB VII');


create table materia(
matid int primary key auto_increment,
matnome varchar(50) not null,
matativa boolean default true
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
insert into usuario values('','Indefinido'),('111.111.111-11', 'Sandro'),
('222.222.222-22', 'Izaltino'), ('333.333.333-33', 'Ruy'),
('444.444.444-44', 'Estaife'), ('555.555.555-55', 'Gustavo');

create table funcao(
fcoid int primary key auto_increment,
fconome varchar(20) not null
);
insert into funcao(fconome) values('Aluno'), ('Coordenador'), ('Monitor');

create table tipousuario(
tususucpf varchar(15) not null,
tusfcoid int not null,
tusdatainicio date not null,
tusdatafim date default null,
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
('444.444.444-44', 3, '2018-10-17', 3),
('555.555.555-55', 3, '2018-10-17', 2);

create table monitoria(
miaid int primary key auto_increment,
miavagas tinyint default 12,
miasalid int not null,
miausucpf varchar(15) not null default '', 
miadiaid int not null,
miahorhora varchar(15) not null,
miaativa boolean default true,
foreign key (miasalid) references sala(salid),
foreign key (miausucpf) references usuario(usucpf),
foreign key (miadiaid) references diadasemana(diaid),
foreign key (miahorhora) references horario(horhora)
);
insert into monitoria(miasalid,miadiaid,miausucpf,miahorhora) values
(1, 1, '', '11:00-12:00'),
(1, 1, '', '12:00-13:00'),
(1, 2, '', '11:00-12:00'),
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
('111.111.111-11', 5);

select * from inscricao;


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

delimiter #
create function f_verificaconflitomonitor(p_miadiaid int, p_miahorhora varchar(15), p_moncpf varchar(15))returns int
begin
	declare v_retorno int default 0;
    
    set v_retorno=(select miaid from monitoria
					where miadiaid = p_miadiaid
                    AND miahorhora = p_miahorhora
                    AND miausucpf = p_moncpf);
	if(v_retorno is null)then
		set v_retorno=0;
	end if;
	return v_retorno;
end#
delimiter ;
select f_verificaconflitomonitor(2, '11:00-12:00', '555.555.555-55');

delimiter #
create function f_contmonitorias(moncpf varchar(15)) returns int
begin
	return (SELECT count(*) FROM monitoria WHERE miausucpf = moncpf);
end #
delimiter ;
select f_contmonitorias('555.555.555-55');

delimiter #
create procedure sp_consultamonitoria(p_miaid int)
begin
    SELECT salid, salnome, horhora, diaid, dianome,
    monitor.usucpf 'moncpf', monitor.usunome 'monnome',
    matid, matnome, miaid, miavagas
    FROM monitoria
	INNER JOIN sala ON salid = miasalid
	INNER JOIN horario ON horhora = miahorhora
	INNER JOIN diadasemana ON diaid = miadiaid
	INNER JOIN usuario monitor ON monitor.usucpf = miausucpf
    INNER JOIN tipousuario ON tususucpf = monitor.usucpf
    INNER JOIN materia ON matid = tusmatid
	WHERE miaid = p_miaid
	order by diaid asc, matnome asc, horhora asc;
end#
delimiter ;


-- Procedure para consultar por sala quais aulas de monitorias existem
-- MonitoriaDAO >>>> consultarMonitoria() utilizando a sql
delimiter #
create procedure sp_consultamonitoriascoord(p_salnome varchar(20))
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
	WHERE salnome LIKE p_salnome
    
	order by diaid asc, matnome asc, horhora asc;
end#
delimiter ;
call sp_consultamonitoriascoord('LAB V');


-- Procedure para consultar por matéria quais aulas de monitorias estão disponíveis para inscrição
-- MonitoriaDAO >>>> consultarMonitoria() utilizando a sql2
delimiter #
create procedure sp_consultamonitoriasdisponiveis(p_matnome varchar(50), p_alucpf varchar(15))
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
call sp_consultamonitoriasdisponiveis('ATP', '111.111.111-11');


-- Procedure voltada ao aluno para consultar por matéria quais aulas de monitorias estou inscrito
-- MonitoriaDAO >>>> consultarMonitoria() utilizando a sql3
delimiter #
create procedure sp_consultamonitoriasinscrito(p_matnome varchar(50), p_alucpf varchar(15))
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
call sp_consultamonitoriasinscrito('ED', '111.111.111-11');


-- Procedure para consultar por matéria quais aulas de monitorias estou inscrito
-- MonitoriaDAO >>>> consultarMonitoria() utilizando a sql3
delimiter #
create procedure sp_consultamonitoriaslivres(p_salnome varchar(20))
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
	WHERE salnome LIKE p_salnome
    AND monitor.usucpf = ''
	order by diaid asc, matnome asc, horhora asc;
end#
delimiter ;
call sp_consultamonitoriaslivres('LAB V');

delimiter #
create procedure sp_consultamonitoriasmonitor(p_salnome varchar(20), p_moncpf varchar(15))
begin
	SELECT salid, salnome, horhora, diaid, dianome,
    monitor.usucpf 'moncpf', monitor.usunome 'monnome',
    matid, matnome, miaid, miavagas
    FROM monitoria
	INNER JOIN sala ON salid = miasalid
	INNER JOIN horario ON horhora = miahorhora
	INNER JOIN diadasemana ON diaid = miadiaid
	INNER JOIN usuario monitor ON monitor.usucpf = miausucpf
    INNER JOIN tipousuario ON tususucpf = monitor.usucpf
    INNER JOIN funcao ON fcoid = tusfcoid
    INNER JOIN materia ON matid = tusmatid
	WHERE salnome LIKE p_salnome
    AND monitor.usucpf = p_moncpf    
	order by diaid asc, matnome asc, horhora asc;
end#
delimiter ;
CALL  sp_consultamonitoriasmonitor('LAB VII', '555.555.555-55');

delimiter #
create procedure sp_monitorcheckboxmarcado(p_miaid int, p_moncpf varchar(15))
begin
	declare v_moncpf varchar(15);
    
    set v_moncpf = (SELECT miausucpf FROM monitoria WHERE miaid = p_miaid);
    
    if v_moncpf != p_moncpf then
		UPDATE monitoria SET miausucpf = p_moncpf WHERE miaid = p_miaid;
	end if;
end#
delimiter ;

delimiter #
create procedure sp_monitorcheckboxdesmarcado(p_miaid int, p_moncpf varchar(15))
begin
	declare v_moncpf varchar(15);
    
    set v_moncpf = (SELECT miausucpf FROM monitoria WHERE miaid = p_miaid);
    
    if v_moncpf = p_moncpf then
		UPDATE monitoria SET miausucpf = '' WHERE miaid = p_miaid;
	end if;
end#
delimiter ;

delimiter #
create procedure sp_consultaalunos()
begin
	SELECT usucpf, usunome FROM usuario
    INNER JOIN tipousuario ON tususucpf = usucpf
    INNER JOIN funcao ON fcoid = tusfcoid
    WHERE usucpf NOT IN (
						SELECT tususucpf FROM tipousuario
                        INNER JOIN funcao ON fcoid = tusfcoid
						WHERE fconome = 'Monitor'AND tusdatafim IS NULL
                        )
	AND fconome = 'Aluno';
end#
delimiter ;

delimiter #
create procedure sp_consultamonitores(p_monnome varchar(50))
begin
	SELECT usucpf, usunome, matid, matnome FROM usuario
    INNER JOIN tipousuario ON tususucpf = usucpf
    INNER JOIN funcao ON fcoid = tusfcoid
    INNER JOIN materia ON tusmatid = matid
    WHERE tusdatafim is null
    AND usunome LIKE p_monnome
    ORDER BY usunome;
end#
delimiter ;


delimiter #
create procedure sp_consultamateriasativas()
begin
	SELECT * FROM materia
    WHERE matativa = true
    AND matid != 1;
end#
delimiter ;

delimiter #
create procedure sp_consultamaterias()
begin
	SELECT * FROM materia
    WHERE matid != 1;
end#
delimiter ;

delimiter #
create function f_inscreveraluno(p_alucpf varchar(15), p_miaid int) returns varchar(50)
begin
    declare v_vagas int;
    declare v_materia varchar(20);
    declare v_hora varchar(20);
    declare v_dia varchar(20);
    
    DECLARE excessao SMALLINT DEFAULT 0;
    
    set v_vagas = (SELECT miavagas FROM monitoria WHERE miaid = p_miaid);
    if(v_vagas>0)then
		INSERT INTO inscricao VALUES (p_alucpf, p_miaid);
        UPDATE monitoria SET miavagas = miavagas -1 WHERE miaid = p_miaid;
        RETURN '';
	else
		SELECT matnome, dianome, miahorhora
        INTO v_materia, v_hora, v_dia
        FROM monitoria
		INNER JOIN usuario ON usucpf = miausucpf
		INNER JOIN tipousuario ON usucpf = tususucpf
		INNER JOIN materia ON tusmatid = matid
        INNER JOIN diadasemana ON miadiaid = diaid
		WHERE miaid = p_miaid;
		RETURN concat('Não há vagas para monitoria de\n ', v_materia, ' na ', v_dia, ' as ', v_hora);
	end if;
    
end#
delimiter ;

delimiter #
create procedure sp_consultasalasativas()
begin
	SELECT * FROM sala WHERE salativa = true;
end#
delimiter ;

delimiter #
create procedure sp_registrarmonitor(p_moncpf varchar(15), p_matid int)
begin
	declare v_dia date;
    
    set v_dia = curdate();
    INSERT INTO tipousuario(tususucpf, tusfcoid, tusdatainicio, tusmatid) 
    values(p_moncpf, 3, v_dia, p_matid);
end #
delimiter;

delimiter #
create procedure sp_inativarmonitor(p_moncpf varchar(15), p_matid int)
begin
	declare v_dia date;
    
    set v_dia = curdate();
    UPDATE tipousuario 
    SET tusdatafim = v_dia
    WHERE tususucpf = p_moncpf 
    AND tusdatafim is null
    AND tusmatid is not null;
end #
delimiter;


-- Procedure para consultar as horas disponíveis para uma sala em um determinado dia
-- HorarioDAO >>> consultarHora()
delimiter #
create procedure sp_consultarhorasdisponiveis(p_salnome varchar(20), p_dianome varchar(20))
begin
	SELECT * FROM horario
	WHERE horhora NOT IN (SELECT miahorhora FROM monitoria
							INNER JOIN sala ON salid = miasalid
							INNER JOIN diadasemana ON diaid = miadiaid
							WHERE salnome = p_salnome AND dianome = p_dianome);
end#
delimiter ;
call sp_consultarhorasdisponiveis('LAB V', 'Segunda');