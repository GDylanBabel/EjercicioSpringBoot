CREATE TABLE account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_Number VARCHAR(255),
    user_Name VARCHAR(255),
    income DOUBLE
);

CREATE TABLE transference (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE,
    code VARCHAR(255),
    sender_Account VARCHAR(255),
    receiver_Account VARCHAR(255),
    concept VARCHAR(255)
);

INSERT INTO Account (id, account_Number, account_Name, income) VALUES (3, 'ES2114650100722030876293', 'John Doe', 5000.00);
INSERT INTO Account (id, account_Number, account_Name, income) VALUES (2, 'ES331415B202015555555555', 'Jane Smith', 7000.00);

INSERT INTO Transference (id, amount, code, sender_Account, receiver_Account, concept) VALUES (4, 1000.00, 'TR123', 'ACC123', 'ACC456', 'Payment for services');
INSERT INTO Transference (id, amount, code, sender_Account, receiver_Account, concept) VALUES (5, 500.00, 'TR456', 'ACC456', 'ACC123', 'Refund for returned goods');
