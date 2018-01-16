INSERT INTO Role (id, roleName, description) VALUES (1, 'STANDARD_USER', 'Standard User - Has no admin rights');
INSERT INTO Role (id, roleName, description) VALUES (2, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');

-- USER
-- non-encrypted password: password
INSERT INTO User (id, firstname, lastname, password, username) VALUES (1, 'Aleksey', 'Borsch', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'borsch');
INSERT INTO User (id, firstname, lastname, password, username) VALUES (2, 'Admin', 'Admin', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'admin');
INSERT INTO User (id, firstname, lastname, password, username) VALUES (3, 'Ivan', 'Ivanov', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'ivanov');


INSERT INTO User_Role(userId, roleId) VALUES(1,1);
INSERT INTO User_Role(userId, roleId) VALUES(2,1);
INSERT INTO User_Role(userId, roleId) VALUES(2,2);
INSERT INTO User_Role(userId, roleId) VALUES(3,1);

INSERT INTO Disc (name, description, owner_id, posterURL) VALUES
  ('Mad Max', 'Owesome', 1, 'https://i.pinimg.com/736x/48/dc/81/48dc81518e59d352ce9a36f413d0651d--paul-shipper-mad-max-.jpg'),
  ('Blade Runner', 'Owesome', 2, 'http://i.ebayimg.com/images/i/191110117883-0-1/s-l1000.jpg'),
  ('Pulp Fiction', 'Owesome', 1, 'http://images.fanpop.com/images/image_uploads/Pulp-Fiction-poster-quentin-tarantino-75036_500_495.jpg'),
  ('Pulp Fiction', 'Owesome', 3, 'http://images.fanpop.com/images/image_uploads/Pulp-Fiction-poster-quentin-tarantino-75036_500_495.jpg'),
  ('The Matrix','Good', 3,'http://image.tmdb.org/t/p/w342//gynBNzwyaHKtXqlEKKLioNkjKgN.jpg');

INSERT INTO Disk_User (diskId, userId) VALUES
(1,2);

commit;
