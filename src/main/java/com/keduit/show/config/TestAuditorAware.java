//package com.keduit.show.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//
//import java.util.Optional;
//
//@Configuration
//@EnableJpaAuditing
//public class TestAuditorAware implements AuditorAware<String> {
//
//    // ThreadLocal을 사용하여 각 스레드별로 다른 값을 사용할 수 있도록 함
//    private static final ThreadLocal<String> currentAuditor = new ThreadLocal<>();
//
//    @Override
//    public Optional<String> getCurrentAuditor() {
//        return Optional.ofNullable(currentAuditor.get());
//    }
//
//    // 테스트에서 사용될 사용자 ID를 설정
//    public static void setCurrentAuditor(String auditor) {
//        currentAuditor.set(auditor);
//    }
//
//    // ThreadLocal 값을 초기화
//    public static void clear() {
//        currentAuditor.remove();
//    }
//}
