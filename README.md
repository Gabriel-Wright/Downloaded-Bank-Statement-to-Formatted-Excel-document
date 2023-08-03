# Bank Statement to Formatted Excel Output program

A command line program used to read bank statement files, downloaded from online banking sites, which are then formatted into an SQLite DB.
These SQLite DB's can then be exported into a formatted Excel File within the program.

## Features

1) Import Bank Statements into SQLite DB (with format changes to the data provided)
2) Export data from SQLite DB into a formatted Excel Document
3) Change settings for implementing this progress e.g. Menu toggles, color coding
4) Open help guide

## Development notes

 - 100% Java
 - JDK8+
 - Maven version 3 recommended

## Supported bank statements
 Nationwide CSV bank statement (as of August 2023)

## How to use

Either clone the repo and build using Maven:
'mvn clean package' and find the jar in the target folder.
'mvn test' run all unit tests

OR download latest release and run .jar executable, will have guide file attached in correct location



 
