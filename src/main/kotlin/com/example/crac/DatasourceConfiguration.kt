package com.example.crac

import com.zaxxer.hikari.HikariDataSource
import org.crac.Context
import org.crac.Core
import org.crac.Resource
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.io.PrintWriter
import java.sql.Connection
import java.util.logging.Logger
import javax.sql.DataSource

@Configuration
//@EnableJpaRepositories
internal class DataSourceConfiguration {

//    @Bean
//    fun getDataSource(datasourceProperties: DataSourceProperties): DataSource {
//        val dataSourceBuilder = DataSourceBuilder.create()
//        dataSourceBuilder.driverClassName(datasourceProperties.driverClassName)
//        dataSourceBuilder.url(datasourceProperties.url)
//        dataSourceBuilder.username(datasourceProperties.username)
//        dataSourceBuilder.password(datasourceProperties.password)
//        @Suppress("UNCHECKED_CAST")
//        val ds = DatasourceWithCheckpointDecorator(dataSourceBuilder as DataSourceBuilder<HikariDataSource>)
//        Core.getGlobalContext().register(ds)
//        return ds
//    }
}

class DatasourceWithCheckpointDecorator : DataSource, Resource {

    private val dsb: DataSourceBuilder<HikariDataSource>

    constructor(dsb: DataSourceBuilder<HikariDataSource>) {
        println("========================== initializing datasource")
        this.dsb = dsb
        this.ds = dsb.build()
    }

    private var ds: HikariDataSource?

    override fun beforeCheckpoint(context: Context<out Resource>?) {
        println("======================== before checkpoint")
        ds?.close()
        ds = null
        println("======================== before checkpoint done")
    }

    override fun afterRestore(context: Context<out Resource>?) {
        println("======================== after restore")
        ds = dsb.build()
        println("======================== after restore done")
    }

    override fun getLogWriter(): PrintWriter = ds!!.logWriter

    override fun setLogWriter(out: PrintWriter) {
        ds!!.logWriter = out
    }

    override fun setLoginTimeout(seconds: Int) {
        ds!!.loginTimeout = seconds
    }

    override fun getLoginTimeout(): Int = ds!!.loginTimeout

    override fun getParentLogger(): Logger = ds!!.parentLogger

    override fun <T : Any?> unwrap(iface: Class<T>?): T = ds!!.unwrap(iface)

    override fun isWrapperFor(iface: Class<*>?): Boolean = ds!!.isWrapperFor(iface)

    override fun getConnection(): Connection = ds!!.connection

    override fun getConnection(username: String?, password: String?): Connection = ds!!.getConnection(username, password)

}