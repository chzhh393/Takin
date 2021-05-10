package io.shulie.surge.data.sink.influxdb;

import com.google.inject.ImplementedBy;
import io.shulie.surge.data.common.lifecycle.Stoppable;
import org.influxdb.dto.BatchPoints;

import java.util.Map;

/**
 * 封装 InfluxDB 的服务的写实现
 *
 * @author pamirs
 */
@ImplementedBy(DefaultInfluxDBSupport.class)
public interface InfluxDBSupport extends Stoppable {
    /**
     * 插入批量数据
     *
     * @param batchPoints 批量数据点
     */
    void writeBatchPoint(BatchPoints batchPoints);

    BatchPoints getBatchPoints(String dataBase);

    /**
     * 插入数据
     *
     * @param measurement 表名
     * @param tags        tag 带索引
     * @param fields      field 不带
     */
    void write(String dataBase,String measurement, Map<String, String> tags, Map<String, Object> fields, long time);

    /**
     * 创建默认保留策略
     *
     * @param policyName  策略名
     * @param duration    保存天数
     * @param replication 保存副本数量
     */
    void createRetentionPolicy(String dataBase,String policyName, String duration, int replication);

    /**
     *
     * @param database
     */
    void createDatabase(String database);
}
