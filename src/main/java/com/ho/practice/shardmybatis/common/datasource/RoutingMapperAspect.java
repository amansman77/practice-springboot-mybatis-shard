package com.ho.practice.shardmybatis.common.datasource;


import com.ho.practice.shardmybatis.common.annotation.ShardKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.SoftException;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RoutingMapperAspect {

    private final static Integer RADIX = 24;

    @Pointcut("execution(* com.ho.practice..shardmybatis..dao..*DaoService.*(..))")
    private void daoService() {
    }

    @Pointcut("execution(public * *(.., @com.ho.practice.shardmybatis.common.annotation.ShardKey (*), ..))")
    private void shardKeyParameter() {
    }

    @Around("daoService() && shardKeyParameter()")
    public Object aroundTargetMethod(ProceedingJoinPoint thisJoinPoint) {
        String shardKey = this.getShardKey(thisJoinPoint);
        String routingDataSourceKey = determineRoutingDataSourceKey(shardKey);
        log.debug("Routing Key: " + routingDataSourceKey);

        DataSourceLookupKeyContextHolder.set(routingDataSourceKey);

        try {
            return thisJoinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            DataSourceLookupKeyContextHolder.clear();
        }
    }

    private String getShardKey(ProceedingJoinPoint thisJoinPoint) {
        MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        Annotation[][] annotations;
        try {
            annotations = thisJoinPoint.getTarget().getClass().
                    getMethod(methodName, parameterTypes).getParameterAnnotations();
        } catch (Exception e) {
            throw new SoftException(e);
        }

        for (int i = 0; i < annotations.length; i++) {
            for (Annotation annotation : annotations[i]) {
                if (annotation.annotationType() == ShardKey.class) {
                    return (String) thisJoinPoint.getArgs()[i];
                }
            }
        }
        return null;
    }

    private String determineRoutingDataSourceKey(String inputId) {
        long idSeq = getIdDeciaml(inputId);
        String idSeqString = String.valueOf(idSeq);
        String lastNo = idSeqString.substring(idSeqString.length() - 1);
        int serverNo = Integer.valueOf(lastNo) % 3;

        return "post0" + (serverNo+1);
    }

    private long getIdDeciaml(String targetId) {
        long total = 0L;

        if (targetId.matches("\\d+")) {
            total = Long.parseLong(targetId);
        } else {
            targetId = targetId.toUpperCase();
            String validValues = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            int lastIndex = targetId.length() - 1;
            byte[] letters = targetId.getBytes();
            for (int i = 0; i < letters.length; i++) {
                int actualValue = validValues.lastIndexOf(letters[i]);
                if (actualValue >= 0) {
                    total += (long) ((actualValue + 1) * Math.pow(RADIX, lastIndex));
                }
                lastIndex--;
            }
        }
        return total;
    }

}
