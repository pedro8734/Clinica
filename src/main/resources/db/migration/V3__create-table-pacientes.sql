create table pacientes (

id bigint  not null auto_increment,
nombre varchar(100) not null,
email varchar(100) not null unique,
telefono varchar(20) not null,
documento varchar(12) not null unique,
calle varchar(100) not null,
barrio varchar(100) not null,
codigo_postal varchar(12) not null,
complemento varchar(100),
numero varchar(20),
ciudad varchar(100) not null,
estado varchar(100) not null,

primary key(id)

);