package com.xchat.xchat.cache;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config")
@Data
@NoArgsConstructor
public class ConfigEntity {

    private String key;
    private String value;
}
