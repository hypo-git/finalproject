package com.finalproject.finalproject.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HibernateFilterAspect {
    @PersistenceContext
    private EntityManager entityManager;

    @Before("execution(* com.finalproject.finalproject..*Repository+.*(..))")
    public void enableLiveFilter() {
        Session session = entityManager.unwrap(Session.class);

        if (session.getEnabledFilter("liveFilter") == null) {
            session.enableFilter("liveFilter").setParameter("isLive", true);
        }
    }
}
