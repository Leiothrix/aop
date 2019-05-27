package com.paprika.aop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * @author adam
 * @date 2019/5/27
 * PS: You may say that I'm a dreamer.But I'm not the only one.
 */
@Entity
@Data
@Table(name = "aop_log")
@NoArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 描述
     */
    private  String description;

    /**
     * 请求方法
     */
    private  String method;

    /**
     * 请求参数
     */
    @Column(columnDefinition = "text")
    private String requestParam;

    /**
     * 日志类型
     */
    @Column(name = "log_type")
    private String logType;

    /**
     * 请求IP
     */
    @Column(name = "request_ip")
    private String requestIp;

    /**
     * 请求耗时
     */
    private Long requestDurationTime;

    /**
     * 异常信息
     */
    @Column(name = "exception_detail", columnDefinition = "text")
    private String exceptionDetail;

    /**
     * 创建日期
     */
    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    public Log(String logType, Long requestDurationTime){
        this.logType = logType;
        this.requestDurationTime = requestDurationTime;
    }
}
