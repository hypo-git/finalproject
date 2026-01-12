ALTER TABLE users
    ADD logo_username varchar(255)
    GO

ALTER TABLE users_aud
    ADD logo_username varchar(255)
    GO

ALTER TABLE users_aud
    ADD logo_username_mod bit GO

DECLARE @sql [nvarchar](MAX)
SELECT @sql = N'ALTER TABLE users DROP CONSTRAINT ' + QUOTENAME([df].[name])
FROM [sys].[columns] AS [c]
    INNER JOIN [sys].[default_constraints] AS [df]
ON [df].[object_id] = [c].[default_object_id]
WHERE [c].[object_id] = OBJECT_ID(N'users')
  AND [c].[name] = N'logo_user_name'
    EXEC sp_executesql @sql
    GO

ALTER TABLE users
    DROP COLUMN logo_user_name
    GO