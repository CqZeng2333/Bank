# Bank
> A virtual bank system implemented with Java
>
> Authors: Annan Miao, Chuqian Zeng, Qingyang Xu

## Requirement
* Java 1.8
* SQL database

## Usage
### Database implementation
* Import the mysql connector (see [mysql-connector](https://dev.mysql.com/downloads/connector/j/)) to your java library
* Import the [bank.sql](./src/bank.sql) into your local database
* Alter the "USER" and "PASSWORD" in [Connector.java](./src/connect_database/Connector.java) to the username and password of your own local database

### Compile and Run
* Download the project
* The main function is in [LoginJFrame.java](./src/backstage/LoginJFrame.java)
>
More description in [project description](./presentation.pdf)
