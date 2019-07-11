/*
    Database initialization script that runs on every web-application redeployment.
*/
DROP TABLE IF EXISTS users CASCADE ;
DROP TABLE IF EXISTS userInfos CASCADE ;
DROP TABLE IF EXISTS games CASCADE ;


CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       email TEXT UNIQUE NOT NULL,
                       password TEXT NOT NULL,
                       balance integer not null,
                       role varchar(15) not null ,
                       CONSTRAINT email_not_empty CHECK (email <> ''),
                       CONSTRAINT password_not_empty CHECK (password <> '')
);

CREATE TABLE userInfos (
                           id SERIAL PRIMARY KEY,
                           address TEXT ,
                           name varchar(25) ,
                           phoneNumber varchar(20) ,
                           userId int not null ,
                           foreign key (userId) references users(id)
);

CREATE TABLE games (
    id serial primary key ,
    name text not null ,
    platform text not null , --enumra
    imageUrl text not null ,
    price integer not null
);

INSERT INTO users (email, password, balance, role) VALUES
('user1@user1.hu', 'user1', 0, 'REGISTERED'),
('user2@user2.hu', 'user2', 0, 'REGISTERED'),
('user2@user3.hu', 'user3', 0, 'ADMIN');

insert into userInfos (address, name, phoneNumber, userId) VALUES
('Budapest', 'name name', '0687254537732', 1),
('Nyíregyháza', 'nam2 gyula', '123213124442', 2),
('Siófok', 'név név', '2138765853243', 3);

insert into games (name, platform, imageUrl, price) VALUES
('Spider-Man', 'PS4', 'https://p1.akcdn.net/full/422035987.sony-marvel-spider-man-ps4.jpg', 50),
('God of War', 'PS4', 'https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.eF4doJiTCFL4q0nOfuNQBAHaI_%26pid%3DApi&f=1', 50);
