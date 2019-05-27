package com.paprika.aop.service.query;

import com.paprika.aop.domain.Log;
import com.paprika.aop.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author adam
 * @date 2019/5/27
 * PS: You may say that I'm a dreamer.But I'm not the only one.
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LogQueryService {

    private LogRepository logRepository;

    @Autowired
    public LogQueryService(LogRepository logRepository){
        this.logRepository = logRepository;
    }

    class Spec implements Specification<Log> {

        private Log log;

        public Spec(Log log){
            this.log = log;
        }

        @Override
        public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();


            if(!ObjectUtils.isEmpty(log.getUserName())){
                list.add(cb.like(root.get("userName").as(String.class),"%"+log.getUserName()+"%"));
            }

            if (!ObjectUtils.isEmpty(log.getLogType())) {
                list.add(cb.equal(root.get("logType").as(String.class), log.getLogType()));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
    public Page queryAll(Log log, Pageable pageable){
        return logRepository.findAll(new Spec(log),pageable);
    }
}
