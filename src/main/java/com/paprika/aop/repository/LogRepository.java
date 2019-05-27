package com.paprika.aop.repository;

import com.paprika.aop.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author adam
 * @date 2019/5/27
 * PS: You may say that I'm a dreamer.But I'm not the only one.
 */
@Repository
public interface LogRepository extends JpaRepository<Log, Long>, JpaSpecificationExecutor {
    /**
     * 查询时间段内的访问IP记录条数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 记录条数
     */
    @Query(value = "select count(*) from (select request_ip from log where create_time between ?1 and ?2 group by request_ip) as s", nativeQuery = true)
    Long findIp(String startTime, String endTime);
}
