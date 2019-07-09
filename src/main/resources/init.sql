/*
    Database initialization script that runs on every web-application redeployment.
*/
DROP TABLE IF EXISTS users CASCADE ;
DROP TABLE IF EXISTS userInfos CASCADE ;

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       email TEXT UNIQUE NOT NULL,
                       password TEXT NOT NULL,
                       balance integer not null,
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

INSERT INTO users (email, password) VALUES
('user1@user1.hu', 'user1', 0), -- 1
('user2@user2.hu', 'user2', 0), -- 2
('user2@user3.hu', 'user3', 0); -- 3

insert into userInfos (address, name, phoneNumber, userId) VALUES
('Budapest', 'name name', '0687254537732', 1),
('Nyíregyháza', 'nam2 gyula', '123213124442', 2),
('Siófok', 'név név', '2138765853243', 3);
