
-- users
INSERT INTO users (name, email) VALUES ('John Doe', 'john.doe@example.com');
INSERT INTO users (name, email) VALUES ('Jane Smith', 'jane.smith@example.com');
INSERT INTO users (name, email) VALUES ('Peter Jones', 'peter.jones@example.com');


-- requests
INSERT INTO requests (description, requestor_id, created) VALUES ('Need a hammer for home repair', 1, NOW());
INSERT INTO requests (description, requestor_id, created) VALUES ('Looking for a ladder for painting', 2, NOW());


-- items
INSERT INTO items (name, description, available, owner_id, request_id) VALUES ('Hammer', 'A sturdy hammer', true, 1, 1);
INSERT INTO items (name, description, available, owner_id, request_id) VALUES ('Ladder', 'A tall ladder', false, 3, 2);
INSERT INTO items (name, description, available, owner_id) VALUES ('Drill', 'Electric drill', true, 1);


-- bookings
INSERT INTO bookings (starting, ending, item_id, booker_id, status) VALUES (NOW(), NOW() + interval '1 day', 1, 2, 'APPROVED');
INSERT INTO bookings (starting, ending, item_id, booker_id, status) VALUES (NOW(), NOW() + interval '2 days', 2, 1, 'WAITING');


-- comments
INSERT INTO comments (text, item_id, author_id, created) VALUES ('Great hammer!', 1, 2, NOW());
INSERT INTO comments (text, item_id, author_id, created) VALUES ('Works perfectly!', 1, 1, NOW());