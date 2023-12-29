package org.choongang.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data // getter와 setter 위주의 클래스로 설정
@ConfigurationProperties(prefix = "file.uploads")
public class FileProperties {
    private String path;
    private String url;
}
