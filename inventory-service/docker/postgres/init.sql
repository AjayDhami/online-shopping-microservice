\c postgres
SELECT 'CREATE DATABASE "inventory-service"'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'inventory-service')\gexec
