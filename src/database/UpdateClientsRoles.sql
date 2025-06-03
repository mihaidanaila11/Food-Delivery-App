    CREATE PROCEDURE updateClientsRoles()
    BEGIN

    DECLARE fetchedUserID varchar(40);
    DECLARE hasRoleCount int;

    DECLARE done tinyint default false;
     DECLARE clients CURSOR FOR SELECT UserID FROM clients;


     DECLARE hasRole CURSOR FOR
            SELECT COUNT(UserID)
            FROM userroles
            WHERE userID = fetchedUserID
            AND RoleID = (SELECT RoleID FROM roles WHERE Upper(RoleName) = 'CLIENT');
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

        OPEN clients;
     updateLoop: LOOP
        FETCH clients INTO fetcheduserID;
        if done THEN
            LEAVE updateLoop;
        END IF;

        OPEN hasRole;

        FETCH hasRole INTO hasRoleCount;

        IF hasRoleCount = 0 THEN
            INSERT INTO userroles (UserID, RoleID)
            VALUES (fetchedUserID, (SELECT RoleID FROM roles WHERE Upper(RoleName) = 'CLIENT'));
        END IF;

        CLOSE hasRole;

     end loop;

     CLOSE clients;
    END;

DROP PROCEDURE IF EXISTS updateClientsRoles;

CALL updateClientsRoles();