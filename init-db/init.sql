SELECT 'CREATE DATABASE userdb' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'userdb')\gexec
SELECT 'CREATE DATABASE resourcedb' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'resourcedb')\gexec
SELECT 'CREATE DATABASE bookingdb' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'bookingdb')\gexec