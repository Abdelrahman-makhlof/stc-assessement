
INSERT INTO PERMISSION_GROUP (GROUP_ID, GROUP_NAME) values (100,'admin');

INSERT INTO PERMISSION  (PERMISSION_ID , USER_EMAIL ,PERMISSION_LEVEL ,  GROUP_ID ) VALUES (1,'ahmed@stc.com', 'VIEW',100);
INSERT INTO PERMISSION  (PERMISSION_ID , USER_EMAIL , PERMISSION_LEVEL , GROUP_ID ) VALUES (2,'ali@stc.com', 'EDIT',100);