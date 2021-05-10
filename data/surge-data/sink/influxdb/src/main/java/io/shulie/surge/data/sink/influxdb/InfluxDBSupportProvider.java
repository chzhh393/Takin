package io.shulie.surge.data.sink.influxdb;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


/**
 * 返回单例的 {@link InfluxDBSupportProvider} 对象
 *
 * @author pamirs
 */
@Singleton
public class InfluxDBSupportProvider implements Provider<InfluxDBSupport> {
    private static final Logger logger = Logger.getLogger(InfluxDBSupportProvider.class);

    private InfluxDBSupport singleton;

    @Inject
    public InfluxDBSupportProvider(@Named("config.influxdb.url") String url,
                                   @Named("config.influxdb.username") String username,
                                   @Named("config.influxdb.password") String password,
                                   @Named("config.influxdb.duration") String duration,
                                   @Named("config.influxdb.database.metircs") String metricsDataBase,
                                   @Named("config.influxdb.database.monitor") String monitorDataBase) {

        try {
            this.singleton = new DefaultInfluxDBSupport(url, username, password);

            /**
             * 默认创建两个库
             */
            if (StringUtils.isNotBlank(monitorDataBase) || StringUtils.isNotBlank(metricsDataBase)) {
                this.singleton.createDatabase(monitorDataBase);
                this.singleton.createDatabase(metricsDataBase);
                this.singleton.createRetentionPolicy(monitorDataBase, monitorDataBase, duration, 1);
                this.singleton.createRetentionPolicy(metricsDataBase, metricsDataBase, duration, 1);
            }
        } catch (Exception e) {
            logger.warn("InfluxDBServiceProvider init fail.url :{}" + url, e);
            throw e;
        }
    }

    @Override
    public InfluxDBSupport get() {
        return this.singleton;
    }
}
