package kgban.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * プロパティ設定.
 */
@Configuration
@PropertySource({"classpath:/ValidationMessages.properties",
    "classpath:/MessageResources.properties"})
public class PropertyConfig {

  @Autowired
  private Environment env;

  /**
   * プロパティ値を取得します.
   * 
   * @param key プロパティキー
   * @return プロパティ値
   */
  public String get(String key) {
    return env.getProperty(key);
  }
}
