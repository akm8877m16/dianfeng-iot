package dianfeng.iot.openstatus.config;
/*
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {


    @Bean
    public KeyGenerator myKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager= new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(60);
        return cacheManager;
    }

    @Bean
    public RedisTemplate redisTemplate(
            RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}
*/

import dianfeng.iot.openstatus.service.rao.DeviceRaoImpl;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ywh.common.redis.HashCacheRao;
import ywh.common.redis.KeysRao;
import ywh.common.redis.StringCacheRao;
import ywh.common.redis.globalIdGenerator.IdGenerator;
import ywh.common.redis.impl.HashCacheRaoImpl;
import ywh.common.redis.impl.KeysRaoImpl;
import ywh.common.redis.impl.StringRaoImpl;

import javax.annotation.Resource;

@Configuration
public class RedisConfig{
    @Resource
    private RedissonClient redissonClient;

    @Bean
    public HashCacheRao getHashCacheRao(){
        HashCacheRaoImpl hashCacheRao = new HashCacheRaoImpl();
        hashCacheRao.setRedissonClient(redissonClient);
        return hashCacheRao;
    }

    @Bean
    public StringCacheRao getStringCacheRao(){
        StringRaoImpl stringCacheRao = new StringRaoImpl();
        stringCacheRao.setRedissonClient(redissonClient);
        return stringCacheRao;
    }

    @Bean
    public KeysRao getKeysRao(){
        KeysRaoImpl keysRao = new KeysRaoImpl();
        keysRao.setRedissonClient(redissonClient);
        return keysRao;
    }

    @Bean("deviceRao")
    public DeviceRaoImpl getDeviceRaoImpl(){
        DeviceRaoImpl deviceRao = new DeviceRaoImpl();
        deviceRao.setHashCacheRao(getHashCacheRao());
        deviceRao.setStringCacheRao(getStringCacheRao());
        deviceRao.setKeysRao(getKeysRao());
        deviceRao.setSeconds(120);
        deviceRao.setKeyPrefix("device");
        return deviceRao;
    }

    @Bean("idGenerator")
    public IdGenerator getIdGenerator(){
        IdGenerator idGenerator = new IdGenerator();
        idGenerator.setIdName("deviceStatusIdGenerator");
        return idGenerator;
    }

}

