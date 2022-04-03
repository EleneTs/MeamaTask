# MeamaTask

Application uses postgresql databases

When Application is started - one admin is created
username: test@test.com
passsword: test


There are 3 privileges: CREATE_PRIVILEGE, UPDATE_PRIVILEGE, DELETE_PRIVILEGE
Admin can create different roles with given privileges

Alredy created roles:
ROLE_ADMIN with all privileges
ROLE_USER with only UPDATE_PRIVILEGE


Only users with ADMIN role can create other users and create other roles
