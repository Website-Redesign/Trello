package com.example.trello.global.config;

//@EnableRedisRepositories
//@Configuration
//public class RedisConfig {
//
//    @Value("${spring.data.redis.host}")
//    private String host;
//
//    @Value("${spring.data.redis.port}")
//    private int port;
//
//    @Value("${spring.data.redis.timeout}")
//    private Long timeout;
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(host, port);
//    }
//
//    @Bean(name="redisTemplate")
//    public RedisTemplate<String, Map<String, Boolean>> redisTemplate() {
//        RedisTemplate<String, Map<String, Boolean>> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Boolean.class));
