package idiomaticKotlin

import org.apache.commons.dbcp2.BasicDataSource


fun blub(){
    // Don't
    val dataSource = BasicDataSource()
    dataSource.driverClassName = "com.mysql.jdbc.Driver"
    dataSource.url = "jdbc:mysql://domain:3309/db"
    dataSource.username = "username"
    dataSource.password = "password"
    dataSource.maxTotal = 40
    dataSource.maxIdle = 40
    dataSource.minIdle = 4
}

// Do
val dataSource = BasicDataSource().apply {
    driverClassName = "com.mysql.jdbc.Driver"
    url = "jdbc:mysql://domain:3309/db"
    username = "username"
    password = "password"
    maxTotal = 40
    maxIdle = 40
    minIdle = 4
}