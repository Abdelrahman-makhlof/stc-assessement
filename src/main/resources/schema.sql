CREATE TABLE ITEM (
                      ITEM_ID SERIAL PRIMARY KEY,
                      TYPE VARCHAR (10) NOT NULL,
                      NAME VARCHAR (50) NOT NULL,
                      PARENT_ID INTEGER,
                      GROUP_ID INTEGER
);

CREATE TABLE PERMISSION (
                            PERMISSION_ID SERIAL PRIMARY KEY,
                            USER_EMAIL VARCHAR (50) NOT NULL,
                            PERMISSION_LEVEL VARCHAR (10) NOT NULL,
                            GROUP_ID INTEGER
);

CREATE TABLE PERMISSION_GROUP (
                                  GROUP_ID SERIAL PRIMARY KEY,
                                  GROUP_NAME VARCHAR (50) NOT NULL,
);

CREATE TABLE FILE (
                      FILE_ID SERIAL PRIMARY KEY,
                      BINARY_DATA bytea ,
                      ITEM_ID INTEGER
);


ALTER TABLE PERMISSION
    ADD CONSTRAINT PERMISSION_FK
        FOREIGN KEY (GROUP_ID)
            REFERENCES PERMISSION_GROUP (GROUP_ID);

ALTER TABLE ITEM
    ADD CONSTRAINT PERMISSION_FK
        FOREIGN KEY (GROUP_ID)
            REFERENCES PERMISSION_GROUP (GROUP_ID);



